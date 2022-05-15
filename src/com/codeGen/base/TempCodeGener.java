package com.codeGen.base;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.*;

import com.base.FileUtils;

public class TempCodeGener {
	
	// 指定要迭代的目录名
	private String inputFolder;
	private String outputFolder;
	
	//实例名称
	private HashMap<String, String> hashMap = null;
	
	public static  TempCodeGener newInstance(String inputFolder, String outputFolder, HashMap hashMap){
		TempCodeGener instance = new TempCodeGener();
		
		instance.inputFolder = inputFolder;
		instance.outputFolder = outputFolder;
		instance.hashMap = hashMap;
		return instance;
	}
	
	public void  doGen() throws IOException{
		FileUtils.copyDir(inputFolder, outputFolder);
		
		
		File outputDir = new File(outputFolder);
		List<File> folderList = new ArrayList<File>();
		//替换目录
//		collectFolders(outputDir,folderList);
//		Collections.reverse(folderList);
		collectFoldersSort(outputDir);

		folderList.clear();
		
		//替换文件
		collectFolders(outputDir,folderList);
		folderList.add(outputDir);
		renameFiles(collectFiles(folderList));
		replaceFileToken(collectFiles(folderList));
//		fixJarPath(collectFiles(folderList));

		System.out.println(outputFolder);
	}

	private void replaceFileToken(List<File> fileList2) {
		
		final Map<String, String> replaceMap = new HashMap<String, String>();
		for (Map.Entry<String, String> kv:hashMap.entrySet()) {
			replaceMap.put(kv.getKey(), "{"+kv.getValue()+"}");
		}
		
		for (File file : fileList2) {
			FileUtils.replace(file, replaceMap);
			
		}
		
	}
//	private void fixJarPath(List<File> fileList2) {
//		//修正 包路径的chainStore
//		final Map<String, String> fixReplaceMap = new HashMap<String, String>();
//		fixReplaceMap.put("chain{Pojo}", "chainStore");
//		
//		for (File file : fileList2) {
//			FileHelper.replace(file, fixReplaceMap);
//		}
//		
//	}

	private void renameFiles(List<File> fileListP) throws IOException {
		final Map<String, String> replaceMap = new HashMap<String, String>();
		for (Map.Entry<String, String> kv:hashMap.entrySet()) {
			replaceMap.put(kv.getKey(), "{"+kv.getValue()+"}");
		}
		for (File file : fileListP) {
			String newpath = file.getAbsolutePath();
			newpath = newpath.replace(outputFolder, "");
//			String newpath = null;
			for (String token : replaceMap.keySet()) {
				newpath = newpath.replace(token, replaceMap.get(token));
			}
			newpath = outputFolder + newpath;
//			//路径有chainStore，特殊处理
//			newpath = newpath.replace("chain{Pojo}", "chainStore");
			File dest = new File(newpath);
//			String absolutePath = dest.getAbsolutePath();
//			System.out.println(absolutePath);
			file.renameTo(dest);
//			Files.move(file.toPath(), dest.toPath(), StandardCopyOption .REPLACE_EXISTING);
		}
		
	}

	private File renameFolders(File file) throws IOException {
		final Map<String, String> replaceMap = new HashMap<String, String>();
		for (Map.Entry<String, String> kv:hashMap.entrySet()) {
			replaceMap.put(kv.getKey(), "{"+kv.getValue()+"}");
		}


		String newpath = file.getAbsolutePath();
		// 替换目录文件
        String relaPath = newpath.replace(outputFolder, "");
		for (String token : replaceMap.keySet()) {
            relaPath = relaPath.replace(token, replaceMap.get(token));
		}
		File dest = new File(outputFolder + relaPath);
//			String absolutePath = dest.getAbsolutePath();
//			System.out.println(absolutePath);
		Boolean flg = file.renameTo(dest);

		return dest;
//			Files.move(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		
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
		List<File> filelist = new ArrayList<File>();
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				folderList.add(file);
				collectFolders(file, folderList);
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
