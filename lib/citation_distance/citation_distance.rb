def write_seed_candidate_pairs(seed_entries, candidate_entries, filepath)
  f = File.open(filepath,"w") 
  seed_entries.each do |seed_paper|
    candidate_entries.each do |candidate_paper|
      f.puts "#{seed_paper.id} #{candidate_paper.id}"
    end
  end 
  f.close
end

def execute_citation_distance_scorer(graph_edge_file, input_seed_candidate_pair_file,output_file)
	exec("java -Xmx1536m -jar ./lib/citation_distance/citation_distance.jar #{graph_edge_file} #{input_seed_candidate_pair_file} #{output_file}")
end
#execute_citation_distance_scorer("data/input/graph_edge.txt", "data/input/seed_candidate_pairs.txt", "data/output/seed_candidate_pairs_distances.txt")
