#project properties
project_number = 7536
cai_id = 26637
puts "project properties are:\n norc_project_number = #{project_number}\n cai_project_id = #{cai_id}\n"
puts

# Read input file
file = File.open("..\\input\\input.txt", "r")
recordings = file.read
puts recordings 

#create sample_unit table
fname = "..\\output\\dbo.sample_unit.csv"
sample_unit_table = File.open(fname, "w")

#add headers
sample_unit_table.puts "sample_unit_key,su_id\n"


#parse recording info sample recording 0100031086_2_2014_11_5.mp3
recordings.each_line{ |recording|	
	puts("recording file_name is:\n #{recording}\n")
	puts

	#split recording
	name_arr = recording.split('_')

	#set recordings properties
	su_id = name_arr[0]; call_id = name_arr[1]; year = name_arr[2]; month = name_arr[3]; day = name_arr[4]
	day.slice!(".mp3")

	puts "recording properties are:\n su_id = #{su_id}\n call_id = #{call_id}\n year = #{year}\n month = #{month}\n day = #{day}\n"
	puts
	
	sample_unit_table.puts ",#{su_id}"
}

#close files
sample_unit_table.close
recordings.close