/*
 * $HeadURL: http://150.236.80.220/dev/dsc/dmm/branches/dmm-2.1.1-13-AOP321-14-maint/src/com/drutt/dmm/util/FileUtils.java $
 * $Id: FileUtils.java 858830 2010-09-15 15:12:04Z eligchn $
 * Copyright (c) 2009 by Ericsson, all rights reserved.
 */

package com.base;

import com.myerlangplugin.ckecktwolist.Regular;

import java.io.*;
import java.util.*;


public class FileUtils {
	
	
	public static String upFirstChar(String input) {
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}
	
	public static String lowFirstChar(String input) {
		return input.substring(0, 1).toLowerCase() + input.substring(1);
	}
	
	public static void openFolder(String filePath){
		try {  
            String[] cmd = new String[5];  
            cmd[0] = "cmd";  
            cmd[1] = "/c";  
            cmd[2] = "start";  
            cmd[3] = " ";  
            cmd[4] = filePath;  
            Runtime.getRuntime().exec(cmd);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}

	public static void replace(File targetFile, Map<String, String> replaceMap) {
		try {
			Set<String> tokenSet = replaceMap.keySet();

			BufferedReader bufReader = new BufferedReader(new FileReader(targetFile));

			StringBuffer strBuf = new StringBuffer();

			String lineTmp = null;
			while ((lineTmp = bufReader.readLine()) != null) {
				for (String token : tokenSet) {
					lineTmp = lineTmp.replace(token, replaceMap.get(token));
				}
				strBuf.append(lineTmp);
				strBuf.append(System.getProperty("line.separator"));
			}

			bufReader.close();

			BufferedWriter bufWriter = new BufferedWriter( new FileWriter(targetFile));
			bufWriter.write(strBuf.toString());
			
			bufWriter.flush();
			bufWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void copyDir(String sourceDirPath, String destDirPath) throws IOException {

		if (!(new File(destDirPath)).exists()) {
			(new File(destDirPath)).mkdir();
		}
		File sourceDir = new File(sourceDirPath);

		String[] fileArray = sourceDir.list();

		for (int i = 0; i < fileArray.length; i++) {
			String sourceFilePathTmp = sourceDirPath + File.separator + fileArray[i];
			String destFilePathTmp = destDirPath + File.separator + fileArray[i];
			if ((new File(sourceFilePathTmp)).isDirectory()) {
				copyDir(sourceFilePathTmp, destFilePathTmp);
			}

			if (new File(sourceFilePathTmp).isFile()) {
				copyFile(sourceFilePathTmp, destFilePathTmp);
			}

		}
	}

	public static void copyFile(String oldPath, String newPath) throws IOException {
		File oldFile = new File(oldPath);
		File file = new File(newPath);
		// 改用字符流
		FileReader in = new FileReader(oldFile);
		FileWriter out = new FileWriter(file);
		;

		char[] buffer = new char[1024];
		int len = -1;
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer,0,len);
		}
		in.close();
		out.close();
	}

	public static void deleteAll(File file) {
		if (!file.exists()) {
			return;
		}

		if (file.isFile()) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteAll(files[i]);
				files[i].delete();
			}
		}
		//删除完后检查自己是否可删除
		if(file.isDirectory()&& file.list().length == 0){
			file.delete();
		}
	}
	
	
	/** Revision of the class */
	public static final String _REV_ID_ = "$Revision: 858830 $";

	/**
	 * Whether the specified folder is empty.
	 * 
	 * @param folder
	 * @return
	 */
	public static boolean isEmptyFolder(String folder) {
		boolean isEmpty = true;
		File f = new File(folder);
		if (f.exists()) {
			String[] fileNames = f.list();
			if (fileNames != null && fileNames.length > 0) {
				isEmpty = false;
			}
		}
		return isEmpty;
	}

	/**
	 * Will create all folders for folder.
	 * 
	 * @param folder
	 *            the folder (and subfolders) to be created
	 */
	public static void createFolder(File folder) {
		if (!folder.exists()) {
			if (!folder.mkdirs()) {
				throw new RuntimeException("Failed to create folder for upload servlet: " + folder.getPath());
			}
		}
	}

	/**
	 * Will copy a file from source to destination.
	 * 
	 * @param source
	 *            Source file
	 * @param dest
	 *            Destination file
	 * @return True if nothing goes wrong
	 */
	public static boolean copyFile(File source, File dest) {
		try {
			// 改用 字符流
			FileWriter os = new FileWriter(dest);
			FileReader is = new FileReader(source);
			char[] buf = new char[1024];
			int len;
			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			os.flush();
			os.close();
			is.close();
		} catch (Exception x) {
			return false;
		}
		return true;
	}

	/**
	 * Will try and delete file .
	 * 
	 * @param f
	 *            The file to be deleted
	 * @return True if the file was successfully deleted
	 */
	public static boolean deleteFile(File f) {
		if (f.exists() && f.canWrite()) {
			try {
				return (f.delete());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static void reNameFiles(String oriFileDirPath, String namePrefix) {

		File oriFileDir = new File(oriFileDirPath);

		int index = 1;
		for (File fileTmp : oriFileDir.listFiles()) {
			File dest = new File(oriFileDirPath + "/" + namePrefix + "_" + index);
			fileTmp.renameTo(dest);
			index++;
		}

	}

	public static String readTxt(File file) throws Exception {
		final String encoding = "UTF-8";
		return readTxt(file, encoding);
	}
	public static String readTxt(File file, String encoding) throws Exception {
		StringBuilder content = new StringBuilder();
		if (file.isFile() && file.exists()) { // 判断文件是否存在
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
			BufferedReader bufferedReader = null;
			try {
				bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					content.append(lineTxt);
				}
			} finally {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			}
		}
		return content.toString();
	}

	public static void writeTxt(File file, String fileContent) throws Exception {
		final String encoding = "UTF-8";
		writeTxt(file, fileContent, encoding);
	}
	
	public static void writeTxt(File file, String fileContent, String encoding) throws Exception {
		if (!file.exists()) {
			file.createNewFile();
		}
		OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), encoding);
		BufferedWriter writer = new BufferedWriter(write);
		try {
			writer.write(fileContent);
			writer.flush();
		} finally {
			writer.close();
		}
	}

	public static void getFiles(String path, List<File> fileslist) {
		File file = new File(path);
		// 如果这个路径是文件夹
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			fileslist.add(file);
			// 获取路径下的所有文件
			for (int i = 0; i < files.length; i++) {
				// 如果还是文件夹 递归获取里面的文件 文件夹
				if (files[i].isDirectory()) {
					getFiles(files[i].getPath(),fileslist);
				} else {
					fileslist.add(files[i]);
				}

			}
		} else {
			fileslist.add(file);
		}
	}

	/**
	 * 得到目录下所有的文件路径
	 * @param path
	 * @param path
	 */

	public static List<File> getAllFilePathByDir(String path){
		File dirFile = new File(path);
		if(dirFile.exists()){
			return FileUtils.getAllFilePathByDir(dirFile);
		}else{
			return null;
		}

	}
	public static List<File> getAllFilePathByDir(File path){
		List<File> fileList = new ArrayList<File>();

		FileUtils.getFiles2(path,fileList);

        return fileList;
	}
	public static List<File> getAllErlFiles(List<File> list){

		List<File> fileList = new ArrayList<File>();

		for (File f:list) {

			String name = f.getName();

			if(Regular.checkFile(name,".*.erl")){ //判断是否是 .erl 结尾的文件

				fileList.add(f);

			};

		}
		return fileList;

	}

	public static void getFiles2(File file, List<File> fileslist) {
		// 如果这个路径是文件夹
		if (file.isDirectory()) {
			File[] files = file.listFiles();
//			fileslist.add(file); 不要目录，只要文件
			// 获取路径下的所有文件
			for (int i = 0; i < files.length; i++) {
				// 如果还是文件夹 递归获取里面的文件 文件夹
				if (files[i].isDirectory()) {
					getFiles2(files[i],fileslist);
				} else {
					fileslist.add(files[i]);
				}

			}
		} else {
			fileslist.add(file);
		}
	}


	public static HashMap<File,File>  getrepeatFiles(List<File> fileslist) throws IOException {
		HashMap<File,File> hsahmap = new HashMap<File,File>();
		for (File f:fileslist) {
			checkrepeatname(f,fileslist,hsahmap);
		}
		return hsahmap;

	}

	public static void checkrepeatname(File tarFile, List<File> fileList,HashMap<File,File> hsahmap)  throws IOException{
		String tarName = tarFile.getName();
		File scrFile = null;
		for (int i = 0;i < fileList.size(); i++){
			scrFile = fileList.get(i);
			String srcName = scrFile.getName();
			if(srcName.equals(tarName) && !scrFile.getPath().equals(tarFile.getPath())){
				if(!hsahmap.containsKey(scrFile)){
					hsahmap.put(tarFile,scrFile);
				}
				break;
			}else{
				continue;
			}
		}
	}

	/**
	 * 得到文本文件的每一行并且以《行号，String 内容》 返回map
	 * @return
	 */
	public static HashMap<Integer,String> getLineByText(File filePath) throws IOException {
		HashMap<Integer,String> map = new HashMap<Integer,String>();
//		File filePath = new File("D:\\AB.txt");
		String s = null;

		BufferedReader br=new BufferedReader(new FileReader(filePath));

		Integer index = 0; // 行数计数器

		while ((s = br.readLine()) != null){

			index ++;

			map.put(index,s.toString());
//			System.out.println(index + "----" + s + "\r\n");
		}
        return map;

	}

	public static void main(String[] args) throws Exception {
		File file1 = new File("D:\\bstest\\ErlangPlugin\\src\\com\\myerlangplugin\\PluginHelper.java");
//		getrepeatFiles(getAllFilePathByDir(file1));
//		File file2 = new File("D:\\bstest\\ErlangPlugin\\src\\com\\text\\Test2.java");
////		getAllFilePathByDir("D:\\bstest\\ErlangPlugin\\src");
		FileUtils.getLineByText(file1);
//		System.out.println(readTxt(file1, "UTF-8"));
	}

}
