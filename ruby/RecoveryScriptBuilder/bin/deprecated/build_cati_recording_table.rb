#project properties
project_number = 7536
cai_id = 26637
puts "project properties are:\n norc_project_number = #{project_number}\n cai_project_id = #{cai_id}\n"
puts

# Read input file
file = File.open("..\\input\\input.txt", "r")
recordings = file.read
puts recordings 
puts

#create cati_recording table
fname = "..\\output\\dbo.cati_recording_table.csv"
cati_rec_table = File.open(fname, "w")
cati_rec_table.puts "recording_key, file_name, voxco_project_id, recording_start_datetime, recording_end_datetime, recording_disposition_id, recording_disposition_datetime, call_type_id, status_id, su_id, call_number, call_month, call_day, call_year, site_id, staff_roster_id, call_result, lcs, phone"

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
	
	
	#parse cati_recording columns
	recording_key						= ""
	file_name							= "/#{project_number.to_s}/#{cai_id.to_s}/#{year.to_s}#{'%02d' % month.to_s}#{'%02d' % day.to_s.strip}/#{recording.strip}"
	voxco_project_id					= cai_id
	recording_start_datetime			= "null"
	recording_end_datetime				= "null"
	recording_disposition_id			= "1"
	recording_disposition_datetime		= "GETDATE()"
	call_type_id						= "2"
	status_id							= "6"
	su_id								= su_id
	call_number							= call_id
	call_month							= month
	call_day							= day
	call_year							= year
	site_id								= "1"
	staff_roster_id						= "null"
	call_result							= "XX"
	lcs									= "1"
	phone								= "3125551234"

	
	string = recording_key.to_s + ',' + file_name + ',' + voxco_project_id.to_s + ',' + recording_start_datetime.strip + ',' + recording_end_datetime.strip + ',' + recording_disposition_id.strip + ',' + recording_disposition_datetime.strip + ',' + call_type_id.strip + ',' + status_id.strip + ',' + su_id.strip + ',' + call_number.strip + ',' + call_month.strip + ',' + call_day.strip + ',' + call_year.strip + ',' + site_id.strip + ',' + staff_roster_id.strip + ',' + call_result.strip + ',' + lcs.strip + ',' + phone + "\n"
	cati_rec_table.puts string
}

#close files
cati_rec_table.close
#recordings.close
