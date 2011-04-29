require 'rubygems'
require 'pp'
require 'yaml'
require '../lib/entities.rb'
require '../lib/utilities.rb'
require 'lingua/stemmer'

class Baseline_Paper
	attr_accessor :id, :score
	def initialize(id, score)
		@id = id
		@score = score
	end
end
log = DataMapper::Logger.new("data/baseline-console-output.txt", :info)
#err = DataMapper::Logger.new($stdout, :info)
DataMapper.setup(:default, CONNECTION)
stemmer = Lingua::Stemmer.new(:language=>"en")

f = File.open("../data/baseline_output.yml","w")

sust_keywords = File.open("../data/compsust_keywords.txt","r").readlines.map do |word| 
	word.chomp!  
	word = stemmer.stem(word)
end

pp sust_keywords
#for i in 501..1000
for i in 1..1632958
	score = 0;
	e = Entry.get(i)
 	unless e.nil? 
		s = "#{e.title} #{e.abstractText} #{e.booktitle}".strip!().gsub("-"," ").delete("^a-zA-Z0-9\s")
		s.split(/\s+/).each do |word|
			word = stemmer.stem(word).downcase
			if sust_keywords.include?(word)
				score +=1
			end
		end
	end
#	puts "#{score}\t#{e.title}"
	if score > 0
		p = Baseline_Paper.new(e.id,score)
		log << "#{p.id}"
		f.puts YAML::dump(p)
	end
end

f.close