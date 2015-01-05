package test.java.org.norc.utils.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import main.java.org.norc.utils.decompiler.controller.Decompiler;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DecompileTest {

	@Test
	public void testCopyJavaFileToOutDir() {
		// move this to the setUpBeofreTestSuite method
		Decompiler decompiler = new Decompiler(); 
		decompiler.setInputDir( Paths.get("src/test/resources/input/") );
		decompiler.setOutputDir( Paths.get("src/test/resources/output/"));
		
		Path testJavaFile = Paths.get("src/test/resources/input/TestJavaFile.java");
		Path subDir = Paths.get("level1/level2/level3/");
		Path newFile = null;
		try {
			newFile = decompiler.copyJavaFileToOutDir(testJavaFile, subDir);
			assert(FileUtils.contentEquals(testJavaFile.toFile(),newFile.toFile()));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
			newFile.toFile().delete();
		}
	}

}
