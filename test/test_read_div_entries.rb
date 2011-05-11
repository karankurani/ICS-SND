require "test/unit"
require "pp"
require "yaml"

OUTPUT_FROM_LDA    = File.expand_path(File.join(File.dirname(__FILE__),"../lib/LDA/data/output/output.yml"))

class DivergencePaper
  attr_accessor :divergence, :id, :title
end

class TestReadDivEntries < Test::Unit::TestCase
  def test_read_div_entries
    foo = nil
    File.open(OUTPUT_FROM_LDA) do |f|
      foo = YAML.load(f.read)
    end
    pp foo.class
    pp foo.size
    pp foo.first.value["title"]
  end
end