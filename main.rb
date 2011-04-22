require 'rubygems'
require 'datamapper'
require 'pp'

gem 'lda-ruby', '0.3.2'
require 'lda-ruby'

class Author
  include DataMapper::Resource
  property :id, Serial
  property :name, String
  has n, :entries, :through => Resource
  has n, :friendships, :child_key => [ :source_id ]
  has n, :friends, self, :through => :friendships, :via => :target
end

class Friendship
  include DataMapper::Resource

  belongs_to :source, 'Author', :key => true
  belongs_to :target, 'Author', :key => true
end

class Entry
  include DataMapper::Resource
  property :id                   , Serial
  property :abstractText         , String
  property :author               , String
  property :booktitle            , String
  property :entry                , String
  property :indexNumber          , String
  property :isbn                 , String
  property :pages                , String
  property :publisher            , String
  property :referenceIndexNumbers, String
  property :title                , String
  property :url                  , String
  property :year                 , String
  property :isSeed               , Boolean, :default => false
  property :type                 , String
  has n, :authors, :through => Resource

  has n, :citations, :child_key => [ :source_id ]
  has n, :entries, self, :through => :citations, :via => :target
end

class Citation
  include DataMapper::Resource

  belongs_to :source, 'Entry', :key => true
  belongs_to :target, 'Entry', :key => true
end


DataMapper.finalize

DataMapper::Logger.new($stdout, :debug)
DataMapper.setup(:default, 'mysql://jrm425:verysecretpassword@192.168.37.2/ICSSND')

entries = Entry.all :limit => 20, :abstractText.not => nil

corpus = Lda::Corpus.new
entries.each do |entry|
  doc = Lda::TextDocument.new(corpus, entry.abstractText)
  corpus.add_document(doc)
end

lda = Lda::Lda.new(corpus)    # create an Lda object for training
lda.em("random")              # run EM algorithm using random starting points
lda.print_topics(20)          # print the topic 20 words per topic
