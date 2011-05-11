require 'rubygems'
require 'pp'
require 'yaml'
require './lib/entities.rb'
require './lib/utilities.rb'
require './lib/LDA/execute_LDA.rb'
require './lib/citation_distance/citation_distance.rb'
require './lib/seed_candidate_pairs_distances.rb'

DataMapper.setup(:default, CONNECTION)

INPUT_FOR_LDA      = File.expand_path(File.join(File.dirname(__FILE__), "./lib/LDA/data/input/input-for-lda-1.yml"))
CANDIDATES_FOR_LDA = File.expand_path(File.join(File.dirname(__FILE__),"./lib/LDA/data/input/candidates-for-lda.yml"))
OUTPUT_FROM_LDA    = File.expand_path(File.join(File.dirname(__FILE__),"./lib/LDA/data/output/output.yml"))
CITATION_DISTANCE_GRAPH_EDGE = File.expand_path(File.join(File.dirname(__FILE__),"./lib/citation_distance/data/input/graph_edge.txt"))
CITATION_DISTANCE_INPUT = File.expand_path(File.join(File.dirname(__FILE__),"./lib/citation_distance/data/input/seed_candidate_pairs.txt"))
CITATION_DISTANCE_OUTPUT = File.expand_path(File.join(File.dirname(__FILE__),"./lib/citation_distance/data/output/seed_candidate_pairs_distances.txt"))
$log << "Starting ..."
seed_entries = Entry.all(:isSeed => true)
File.open(INPUT_FOR_LDA, "w") {|f| f.puts seed_entries.to_yaml }
evaluated_authors = []
iteration_number = 1
while true do
  GC.enable
  iter_out = File.expand_path(File.join(File.dirname(__FILE__),"./data/output/iter-out-#{iteration_number}.txt"))
  bipart_out = File.expand_path(File.join(File.dirname(__FILE__),"./data/output/bipart-out-#{iteration_number}.txt"))
  $log << "\n***\n= getting authors =\n***\n"
  seed_authors = authors_of(seed_entries)

  $log << seed_authors.map{ |x| x.name }.join(', ')

  $log << "\n***\n= getting co-authors =\n***\n"
  seed_co_authors = co_authors_of(seed_authors).to_a.shuffle.first(100)
  seed_co_authors.reject! { |coa| evaluated_authors.include? coa }
  evaluated_authors |= seed_co_authors
  #$log << seed_co_authors.map{ |x| x.name }.join(', ')
  $log << "count of co-authors: #{seed_co_authors.size}"

  $log << "\n***\n= getting entries of seed co-authors =\n***\n"
  candidate_entries = entries_of(seed_co_authors).reject { |paper| seed_entries.include? paper }
  #$log << candidate_entries.map{ |x| x.title }.join(', ')
  $log << "candidate papers: #{candidate_entries.size}"

  $log << "\n***\n= getting ready to start scoring =\n***\n"
  p "GC called: #{GC.count}"
  #LDA scoring
  File.open(CANDIDATES_FOR_LDA, "w") { |f| f.puts candidate_entries.to_a.to_yaml }
  $log << "\n***\n= calling lda =\n***\n"
  execute_LDA(INPUT_FOR_LDA, CANDIDATES_FOR_LDA, OUTPUT_FROM_LDA)
  lda_scores = nil
  File.open(OUTPUT_FROM_LDA) { |f| lda_scores = YAML.load(f.read) }

  # Citation Network Score
  $log << "\n***\n= calling citation distance calculator =\n***\n"
  $log << "\n***\n= writing seed candidate pairs =\n***\n"
  write_seed_candidate_pairs(seed_entries,candidate_entries,CITATION_DISTANCE_INPUT)
  $log << "\n***\n= done.. calculating distances =\n***\n"
  execute_citation_distance_scorer(CITATION_DISTANCE_GRAPH_EDGE, CITATION_DISTANCE_INPUT, CITATION_DISTANCE_OUTPUT)
  citation_distance_scores = find_min_seed_cand_dist(CITATION_DISTANCE_OUTPUT)
  
  temp_entries = []
  count_of_papers = 1
  GC.disable
  candidate_entries.each do |entry|
    p "GC called: #{GC.count}"
    # LDA Score
    lda_entry = lda_scores.find { |x| x.value["id"] == entry.id.to_s }
    score_1 = nil
    unless lda_entry.nil?
      score_1 = lda_entry.value["divergence"]
      score_1 = score_1.to_f unless score_1.nil?
    end

    # Citation Score
    citation_score = citation_distance_scores[entry.id.to_s]
    score_2 = nil
    score_2 = citation_score.to_i unless citation_score.nil?

    # Co-Author Score
    score_3 = seed_entries.map do |seed|
      (co_authors_of(entry.authors) & seed.authors).size
    end.reduce(:+)

    # Reference Score
    score_4 = seed_entries.map do |seed|
      (seed.citation_entries.include? entry) ? 1 : 0
    end.reduce(:+)
    if total_score({:score_1 => score_1,
                   :score_2 => score_2,
                   :score_3 => score_3,
                   :score_4 => score_4}) > 2
      temp_entries << entry
    end
    count_of_papers += 1
    if(count_of_papers % 100 == 0)
      p "#{count_of_papers}/#{candidate_entries.size}"
      GC.enable
      GC.start
      GC.disable
    end
  end
  seed_entries |= temp_entries
  
  File.open(bipart_out, "w") do |f|
    seed_entries.each do |entry|
      entry.citation_entries.each do |cit|
        f.puts "#{entry.id} #{cit.id}" if seed_entries.include? cit
      end
    end
  end
  
  File.open(iter_out, "w") do |f|
    seed_entries.each do |entry|
        f.puts "#{entry.id}"
    end
  end
  iteration_number += 1
  
end
$log << "End."
