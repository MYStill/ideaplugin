package com.codeGen.angular.apiData;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class StTypeHelper {

	private static StTypeHelper instance = new StTypeHelper();
	public static StTypeHelper getInstance(){
		return instance;
	}
	private Map<String,String> typeMap = new HashMap<String,String>();
	
	public StTypeHelper(){
		
		typeMap.put("String", "string");
		
		typeMap.put("long", "number");
		typeMap.put("short", "number");
		typeMap.put("int", "number");
		typeMap.put("float", "number");
		typeMap.put("double", "number");
		typeMap.put("boolean", "boolean");
		
		typeMap.put("Long", "number");
		typeMap.put("Short", "number");
		typeMap.put("Int", "number");
		typeMap.put("Float", "number");
		typeMap.put("Double", "number");
		typeMap.put("Boolean", "boolean");
		
		typeMap.put("List<String>", "Array<string>");

		typeMap.put("List<long>", "Array<number>");
		typeMap.put("List<short>", "Array<number>");
		typeMap.put("List<int>", "Array<number>");
		typeMap.put("List<float>", "Array<number>");
		typeMap.put("List<double>", "Array<number>");
		typeMap.put("List<boolean>", "Array<boolean>");
		
		typeMap.put("List<Long>", "Array<number>");
		typeMap.put("List<Short>", "Array<number>");
		typeMap.put("List<Int>", "Array<number>");
		typeMap.put("List<Float>", "Array<number>");
		typeMap.put("List<Double>", "Array<number>");
		typeMap.put("List<Boolean>", "Array<boolean>");		
		
	}
	
	public String getSTType(String javaType){
		String stType = typeMap.get(javaType);
		if(StringUtils.isBlank(stType)){
			stType = javaType;
		}
		return stType;
	}
	
	
}
