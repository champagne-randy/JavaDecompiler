require_relative 'Formatter'
require_relative 'Recording'

class ScriptBuilder
    #project properties
    #consider adding validation for directory string
    #collection of recordings
    attr_accessor :project_number, :cai_id, :formatter, :input_file, :output_file, :recordings
    
    # find a better word than formatter
    # implement input validation
    def initialize(project_number, cai_id, operation, input_file, output_file)
      @project_number = project_number
      @cai_id = cai_id
      @formatter = Formatter.new(operation) 
      @input_file = input_file
      @output_file = output_file
      @recordings = Array.new
    end
    
    def run
      read_inputs
      formatter.build_commands(@recordings)
      write_output
    end
    
    
    # methods that follow are not accessible for outside objects
    private
    
    # read file
    # initialize each recording object and 
    # store in local collection
    def read_inputs
      File.open(@input_file).each_line  { |recording_name|
        @recordings += [Recording.new(recording_name)]
      }
    end
    
    # write the outputs of the @formatter to the @output_file
    def write_output
      formatter.write_to(@output_file)
    end
    
end