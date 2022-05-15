package com.codeGen.angular.apiData;

import java.util.LinkedList;

public class ApiDataInfo {

	private boolean isEnum = false;
	private String className;
	private LinkedList<ApiDataFieldInfo> fieldList = new LinkedList<ApiDataFieldInfo>();
	
	public void addFieldName(String fieldName, String fieldType){
		fieldList.add(ApiDataFieldInfo.newInstance(fieldName, fieldType));
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}

	public boolean isEnum() {
		return isEnum;
	}

	public void setEnum(boolean isEnum) {
		this.isEnum = isEnum;
	}

	public LinkedList<ApiDataFieldInfo> getFieldList() {
		return fieldList;
	}
	
}
