require 'rubygems'
require 'datamapper'

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
  property :indexNumber          , String, :required => false
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
