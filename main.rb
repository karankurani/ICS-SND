require 'rubygems'
require 'pp'
require 'yaml'
require './entities.rb'

TIME_STAMP = Time.new.strftime("%Y-%m-%d-%H-%M-%S")
ERR        = "./log/err-#{TIME_STAMP}.txt"
LOG        = "./log/log-#{TIME_STAMP}.txt"
log = DataMapper::Logger.new(LOG, :debug)
err = DataMapper::Logger.new(ERR, :debug)

seed_entries = Entry.all(:isSeed => true)

while !has_converged do
  seed_authors = authors_of(seed_entries)
  seed_co_authors = co_authors_of(seed_authors)
  candidate_entries = entries_of(co_authors)
  lda_model = train_lda(seed_entries)

  score_vectors = {}

  candidate_entries.each do |entry|
    # LDA Similarity
    score_1 = seed_entries.map do |seed|
      lda_similarity(entry, seed, lda_model)
    end.max

    # Citation Network Score
    score_2 = seed_entries.map do |seed|
      citation_distribution(entry, seed)
    end.min

    # Co-Author Score
    score_3 = seed_entries.map do |seed|
      (co_authors_of(authors_of(entry)) \
        & authors_of(seed)).size
    end.reduce(:+)

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