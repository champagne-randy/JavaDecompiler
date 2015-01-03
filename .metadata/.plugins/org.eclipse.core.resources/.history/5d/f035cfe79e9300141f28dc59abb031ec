class Recording
    attr_reader :su_id, :call_id, :year, :month, :day
    
    #set recordings properties
    def initialize(file_name)
      #split recording
      name_arr = recording.split('_')
      #initialize parameters
      @su_id = name_arr[0]
      @call_id = name_arr[1] 
      @year = name_arr[2] 
      @month = name_arr[3] 
      @day = name_arr[4]
      day.slice!(".mp3")
    end
    
    def to_s
      "recording properties are:\n su_id = #{:su_id}\n call_id = #{:call_id}\n year = #{:year}\n month = #{:month}\n day = #{:day}\n"
    end
      
end