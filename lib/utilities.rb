TIME_STAMP = Time.new.strftime("%Y-%m-%d-%H-%M-%S")
ERR        = File.expand_path(File.join(File.dirname(__FILE__), "../log/err-#{TIME_STAMP}.txt"))
LOG        = File.expand_path(File.join(File.dirname(__FILE__), "../log/log-#{TIME_STAMP}.txt"))
CONNECTION = 'mysql://root:pass@127.0.0.1:3306/ICSSND'

$log = DataMapper::Logger.new($stdout, :info)
$err = DataMapper::Logger.new($stdout, :error)

def authors_of(entries)
  entries.inject(Set.new) { |authors, entry| authors | entry.authors }
end

def co_authors_of(authors)
  authors.inject(Set.new) { |authors, author| authors | author.friends }
end

def entries_of(authors)
  ret = Set.new
  authors.each do |author|
    ret |= author.entries
    $log << "#{author.name}"
  end
  return ret
end

def total_score(scores)
  score_1 = 1.0/scores[:score_1].to_f
  score_2 = 1.0/scores[:score_2].to_f
  score_3 = scores[:score_3].to_f/10
  score_4 = scores[:score_4].to_f
  #puts "#{score_1}\t#{score_2}\t#{score_3}\t#{score_4}"
  total = score_1 + score_2 + score_3 + score_4
  #puts "total: #{total}"
  return total
end

def has_converged
  return true
end

def get_lda_score(lda_scores, entry)
  lda_entry = lda_scores.find { |x| x.value["id"] == entry.id.to_s }
  score_1 = nil
  unless lda_entry.nil?
    score_1 = lda_entry.value["divergence"]
    score_1 = score_1.to_f unless score_1.nil?
  end
  return score_1
end

def get_citation_distance_score(citation_distance_scores, entry)
  citation_score = citation_distance_scores[entry.id.to_s]
  score_2 = nil
  score_2 = citation_score.to_i unless citation_score.nil?
  return score_2
end

def get_co_author_score(seed_entries, entry)
  seed_entries.map do |seed|
    (co_authors_of(entry.authors) & seed.authors).size
  end.reduce(:+)
end

def get_reference_score(seed_entries, entry)
  seed_entries.map do |seed|
    (seed.citation_entries.include? entry) ? 1 : 0
  end.reduce(:+)
end
