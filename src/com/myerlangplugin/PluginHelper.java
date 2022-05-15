package com.myerlangplugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.base.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class PluginHelper {

    private File codeGenInputDir;
    private File codeGenTempDir;
    private File codeGenOutputDir;


    public static PluginHelper newInstance(){
        return new PluginHelper();
    }


    // 返回第一个
    public File prepareCodeGen(String workPath, String filePath) throws IOException {
        if (filePath != null) {

            String prjAbsPath = "D:";
            if (workPath != null) {
                prjAbsPath = workPath;
            }
            File codeGenDir = new File(prjAbsPath + "\\codeGenTmpDir");
            FileUtils.deleteAll(codeGenDir);

            codeGenInputDir = new File(prjAbsPath + "\\codeGenTmpDir\\input");
            codeGenTempDir = new File(prjAbsPath + "\\codeGenTmpDir\\temp");
            codeGenOutputDir = new File(prjAbsPath + "\\codeGenTmpDir\\output");

            if (!codeGenDir.exists()) {
                codeGenDir.mkdir();
            }
            if (!codeGenInputDir.exists()) {
                codeGenInputDir.mkdir();
            }
            if (!codeGenTempDir.exists()) {
                codeGenTempDir.mkdir();
            }
            if (!codeGenOutputDir.exists()) {
                codeGenOutputDir.mkdir();
            }

            List<File> selectedFileList = getSelectedFileList(filePath);
//            for (File f:selectedFileList
//                 ) {
//                System.out.println("f " + f.getPath());
//
//            }
            copy2CodeGenInputDir(selectedFileList, codeGenInputDir);
            return selectedFileList.get(0);
        }
        return null;
    }

    public List<File> getSelectedFileList(String FilePath) {
        List<File> list = new ArrayList<File>();
        File filePath = new File(FilePath);
        list.add(filePath);
//        if (filePath.isDirectory()) {
//            File[] files = filePath.listFiles();
//            for (int i = 0; i < files.length; i++) {
//                list.add(files[i]);
//            }
//        }else{
//
//        }
//        FileUtils.getFiles(FilePath,list);
        return list;
    }

    private void copy2CodeGenInputDir(List<File> fileList, File codeGenInputDir) throws IOException {

        for (File fileTmp : fileList) {
            String fileName = fileTmp.getName();
            String targetFilePath = codeGenInputDir.getAbsolutePath() + "\\" + fileName;
//            System.out.println("targetFilePath" + targetFilePath);
            if (fileTmp.isDirectory()) {
                FileUtils.copyDir(fileTmp.getAbsolutePath(), targetFilePath);
            } else {
                FileUtils.copyFile(fileTmp, new File(targetFilePath));
            }
        }

    }

    public String getClassName(File dir) {
        String absolutePath = dir.getAbsolutePath();
        String className = StringUtils.substringAfterLast(absolutePath, "\\");
        return FileUtils.upFirstChar(className);
    }

    /**
     * 得到文本 token
     * @return
     */
    public static HashMap getTokenMap(String text){
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String[] split = text.split("\\r?\\n");
//        String[] split = text.split(System.lineSeparator());
//        System.out.println(System.getProperty("line.separator").equals("\r\n"));
        String[] temSplit = null;
        String srcToken = null;
        String tarToken = null;
        for (String ss:split) {
            temSplit = ss.split("-");
            if(temSplit.length == 2){
                srcToken = temSplit[0].trim();
                tarToken = temSplit[1].trim();
                if(!srcToken.equals("") && !tarToken.equals("")){
                    hashMap.put(srcToken, tarToken);
                }
            }
        }
        return hashMap;
    }


    public File getCodeGenInputDir() {
        return codeGenInputDir;
    }

    public File getCodeGenTempDir() {
        return codeGenTempDir;
    }

    public File getCodeGenOutputDir() {
        return codeGenOutputDir;
    }

    public static void main(String[] Args) {
        PluginHelper pp = new PluginHelper();
//        try {
////            File file= pp.prepareCodeGen(null,"D:\\bstest\\ErlangPlugin\\src\\com");
////            System.out.println("file" + file.getPath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//        }
            String text = "aa - bb\n\rcc - dd";
            System.out.println(pp.getTokenMap(text).size());
//        File f = new File("D:/aa/asd/asd");
//        String ClassName2 = pp.getClassName(f);
//        System.out.println("ClassName2" + ClassName2);
    }

}


