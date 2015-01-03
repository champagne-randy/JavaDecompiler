#project properties
project_number = 7536
cai_id = 26637
puts "project properties are:\n norc_project_number = #{project_number}\n cai_project_id = #{cai_id}\n"
puts

# Read input file
file = File.open("..\\input\\input.txt", "r")
recordings = file.read
puts recordings 

#create move command file
fname = "..\\output\\move_command_file.csv"
move_command_file = File.open(fname, "w")

#add headers to file
move_command_file.puts "from,to"

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
	
	
	#parse move commands 
	#sample source folder \\S081npap00957\recdmgmt-e\TempData\work\0100018038_3_2014_11_4.mp3
	src_root = '\\\\S081npap00957\\recdmgmt-e\\TempData\\work\\'
	src_dir_label = "Source Directory"
	src_dir = src_root + recording
	puts"source file is\n #{src_dir}"
	puts
	
	#sample destination folder \\norc.org\projects\Recording\Voxco\7605\22691\20141104\
	dest_root = '\\\\norc.org\\projects\\Recording\Voxco\\'
	dest_dir_label = "Destination Directory"
	dest_dir = dest_root + project_number.to_s + "\\" + cai_id.to_s + "\\" + year.to_s + '%02d' % month.to_s + '%02d' % day.to_s
	puts"destination directory is\n #{dest_dir}"
	puts
	
	string = src_dir.strip + ', ' + dest_dir.strip + "\\"
	move_command_file.puts string
}

#close files
#move_command_file.close
#recordings.close