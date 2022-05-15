package com.myerlangplugin.cfg;

import com.utils.log.handle.Log;

import java.io.*;
import java.util.Iterator;
import java.util.Properties;


public class CfgProperties {
    public static final String FILE_PATH = "\\codeGenTmpDir\\";
    public static final String FILE_PATH1 = "\\codeGenTmpDir\\cfg\\";
    public static final String FILE = "\\codeGenTmpDir\\cfg\\properties.properties";

    private static void createFile(String workPath) {
        try {
            File file = new File(workPath + FILE_PATH );
            System.out.println(file.getAbsolutePath());
            if(!file.exists()){
                file.mkdirs();
            }
            File file1 = new File(workPath + FILE_PATH1 );
            if(!file1.exists()){
                file1.mkdirs();
            }
            File file2 = new File(workPath + FILE );
            if(!file2.exists()) {
                file2.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getConfig(String key, String workPath, String Default) {
        String value = Default; // 改默认为相对路径
        String key1 = "";
        Properties prop = new Properties();
        try{
            //读取属性文件a.properties
            File file = new File(workPath + CfgProperties.FILE);
            if(file.exists()){
                InputStream in = new BufferedInputStream(new FileInputStream(workPath + CfgProperties.FILE));
                prop.load(in);     ///加载属性列表
                Iterator<String> it=prop.stringPropertyNames().iterator();
                while(it.hasNext()){
                    key1 = it.next();
                    if(key.equals(key1)){
                        value = prop.getProperty(key);
                    }
                }
                in.close();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return value;
    }
    public static void setConfig(String key, String Value, String workPath) throws IOException {
        Properties prop = new Properties();
        createFile(workPath);
        ///保存属性到b.properties文件
        FileOutputStream oFile = new FileOutputStream(workPath + CfgProperties.FILE, true);//true表示追加打开
//        prop.load(in);     ///加载属性列表
//        Iterator<String> it=prop.stringPropertyNames().iterator();
        oFile.close();
        prop.setProperty(key, Value);
        prop.store(oFile, "123456789");
    }
    public static void main(String[] args) throws IOException {

    }
}