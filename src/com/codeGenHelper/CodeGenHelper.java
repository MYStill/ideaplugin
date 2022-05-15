package com.codeGenHelper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.base.FileUtils;
import com.codeGen.base.BSCodeGener;
import com.codeGen.base.TempCodeGener;

public class CodeGenHelper {
	
	
	private File inputDir;
	private File tempDir;
	private File outputDir;
	
	public static CodeGenHelper newInstance(File inputDir, File tempDir, File outputDir){
		CodeGenHelper target = new CodeGenHelper();
		target.inputDir = inputDir;
		target.tempDir = tempDir;
		target.outputDir = outputDir;
		return target;
	}

	private HashMap<String, String> bsHashMap = null;
	
	public CodeGenHelper withBsClass(HashMap hashMap){
		
		this.bsHashMap = hashMap;
		
		return this;
	}
	
	private HashMap<String, String> temHashMap = null;
	
	public CodeGenHelper withTempClass(HashMap hashMap){
		
		this.temHashMap = hashMap;
		
		return this;
	}
	
	private void doGenTemp() throws IOException {
		String input = inputDir.getAbsolutePath();
		String output = tempDir.getAbsolutePath();
		TempCodeGener.newInstance(input, output, temHashMap).doGen();
		
	}
	

	public void doGenBS() throws IOException {
		this.doGenTemp();
		String input = tempDir.getAbsolutePath();
		String output = outputDir.getAbsolutePath();
		BSCodeGener.newInstance(input, output, bsHashMap).doGen();
		
		FileUtils.openFolder(output);
		
	}

	



	
	
}
