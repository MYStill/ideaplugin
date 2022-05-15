package com.codeGen.angular.apiData;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.base.FileUtils;

public class ApiDataCodeGener {
	
	// 指定要迭代的目录名
	private String inputFolder;
	private String outputFolder;
	
	private String outPutFileName = "BUserDeviceApiData";
	
	private String separator = System.getProperty("line.separator");
	
	public static  ApiDataCodeGener newInstance(String input,String output){
		ApiDataCodeGener instance = new ApiDataCodeGener();
		
		instance.inputFolder = input;
		instance.outputFolder = output;
	
		return instance;
	}
	
	public void doGen() throws Exception{
		FileUtils.createFolder(new File(outputFolder));
		
		File inputDir = new File(inputFolder);
		List<File> folderList = new ArrayList<File>();
		collectFolders(inputDir,folderList);
		folderList.add(inputDir);
		
		List<File> fileList = collectFiles(folderList);
		List<com.codeGen.angular.apiData.ApiDataInfo> dataInfoList = new ArrayList<com.codeGen.angular.apiData.ApiDataInfo>();
		for (File file : fileList) {
			dataInfoList.add(getDataInfo(file));
		}
		
		
		
		genAngularFile(dataInfoList);
		
	}


	private void genAngularFile(List<com.codeGen.angular.apiData.ApiDataInfo> dataInfoList) throws Exception {
		
			
		for (com.codeGen.angular.apiData.ApiDataInfo apiDataInfo : dataInfoList) {
			StringBuilder sb = new StringBuilder();
			File outputFile = new File(outputFolder, apiDataInfo.getClassName()+".ts");
			
			if(apiDataInfo.isEnum()){
				appendEnumInfo(sb, apiDataInfo);
			}else{
				appendClassInfo(sb, apiDataInfo);
			}
			
			try {
				FileUtils.writeTxt(outputFile, sb.toString());
			} catch (Exception e) {
				System.out.println("error: "+outputFile.getPath());
			}
			
		}
		
		
	}
	
	private void appendEnumInfo(StringBuilder sb, com.codeGen.angular.apiData.ApiDataInfo apiDataInfo) {

//		export enum BUserUpdateType {
//			  updateInfo = 0,
//			  changePassword = 1
//			}

		
		String className = apiDataInfo.getClassName();

		
		sb.append("export enum "+className+" {").append(separator);
			
		LinkedList<com.codeGen.angular.apiData.ApiDataFieldInfo> linkedList = apiDataInfo.getFieldList();
		
		com.codeGen.angular.apiData.ApiDataFieldInfo fieldTmp = linkedList.pop();
		int index = 0;
		while(fieldTmp!=null){

			String fieldName = fieldTmp.getFieldName();
			if(StringUtils.contains(fieldName, "(")){
				fieldName = StringUtils.substringBeforeLast(fieldName, "(").trim();
			}
			sb.append("  ").append(fieldName).append(" = ").append(index).append(",");
			sb.append(separator);
			index++;
			fieldTmp = linkedList.pollFirst();
		}
		
		sb.append("}").append(separator);
		
	}

	private void appendClassInfo(StringBuilder sb, com.codeGen.angular.apiData.ApiDataInfo apiDataInfo) {


//		export class BUserAddApiForm {
//			  name: string;
//			  phone: string;
//			  password: string;
//			  headImg: string;
//			  gender: number;
//			  age: number;
//			  roleSet :Array<number>;
//			  verifyCode:string;//验证码
//
//			  checked:boolean;
//
//			  constructor(){}
//			}
		
		
		String className = apiDataInfo.getClassName();
		sb.append("export class "+className+" {").append(separator);
		sb.append("    constructor(){}").append(separator);
		
		for (com.codeGen.angular.apiData.ApiDataFieldInfo fieldTmp : apiDataInfo.getFieldList()) {
			String fSTType = com.codeGen.angular.apiData.StTypeHelper.getInstance().getSTType(fieldTmp.getFieldType());
			sb.append("    ").append(fieldTmp.getFieldName()).append(":").append(fSTType).append(";").append(separator);
		}		
		
		
		sb.append("}").append(separator);
		
	}

	private com.codeGen.angular.apiData.ApiDataInfo getDataInfo(File targetFile) {
		try {
			com.codeGen.angular.apiData.ApiDataInfo info = new com.codeGen.angular.apiData.ApiDataInfo();
			BufferedReader bufReader = new BufferedReader(new FileReader(targetFile));

			List<String> lineList = new ArrayList<String>();
			boolean isEnum = false;
			String lineTmp = null;
			while ((lineTmp = bufReader.readLine()) != null) {
				lineList.add(lineTmp);
				if(StringUtils.contains(lineTmp, "enum")){
					isEnum = true;
				}
				
			}
			bufReader.close();
			
			if(isEnum){
				info = handleEnum(lineList);
			}else{
				info = handleClass(lineList);
			}
			
			return info;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private com.codeGen.angular.apiData.ApiDataInfo handleEnum(List<String> lineList) {
		com.codeGen.angular.apiData.ApiDataInfo info = new com.codeGen.angular.apiData.ApiDataInfo();
		info.setEnum(true);
		String className;
		
		StringBuilder enumSumSb = new StringBuilder();
		boolean shouldAppend = false;
		for (String lineTmp : lineList) {
			if(shouldAppend){
				enumSumSb.append(lineTmp);
				if(StringUtils.contains(lineTmp, ";")){
					shouldAppend = false;
				}
			}
			if(StringUtils.contains(lineTmp, "enum") && StringUtils.contains(lineTmp, "{")){
				className = StringUtils.substringBetween(lineTmp, "enum", "{").trim();
				info.setClassName(className);
				shouldAppend = true;
			} 
				
		}
		
		
		String enumSum = StringUtils.substringBefore(enumSumSb.toString(), ";") ;
		
		String[] split = enumSum.split(",");
		for (String fieldNameTmp : split) {
			String trim = StringUtils.substringBefore(fieldNameTmp, "(").trim();
			if(StringUtils.isNotBlank(trim)){
				info.addFieldName(trim, null);
			}
		}
		
		return info;
	}

	private com.codeGen.angular.apiData.ApiDataInfo handleClass(List<String> lineList){
		com.codeGen.angular.apiData.ApiDataInfo info = new com.codeGen.angular.apiData.ApiDataInfo();
		for (String lineTmp : lineList) {
			lineTmp = handleComment(lineTmp);
			
			if(StringUtils.contains(lineTmp, "class") && StringUtils.contains(lineTmp, "{")){
				String className = StringUtils.substringBetween(lineTmp, "class", "{").trim();
				if(StringUtils.contains(className, "implements")){
					className = StringUtils.substringBetween(lineTmp, "class", "implements").trim();
				}
				info.setClassName(className);
			}
			
			
			
			if(StringUtils.contains(lineTmp, "private") && lineTmp.trim().endsWith(";")){
				String tmp = StringUtils.substringAfter(lineTmp, "private").trim();
				String fieldName = null;
				String filedType = StringUtils.substringBefore(tmp, " ").trim();
				if(StringUtils.contains(tmp, "=")){	
					fieldName = StringUtils.substringBetween(tmp, " ", "=").trim();
				}else{
					fieldName = StringUtils.substringBetween(tmp, " ", ";").trim();
				}
				info.addFieldName(fieldName,filedType);
			}
		}
		return info;
		
	}

	private String handleComment(String lineTmp) {
		
		if(StringUtils.contains(lineTmp, "//")){
			lineTmp = StringUtils.substringBefore(lineTmp, "//");
		}
		
		return lineTmp;
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

	public String getOutPutFileName() {
		return outPutFileName;
	}

	public void setOutPutFileName(String outPutFileName) {
		this.outPutFileName = outPutFileName;
	}

}
