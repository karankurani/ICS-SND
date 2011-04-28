require 'rubygems'
require 'pp'
require 'yaml'
require 'set'
require './entities.rb'

TIME_STAMP = Time.new.strftime("%Y-%m-%d-%H-%M-%S")
ERROR      = "./log/error-#{TIME_STAMP}.txt"
LOG        = "./log/log-#{TIME_STAMP}.txt"

log = DataMapper::Logger.new(LOG, :debug)
err_log = DataMapper::Logger.new(ERROR, :info)
DataMapper.setup(:default, 'mysql://jrm425:verysecretpassword@192.168.37.2/ICSSND')

count = 0
File.open("CoAuthorNetwork.txt", "r").each_line do |line|
  begin
    author_id = line.gsub(/^(\d+)<break>.*/, '\1').to_i
    co_author_ids = line.gsub(/^\d+<break>(.*)/, '\1').split(',').map { |x| x.to_i }

    a = Author.get(author_id)
    raise "nil: #{author_id}" if a.nil?
    log << ("%0.3f" % (count.to_f / 975007.0))
    log << "author: #{a.name}"
    co_author_ids = Set.new(co_author_ids)

    co_author_ids.to_a.each do |co_author_id|
      c = Author.get(co_author_id)
      if c.nil?
        err_log << "nil: #{author_id} - #{co_author_id}"
      else
        log << " - #{c.name}"
        Friendship.create(:source_id => author_id, :target_id => co_author_id)
      end
    end

  rescue Exception => ex
    err_log << "line: #{count}"
    err_log << ex.message
    err_log << ex.backtrace.join("\n")
  ensure
    count += 1
    # break if count == 3
  end
end
