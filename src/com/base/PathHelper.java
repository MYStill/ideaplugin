package com.base;

import org.apache.commons.lang3.StringUtils;

public class PathHelper {
	private static final String separator = "/";
	
	public static String joinPaths(String... paths) {
		String result = "";
		for (String path : paths) {
			result=join(result, convertSeparator(path));
		}
		return result;
	}
	
	private static String join(String path1, String path2) {
		return convertSeparator(path1) + separator + convertSeparator(path2);
	}

	public static String getSuffix(String fullPath) {
		return StringUtils.substringAfterLast(fullPath, ".");
	}
	
	public static String getFileName(String fullPath) {
		return StringUtils.substringAfterLast(convertSeparator(fullPath), separator);
	}
	
	private static String convertSeparator(String path){
		path = path.replaceAll("\\\\", separator);
		if(path.endsWith(separator)){
			path = path.substring(0, path.lastIndexOf(separator));
		}
		if(path.startsWith(separator)){
			path = path.substring(separator.length(), path.length());
		}
		return path;
	}

	public static void main(String[] args){
		String pathname = "D:\\allen\\prjhq\\prjsaas\\chainStore\\code\\java\\zmDevHelper\\doc\\text\\StoreAPI.java";
		System.out.println(PathHelper.getFileName(pathname));
		System.out.println(PathHelper.getSuffix(pathname));
		System.out.println(PathHelper.joinPaths("D:\\allen\\prjhq\\prjsaas", "/kkk", "\\eee"));
	}
}
