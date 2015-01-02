package test.java.org.norc.utils.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import main.java.org.norc.utils.controller.Decompiler;

import org.junit.Before;
import org.junit.Test;

public class DecompileTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testWriteJavaFileToOutDir() {
		//fail("Not yet implemented");
		String testDir = "src\\test\\java\\org\\norc\\utils\\controller\\";
		File javaFile = new File(testDir + "TestJavaFile.java");
		Decompiler decompiler = new Decompiler(); 
		decompiler.setOutputDir(testDir);
		try {
			decompiler.copyJavaFileToOutDir(javaFile);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		File out = new File(testDir + testDir + "TestJavaFile.java");
		assertTrue(out.exists());
	}

}
