require 'rubygems'
require 'pp'
require 'yaml'

require './entities.rb'

log = DataMapper::Logger.new($stdout, :info)
DataMapper.setup(:default, 'mysql://jrm425:verysecretpassword@192.168.37.2/ICSSND')

entries = Entry.first(:authors => {:name.like => "%Kleinberg%"}, :limit => 50 )
entries.each do |entry|
  authors = entry.authors.map{ |x| x.name }.join(', ')
  log << "#{entry.title} - #{authors}"
  entry.entries.each do |other|
    authors = other.authors.map {|x| x.name}.join(', ')
    log << "\t#{other.title} - #{authors}"
  end
end
