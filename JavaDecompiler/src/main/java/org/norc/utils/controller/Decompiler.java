package main.java.org.norc.utils.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Decompiler {
	
		private static String inputDirRoot = "";
		private static String outputDirRoot = "";

		/**
		 * @param args
		 * @throws Exception 
		 * 
		 * 		Arguments:
		 *	 	args[0]:	file or directory?
		 *		args[1]:	input path
		 *		args[2]:	output path
		 * http://www.benf.org/other/cfr/
		 */
		public static void main(String[] args) {
			List<File> listClassFiles = null; 
			
			// get input and output directories
			List<String> arguments = parseArguments(args);
			inputDirRoot = arguments.get(0);
			outputDirRoot = arguments.get(1);
			
			// Recursively search the input directory to get class files
			listClassFiles = getListofClassFiles(inputDirRoot);
			
			// decompile the class files
			if (listClassFiles != null){
				try {
					processListOfClassFiles(listClassFiles);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		/*
		 * 	Done
		 */
		private static List<String> parseArguments(String[] args) {
			// TODO add validation on arguments
			List<String> arguments = new ArrayList<String>();
			
			String inputDirRoot = args[0];
			arguments.add(inputDirRoot += "\\");
			String outputDirRoot = args[1];
			arguments.add(outputDirRoot += "\\");
			
			return arguments;
		}
		
		
		/*
		 *  TODO: Recursively search the input directory to get class files
		 */
		public static List<File> getListofClassFiles(String inputDirRoot) {
			
			List<File> tempList = new ArrayList<File>();
			File file = new File(inputDirRoot);
			//File file = new File("D:\\Documents\\Support\\temp\\Ticket 231649\\documentAdmin\\WEB-INF\\classes\\");
			if(file.exists()){
				tempList = getListofClassFiles(file, tempList);
			}
			
			return tempList;
		}
		
		
		private static List<File> getListofClassFiles(File file, List<File> tempList){
			
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
		
		
		/*
		 *  TODO: decompile the class files
		 *  
		 *  will only work if the package header is declared in the class file
		 */
		public static void processListOfClassFiles(List<File> listClassFiles) throws Exception {
			 
			
			for(File file: listClassFiles){
				
				decompile();
			}
		}
		
		
		/*
		 * TODO: make a call to the jar to decompile one class
		 */
		public static void decompile(String Path) throws Exception{
			// http://stackoverflow.com/questions/4936266/execute-jar-file-from-a-java-program
			Process proc = Runtime.getRuntime().exec("java -jar Validate.jar");
			proc.waitFor();
			// Then retrieve the process output
			InputStream in = proc.getInputStream();
			InputStream err = proc.getErrorStream();
			// std.out
			byte b[]=new byte[in.available()];
			in.read(b,0,b.length);
			System.out.println(new String(b));
			// std.err
			byte c[]=new byte[err.available()];
			err.read(c,0,c.length);
			System.out.println(new String(c));
		}
		
		
		/*
		 * This method writes one class file to output directory
		 * it uses the package header to create subdirectories
		 */
		public static void copyJavaFileToOutDir(File javaFile) throws IOException{
			// read the package header from file
			FileReader reader = new FileReader(javaFile);
			BufferedReader in = new BufferedReader(reader);
			String packageHeader =  in.readLine();
			
			// strip "package " and everything after ";" 
			String temp = packageHeader.substring(8, packageHeader.indexOf(";"));
			// format it to a path relative to the outputDir
			String outputSubDir = temp.replace(".", "/") + "/";
			in.close();
			reader.close(); 
			
			// if subdirs do not exist, create them
			File tempFile = new File(outputDirRoot + outputSubDir);
			if(!(tempFile.exists() || tempFile.isDirectory())){
				boolean isSubDirCreated = tempFile.mkdirs();
			}
			
			// copy java file to subDirs
			Path sourcePath = javaFile.toPath();
			OutputStream outputStream = new FileOutputStream(outputDirRoot + outputSubDir +javaFile.getName());
			Files.copy(sourcePath,outputStream);
			outputStream.close();
		}
		
		
		public void setInputDir(String inputDirRoot) {
			Decompiler.inputDirRoot = inputDirRoot;			
		}
		
		public String getInputDir(){
			return Decompiler.inputDirRoot;
		}
		
		public void setOutputDir(String outputDirRoot) {
			Decompiler.outputDirRoot = outputDirRoot;			
		}
		
		public String getOutputDir(){
			return Decompiler.outputDirRoot;
		}
}
