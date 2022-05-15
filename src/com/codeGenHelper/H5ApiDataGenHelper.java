package com.codeGenHelper;

import java.io.File;

import com.base.FileUtils;
import com.codeGen.angular.apiData.ApiDataCodeGener;

public class H5ApiDataGenHelper {

	
	public static H5ApiDataGenHelper newGenHelper(File inputDir, File outputDir){
		H5ApiDataGenHelper target = new H5ApiDataGenHelper();
		
		target.inputDir = inputDir;
		target.outputDir = outputDir;
		
		return target;
	}
	
	private File inputDir;
	private File outputDir;
	

	public void doGen() throws Exception {
		String input = inputDir.getAbsolutePath();
		String output = outputDir.getAbsolutePath();
		ApiDataCodeGener.newInstance(input,output).doGen();
		
		FileUtils.openFolder(output);
	}
	
	
}
