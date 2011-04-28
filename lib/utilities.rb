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