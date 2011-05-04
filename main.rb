require 'rubygems'
require 'pp'
require 'yaml'
require './lib/entities.rb'
require './lib/utilities.rb'
require './lib/LDA/execute_LDA.rb'
require './lib/citation_distance/citation_distance.rb'

log = DataMapper::Logger.new($stdout, :info)
#err = DataMapper::Logger.new($stdout, :info)
DataMapper.setup(:default, CONNECTION)

INPUT_FOR_LDA      = File.expand_path(File.join(File.dirname(__FILE__), "./lib/LDA/data/input/input-for-lda-1.yml"))
CANDIDATES_FOR_LDA = File.expand_path(File.join(File.dirname(__FILE__),"./lib/LDA/data/input/candidates-for-lda.yml"))
OUTPUT_FROM_LDA    = File.expand_path(File.join(File.dirname(__FILE__),"./lib/LDA/data/output/output.yml"))
CITATION_DISTANCE_GRAPH_EDGE = File.expand_path(File.join(File.dirname(__FILE__),"./lib/citation_distance/data/input/graph_edge.txt"))
CITATION_DISTANCE_INPUT = File.expand_path(File.join(File.dirname(__FILE__),"./lib/citation_distance/data/input/seed_candidate_pairs.txt"))
CITATION_DISTANCE_OUTPUT = File.expand_path(File.join(File.dirname(__FILE__),"./lib/citation_distance/data/output/seed_candidate_pairs_distances.txt"))

log << "Starting ..."
seed_entries = Entry.all(:isSeed => true, :limit => 1)
puts seed_entries.class
File.open(INPUT_FOR_LDA, "w") {|f| f.puts seed_entries.to_yaml }

while true do
  log << "\n***\n= getting authors =\n***\n"
  seed_authors = authors_of(seed_entries)

  log << seed_authors.map{ |x| x.name }.join(', ')

  log << "\n***\n= getting co-authors =\n***\n"
  seed_co_authors = co_authors_of(seed_authors)
  #log << seed_co_authors.map{ |x| x.name }.join(', ')
  log << "count of co-authors: #{seed_co_authors.size}"

  log << "\n***\n= getting entries of seed co-authors =\n***\n"
  candidate_entries = entries_of(seed_co_authors)
  #log << candidate_entries.map{ |x| x.title }.join(', ')
  log << "candidate papers: #{candidate_entries.size}"
  File.open(CANDIDATES_FOR_LDA, "w") { |f| f.puts candidate_entries.to_a.to_yaml }
  
  #LDA scoring
  log << "\n***\n= calling train lda =\n***\n"
  execute_LDA(INPUT_FOR_LDA, CANDIDATES_FOR_LDA, OUTPUT_FROM_LDA)
  
  # Citation Network Score
  log << "\n***\n= calling citation distance calculator =\n***\n"
  log << "\n***\n= writing seed candidate pairs =\n***\n"
  write_seed_candidate_pairs(seed_entries,candidate_entries,CITATION_DISTANCE_INPUT)
  log << "\n***\n= done.. calculating distances =\n***\n"
  execute_citation_distance_scorer(CITATION_DISTANCE_GRAPH_EDGE, CITATION_DISTANCE_INPUT, CITATION_DISTANCE_OUTPUT)
  log << "\n***\n= getting ready to start scoring =\n***\n"
  score_vectors = {}

  candidate_entries.each do |entry|
    # Co-Author Score
    score_3 = seed_entries.map do |seed|
      (co_authors_of(entry.authors) & seed.authors).size
    end.reduce(:+)
    #puts "#{score_3} #{entry.title}"
    # Reference Score
    score_4 = seed_entries.map do |seed|
      (seed.citations.include? entry) ? 1 : 0
    end.reduce(:+)

    score_vectors[entry] =
    {:score_1 => score_1,
     :score_2 => score_2,
     :score_3 => score_3,
     :score_4 => score_4}
  end

  score_vectors.each_pair do |entry, scores|
    if scores_are_good_enough(scores)
      seed_entries << entries
    end
  end
end
log << "End."
