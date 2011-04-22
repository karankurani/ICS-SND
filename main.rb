require 'rubygems'
require 'datamapper'
require 'pp'
require 'yaml'

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

entries = Entry.all :limit => 200, :isSeed => true

f = File.open("output.yml", "w")
f.puts entries.to_yaml
f.close
