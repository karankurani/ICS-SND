def execute_LDA(input_seed_file,prediction_file,output_file)
	system("java -jar ./lib/LDA/LDA.jar #{input_seed_file} #{prediction_file} #{output_file}")
end
#execute_LDA("/data/input/input-for-lda.yml","/data/input/input-for-lda.yml","/data/output/jason.yml")
