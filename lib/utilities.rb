TIME_STAMP = Time.new.strftime("%Y-%m-%d-%H-%M-%S")
ERR        = File.expand_path(File.join(File.dirname(__FILE__), "../log/err-#{TIME_STAMP}.txt"))
LOG        = File.expand_path(File.join(File.dirname(__FILE__), "../log/log-#{TIME_STAMP}.txt"))
CONNECTION = 'mysql://root:pass@127.0.0.1:3306/ICSSND'

$log = DataMapper::Logger.new($stdout, :info)
$err = DataMapper::Logger.new($stdout, :error)

def authors_of(entries)
  entries.inject(Set.new) { |authors, entry| authors | entry.authors }
end

def co_authors_of(authors)
  authors.inject(Set.new) { |authors, author| authors | author.friends }
end

def entries_of(authors)
  ret = Set.new
  authors.each do |author|
    ret |= author.entries
    $log << "#{author.name}"
  end
  return ret
end

def has_converged
  return true
end
