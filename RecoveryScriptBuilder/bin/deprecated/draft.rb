#Reading
file = File.open("input.txt", "r")
recordings = file.read
puts recordings 

recordings.each_line{ |recording|
	
	# parse recording info sample recording 0100031086_2_2014_11_5.mp3
	puts("recording file_name is:\n #{recording}\n")
	puts

	# split recording
	name_arr = recording.split('_')

	#set recordings properties
	su_id = name_arr[0]
	call_id = name_arr[1]
	year = name_arr[2]
	month = name_arr[3]
	day = name_arr[4].chomp(".mp3")


	puts "recording properties are:\n su_id = #{su_id}\n call_id = #{call_id}\n year = #{year}\n month = #{month}\n day = #{day}\n"
	puts

}


#in `<main>': undefined method `each' for #<String:> (NoMethodError)