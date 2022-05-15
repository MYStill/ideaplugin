package com.codeGen.angular.apiData;

public class ApiDataFieldInfo {

	private String fieldName;
	private String fieldType;
	
	public static ApiDataFieldInfo newInstance(String fName,String fType){
		ApiDataFieldInfo fInfo = new ApiDataFieldInfo();
		fInfo.fieldName = fName;
		fInfo.fieldType = fType;
		return fInfo;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	
	
	
	
}
