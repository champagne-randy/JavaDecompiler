package test.java.org.norc.utils.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import main.java.org.norc.utils.decompiler.controller.Decompiler;

import org.junit.Before;
import org.junit.Test;

public class DecompileTest {

	@Before
	public void setUp() throws Exception {
		// delete files from last test
	}

	@Test
	public void testCopyJavaFileToOutDir() {
		// move this to the setUpBeofreTestSuite method
		Decompiler decompiler = new Decompiler(); 
		decompiler.setInputDir( Paths.get("src/test/resources/input/") );
		decompiler.setOutputDir( Paths.get("src/test/resources/output/"));
		
		Path testJavaFile = Paths.get("src/test/resources/TestJavaFile.java");
		Path subDir = Paths.get("level1/level2/level3/");
		try {
			decompiler.copyJavaFileToOutDir(testJavaFile, subDir);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		Path out = Paths.get("src/test/resources/output/TestJavaFile.java");
		try {
			assertTrue(Files.isSameFile(testJavaFile, out));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
