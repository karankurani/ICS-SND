INF = 9999

def find_min_seed_cand_dist(file)
  h = Hash.new(INF)

  File.open(file, 'r').each_line do |line|
    seed, cand, dist = line.split(' ')
    dist = dist == "null" ? INF : dist.to_i
    h[cand] = [dist, h[cand]].min
  end

  return h
end