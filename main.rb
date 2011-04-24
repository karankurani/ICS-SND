require 'rubygems'
require 'pp'
require 'yaml'

require './entities.rb'

log = DataMapper::Logger.new("output.txt", :debug)
DataMapper.setup(:default, 'mysql://jrm425:verysecretpassword@192.168.37.2/ICSSND')

count = 0
total = Entry.count :referenceIndexNumbers.not =>nil
entries = Entry.all :referenceIndexNumbers.not => nil
unless entries.nil?
  entries.each do |entry|
    references = entry.referenceIndexNumbers
    unless references.nil? || references.empty?
      references.split('|').each do |other_entry_index|
        unless other_entry_index.nil? || other_entry_index.empty?
          entry_sql = repository(:default).adapter.select("SELECT * FROM entries WHERE index_number_int = #{other_entry_index}")
          other_entry = Entry.get(entry_sql.first.id)
          if Citation.count(:source_id => entry.id, :target_id => other_entry.id) == 0
            log << "%.3f #{entry.id} #{other_entry.id}" % (count.to_f/total.to_f)
            Citation.create(:source_id => entry.id, :target_id => other_entry.id)
          end
        end
      end
    end
    count += 1
  end
end
