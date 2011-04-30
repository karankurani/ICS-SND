TIME_STAMP = Time.new.strftime("%Y-%m-%d-%H-%M-%S")
ERR        = "./log/err-#{TIME_STAMP}.txt"
LOG        = "./log/log-#{TIME_STAMP}.txt"
CONNECTION = 'mysql://jrm425:verysecretpassword@192.168.37.2/ICSSND'

def authors_of(entries)
  entries.inject(Set.new) { |authors, entry| authors | entry.authors }
end

def co_authors_of(authors)
  authors.inject(Set.new) { |authors, author| authors | author.friends }
end

def entries_of(authors)
  authors.inject(Set.new) { |entries, author| entries | author.entries }
end

def has_converged
  return true
end
