require 'rubygems'
require 'pp'
require 'yaml'

class Baseline_Paper
	attr_accessor :id, :score
	def initialize(id, score)
		@id = id
		@score = score
	end
end

class MyHash
    attr_accessor :hash,:max_score
    def initialize
      @hash = Hash.new
      @max_score = 0
    end
    def add_baseline_paper(baseline_paper)
      if baseline_paper.score > @max_score
        @max_score = baseline_paper.score
      end
      arr = @hash[baseline_paper.score]
      if arr.nil?
        arr = Array.new
      end
      arr << baseline_paper
      @hash[baseline_paper.score] = arr
    end
end

score_hash = MyHash.new
paper = nil
File.open( 'baseline_output.yml' ) do |yf|
    YAML.each_document( yf ) do |paper|
      score_hash.add_baseline_paper(paper)      
    end
end

pp score_hash.max_score

sort_file = File.open('baseline_output_sorted.yml','w')

score_hash.max_score.downto(1) do |i|
  unless score_hash.hash[i].nil?
    score_hash.hash[i].each do |paper|
      sort_file.puts paper.to_yaml
    end   
  end
end

sort_file.close
