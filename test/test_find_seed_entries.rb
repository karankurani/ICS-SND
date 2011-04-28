require "test/unit"

require "utilities"

class TestUtilities < Test::Unit::TestCase
  def setup
    @seed_papers = find_seed_papers
  end

  def test_find_seed_papers
    assert_equal(2, @seed_papers.size)
    assert_equal(["paper 1", "paper 2"], @seed_papers.map { |x| x.title })

    paper1 = @seed_papers.first
    paper2 = @seed_papers.last
    assert_equal("paper 1", paper1.title)
    assert_equal("paper 2", paper2.title)
  end

  def test_authors_of
    authors = authors_of(@seed_papers).map { |x| x.name }
    assert(authors.include?("author 1"), "Should include author 1.")
    assert(authors.include?("author 2"), "Should include author 2.")
    assert(authors.include?("author 3"), "Should include author 3.")
  end

  def test_co_authors_of_1
    paper = Paper.new("paper")
    author1 = Author.new("author 1")
    author2 = Author.new("author 2")
    paper.add_author(author1)
    paper.add_author(author2)
    assert_equal(1, author1.co_authors.size)
    assert_equal("author 2", author1.co_authors.first.name)
    assert_equal(1, author2.co_authors.size)
    assert_equal("author 1", author2.co_authors.first.name)
  end

  def test_co_authors_of_2
    authors = authors_of(@seed_papers)
    author = authors.find { |x| x.name == "author 2" }
    assert_equal("author 2", author.name)
    actual = author.co_authors.map { |x| x.name }
    assert_equal(["author 1", "author 3"], actual)
  end
  
  def test_papers_of
    authors = authors_of(@seed_papers)
    papers = papers_of(authors).map { |x| x.title }
    assert(papers.include?("paper 1"), "Should include author 1.")
    assert(papers.include?("paper 2"), "Should include author 2.")
    assert(papers.include?("paper 3"), "Should include author 3.")
  end
end
