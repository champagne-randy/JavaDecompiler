package main.java.org.norc.utils.decompiler.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.benf.cfr.reader.Main;
import org.benf.cfr.reader.state.DCCommonState;
import org.benf.cfr.reader.util.getopt.Options;
import org.benf.cfr.reader.util.getopt.OptionsImpl;

import main.java.org.norc.utils.decompiler.utils.FileFinder;

public class Decompiler {
	
		private Path inputDirRoot = null;
		private Path outputDirRoot = null;
		List<Path> listClassFiles = null;  

		/**
		 * This utility will iterate through a directory and decompile every class file in the directory
		 * and its sub-directories. It will output the decompiled java files in the same package structure.
		 * 
		 * It uses the the cfr java decompiler as its engine: http://www.benf.org/other/cfr/
		 *  
		 * @param 	args[0]		fully qualified path to the input directory to be processed
		 * @param	args[1]		fully qualified path to the output directory where to store decompiled java files
		 * @throws 	Exception 
		 *
		 *TODO: use log4j to log outputs and exceptions 
		 *TODO: handle exceptions in main method
		 *TODO: work out encapsulation for this class http://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html
		 *TODO: complete documentation http://www.oracle.com/technetwork/articles/java/index-137868.html
		 *TODO: complete unit testing
		 *TODO: use reflection to deal with testing private methods http://stackoverflow.com/questions/34571/how-to-test-a-class-that-has-private-methods-fields-or-inner-classes
		 *
		 *nextStep: copyJavaFileToOutDir()
		 */
		public static void main(String[] args) {
			Decompiler decompiler = new Decompiler();		
			
			// parse input and output directories if they were supplied as parameters
			// else prompt the user to enter them
			if (args.length == 2) {
				try {
					decompiler.parseArguments(args);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// TODO: implement prompting and input taking for input/output directory
				//decompiler.setInputDir( Paths.get("src/test/resources/input/") );
				//decompiler.setOutputDir( Paths.get("src/test/resources/input/"));
			}
			
			decompiler.run();
		}
		
		
		/*
		 * This method validates the input and output directories and initializes the parameters
		 * 
		 *@param 	args[0]		fully qualified path to the input directory to be processed
		 *@param	args[1]		fully qualified path to the output directory where to store decompiled java files
		 *
		 *TODO: add validation on arguments
		 *TODO: implement validation method/class to validate inputs
		 */
		private void parseArguments(String[] args) throws Exception{
			String inputDirString = args[0];
			System.out.println("Input directory is: " + inputDirString);
			String outputDirString = args[1];
			System.out.println("Output directory is: " + outputDirString);
			
			try {
				// turn inputDirString in to a path
				try{
					this.setInputDir( Paths.get(inputDirString) );
				} 
				catch (InvalidPathException e){
					System.err.println("This is not a valid input directory: " + inputDirRoot);
					e.printStackTrace();
				}
				
				// turn outputDirString in to a path
				try{
					this.setOutputDir( Paths.get(outputDirString) );
				} 
				catch (InvalidPathException e){
					System.err.println("This is not a valid output directory: " + outputDirString);
					e.printStackTrace();
				}
			} 
			catch (Exception e) {
				System.err.print("Unknown Error occured while parsing the input or output directories");
				e.printStackTrace();
			}
		}
		
		
		public void run(){
			
			// Recursively search the input directory to get class files
			try {
				getListofClassFiles();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// decompile the class files
			try {
				processListOfClassFiles();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		/**
		 * This method uses the FileFinder tool to search the input directory to get all class files.
		 * It stores them in the listClassFiles variable
		 * 
		 * FIXME: handle exceptions
		 */
		private void getListofClassFiles() throws Exception{
			
			String pattern = ".class";
			
			FileFinder finder = new FileFinder(pattern);
	        try {
				Files.walkFileTree(inputDirRoot, finder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			setListOfClassFiles( finder.getlistOfFilesFound() );
		}
		
		
		@Deprecated
		private List<File> getListofClassFiles(File file, List<File> tempList){
			
			// recursion: if it's a directory, enter it and search for class files
			if(file.isDirectory()){
				File[] nextFiles = file.listFiles();
				for(File nextFile: nextFiles){
					tempList = getListofClassFiles(nextFile, tempList); 
				}
			} 
			// stop condition: this is a file. If this is a class file, ad it to the list
			else {
				String name = file.getName();
				if (".class".equalsIgnoreCase(name.substring(name.indexOf("."),name.length()))){
					tempList.add(file);
				}
			}
			
			return tempList;
		}
		
		
		/**
		 *  This methods loops through listClassFiles, decompile each classFile,
		 *  creates the sub-directories to mirror the classFile package structure,
		 *  and stores them in outputDirectory
		 * 
		 * @param 	classFile	a class file to be decompiled
		 * @throws 	Exception
		 * @return 	void 
		 */
		private void processListOfClassFiles() throws Exception {
			 
			if (listClassFiles != null){
				for(Path classFile: listClassFiles){					
					decompile(classFile);
				}
			} else {
				// TODO: alert user that there are no classes in the input directory
			}
		}
		
		
		/*
		 * TODO: make a call to the jar to decompile one class
		 * TODO: return a Path to the decompiled java class
		 */
		 public void decompile(Path classFile) throws Exception{
			Map<String, String> opts = new HashMap<String,String>();
			opts.put("outputdir", outputDirRoot.toAbsolutePath().toString());
			Options options = new OptionsImpl(classFile.toAbsolutePath().toString(), null, opts) ;
			DCCommonState dcCommonState = new DCCommonState(options);
			Main.doClass(dcCommonState, classFile.toFile().getAbsolutePath());			
		}
		
		

		/**	
		 * This method compares the absolute path of the child Path to that of the parent and
		 * returns a Path representing the sub-directory. Uses method Path.subpath(int beginIndex, int endIndex) 
		 * http://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html#subpath-int-int-
		 * 
		 * @param 	parent		A Path object representing the parent directory
		 * @param	child		A Path object representing the child directory
		 * 
		 * TODO: Does it work the same for files?
		 * TODO: Test edge cases
		 * TODO: handle exceptions
		 */
		 @Deprecated
		private Path getSubdir(Path parent, Path child){
			Path parentAbsPath = parent.toAbsolutePath();
			Path childAbsPath = child.toAbsolutePath();
			Path subpath = child.subpath(parentAbsPath.getNameCount(),childAbsPath.getNameCount());
			return subpath;
		}
		
		
		/**
		 * This method writes one class file to output directory
		 * create sub-directories if they don't exist and save java file there. 
		 * It returns a Path to the new file that was created or null if failed
		 * 
		 * @param	javaFile	a Path object of the java file to be copied 
		 * @param	subDir		a Path object that is the package structure where to copy javaFile
		 * @return	newFile		a Path object that is the new file copied or null if copy failed
		 */
		 @Deprecated
		public Path copyJavaFileToOutDir(Path javaFile, Path subDir ) throws IOException{
			boolean subDirIsCreated = false;
			Path newFile = null;
			
			File newDir = new File(outputDirRoot.toFile(), subDir.toString());
			if(newDir.exists() || newDir.isDirectory()){
				subDirIsCreated = true;
			} else {
				subDirIsCreated = newDir.mkdirs();
			}
			
			if(subDirIsCreated){
				newFile = newDir.toPath().resolve(javaFile.getFileName());				
				Files.copy(javaFile, newFile);
			}
			return newFile;
		}
		
		
		public void setInputDir(Path inputDirRoot) 		{ this.inputDirRoot = inputDirRoot; }
		public Path getInputDir()						{ return this.inputDirRoot; }
		
		public void setOutputDir(Path outputDirRoot) 	{ this.outputDirRoot = outputDirRoot; }
		public Path getOutputDir()						{ return this.outputDirRoot; }
		
		private void setListOfClassFiles(List<Path> list){ this.listClassFiles = list; }
		//private List<Path> getlistOfClassFiles()			{ return this.listClassFiles; }
}


/*
 // for validation
static void usage() {
    System.err.println("java Decompiler <input path> <output path>";
    System.exit(-1);
}

public static void main(String[] args) throws IOException {

    if (args.length < 4 || !args[1].equals("-input") || !args[3].equals("-output"))
        usage();

    Path inputDirRoot = Paths.get(args[2]);
    Path outputDirRoot = Paths.get(args[4]);
    
    
    String pattern = args[2];

    Finder finder = new Finder(pattern);
    Files.walkFileTree(startingDir, finder);
    finder.done();
}
*/