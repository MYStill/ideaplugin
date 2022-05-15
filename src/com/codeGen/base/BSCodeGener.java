package com.codeGen.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.FileUtils;

public class BSCodeGener {

	// 指定要迭代的目录名
	private String inputFolder = "";
	private String outputFolder = "";

	//实例名称
	private HashMap<String, String> hashMap = null;
	
	
	public static  BSCodeGener newInstance(String inputFolder,String outputFolder, HashMap hashMap){
		BSCodeGener instance = new BSCodeGener();
		
		instance.inputFolder = inputFolder;
		instance.outputFolder = outputFolder;
		instance.hashMap = hashMap;
		return instance;
	}
	
	public void doGen() throws IOException{
		FileUtils.copyDir(inputFolder, outputFolder);
		
		
		File outputDir = new File(outputFolder);
		//修改目录
		List<File> folderList = new ArrayList<File>();
		collectFoldersSort(outputDir);
		folderList.clear();

		//修改文件
		collectFolders(outputDir,folderList);
		folderList.add(outputDir);
		renameFiles(collectFiles(folderList));
		replaceFileToken(collectFiles(folderList));
		
		System.out.println("BSCodeGener.doGen() finish, outputFolder:");
		System.out.println(outputFolder);
	}

	private void replaceFileToken(List<File> fileList2) {
		
		final Map<String, String> replaceMap = new HashMap<String, String>();

		for (Map.Entry<String, String> kv:hashMap.entrySet()) {
			replaceMap.put("{"+kv.getValue()+"}", kv.getValue());
		}
		
		for (File file : fileList2) {
			FileUtils.replace(file, replaceMap);
			
		}
		
	}

	private void renameFiles(List<File> fileListP) {
		final Map<String, String> replaceMap = new HashMap<String, String>();

		for (Map.Entry<String, String> kv:hashMap.entrySet()) {
			replaceMap.put("{"+kv.getValue()+"}", kv.getValue());
		}
		for (File file : fileListP) {
			String newpath = file.getAbsolutePath();
			newpath = newpath.replace(outputFolder, "");
//			String newpath = null;
			for (String token : replaceMap.keySet()) {
				newpath = newpath.replace(token, replaceMap.get(token));
			}
			newpath = outputFolder + newpath;
			File dest = new File(newpath);
//			String absolutePath = dest.getAbsolutePath();
//			System.out.println(absolutePath);
			file.renameTo(dest);
		}
		
	}

	private File renameFolders(File file) {
		final Map<String, String> replaceMap = new HashMap<String, String>();

		for (Map.Entry<String, String> kv:hashMap.entrySet()) {
			replaceMap.put("{"+kv.getValue()+"}", kv.getValue());
		}
		String newpath = file.getAbsolutePath();
//		String newpath = null;
		for (String token : replaceMap.keySet()) {
			newpath = newpath.replace(token, replaceMap.get(token));
		}
		File dest = new File(newpath);
		file.renameTo(dest);
		return dest;
	}

	public List<File> collectFiles(List<File> folderList) {
		List<File> fileList = new ArrayList<File>();
		
		for (File folder : folderList) {
			File[] files = folder.listFiles();
			for (File filetmp : files) {
				if(!filetmp.isDirectory()){
					fileList.add(filetmp);
				}
			}
		}
		
		return fileList;
	}
	public void collectFolders(File dir,List<File> folderList) {
		
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				folderList.add(file);
				collectFolders(file,folderList);
			} 
		}
	}

	public void collectFoldersSort(File dir) throws IOException {
		List<File> dirList = new ArrayList<File>();
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				dirList.add(renameFolders(file));
			}
		}
		collectFoldersSort(dirList);
	}
	public void collectFoldersSort(List<File> dirList) throws IOException {
		List<File> filelist = new ArrayList<File>();
		for (File dir : dirList) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					filelist.add(renameFolders(file));
				}
			}
		}
		if(!filelist.isEmpty()){
			collectFoldersSort(filelist);
		}
	}

}
