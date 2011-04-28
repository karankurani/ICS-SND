require 'rubygems'
require 'pp'
require 'yaml'
require './entities.rb'

log = DataMapper::Logger.new("output.txt", :debug)
