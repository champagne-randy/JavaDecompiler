package test.java.org.norc.utils.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import main.java.org.norc.utils.decompiler.Decompiler;

import org.junit.Before;
import org.junit.Test;

public class DecompileTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testWriteJavaFileToOutDir() {
		Decompiler decompiler = new Decompiler(); 
		decompiler.setInputDir( Paths.get("src/test/resources/input/") );
		decompiler.setOutputDir( Paths.get("src/test/resources/input/"));
		try {
			decompiler.decompile();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		//TODO: use file walker to verify that class files exist
		fail("Not yet implemented");
		//File out = new File(testDir + testDir + "TestJavaFile.java");
		//assertTrue(out.exists());
	}

}
