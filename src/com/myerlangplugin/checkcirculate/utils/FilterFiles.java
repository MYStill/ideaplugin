package com.myerlangplugin.checkcirculate.utils;

import com.base.FileUtils;
import com.myerlangplugin.checkcirculate.pojo.ModAndMethod;
import com.myerlangplugin.checkcirculate.pojo.ModContact;
import com.myerlangplugin.ckecktwolist.Regular;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterFiles {

    public FilterFiles(){


    }

    public FilterFiles(String filPath){


    }

    /**
     *  得到当前目录的所有 erl 文件
     *  正则参数
     */
    public List<File> getAllFiles(List<File> list, String pattern){

        List<File> fileList = new ArrayList<File>();

        for (File f:list) {

            String name = f.getName();

            if(Regular.checkFile(name,".*.erl")){ //判断是否是 .erl 结尾的文件

                fileList.add(f);

            };

        }

        return fileList;

    }

    public HashMap<String,ModContact> getAllModContact(List<File> list){

        HashMap<String,ModContact> modList = new HashMap<String,ModContact>();

        ModContact modContact = null;

        String modName = "";

        for (File f:list) {

            modContact = new ModContact();

            modName = getModName(f);

            modContact.setModName(modName);

            modContact.setList(getModAndMethod(f));

            modList.put(modName,modContact);

        }

        return modList;

    }
    public static String getModName(File f){

        int end = 0;

        String fileName = "";

        fileName = f.getName();

        end = fileName.lastIndexOf(".");

        end = end == -1 ? fileName.length() : end;

        return fileName.substring(0,end);

    }
    public static String getModName(String fileName){
        int start = 0;

        int start1 = 0;

        int start2 = 0;

        int end = 0;

        start1 = fileName.lastIndexOf("\\");

        start2 = fileName.lastIndexOf("/");

        start = Math.max(start1, start2);

        start= start == -1 ? 0 : start;


        end = fileName.lastIndexOf(".");

        end = end == -1 ? fileName.length() : end;

        return fileName.substring(start + 1,end);

    }

    public List<ModAndMethod> getModAndMethod(File file1){

        List<ModAndMethod> list = new ArrayList<ModAndMethod>();


        try {

            HashMap<Integer,String> map = FileUtils.getLineByText(file1);

            String modName = getModName(file1);

            getModAndMethodByText(modName,list,map);

//            System.out.println(list.toString());

        } catch (IOException e) {

            e.printStackTrace();

        }

        return list;

    }

    public void getModAndMethodByText(String modName, List<ModAndMethod> list, HashMap<Integer,String> map){

        for (Map.Entry<Integer,String> entry:map.entrySet()){

            Integer index = entry.getKey();

            String content = entry.getValue();

            content = fileTextByAnnotation(content,'%');

            getContext(modName,list,index,content);
        }

    }


    public void getContext(String modName,List<ModAndMethod> list,Integer index ,String content) {

//        List resultList = new ArrayList();

        Pattern p = Pattern.compile("(\\w+):(\\w+)");//正则表达式

        Matcher m = p.matcher(content);//

        ModAndMethod modAndMethod = null;

//        String modName = "";

        String[] filterMod = {}; // 过滤列表

        while (m.find()) {

//            if(filterMod.contact)

            modAndMethod = new ModAndMethod();

            modAndMethod.setCurMod(modName);

            modAndMethod.setLine(index);

            modAndMethod.setMethod(m.group(2));

            modAndMethod.setMod(m.group(1));

            modAndMethod.setModAndMethod(m.group(0));

//            System.out.println(m.group(0));

            list.add(modAndMethod);

        }

    }

    /**
     * 过滤注释掉的文件
     * 百分号规则，暂时不考虑 ‘%’ “%”
     * @return
     */
    public static String fileTextByAnnotation(String s, char c){

//        c = '%';

        char[] charList = s.toCharArray();

        int len = charList.length;

        int end = -1;

        for (int i = 0; i < len; i ++ ){

            if(charList[i] == c){

                break;
            }

            end = i;

        }

        return new String(charList,0,end + 1);
    }

    public HashMap<String, ModContact> getModContactByDir(String dir){

        List<File> list = FileUtils.getAllFilePathByDir(dir);

        return getAllModContact(getAllFiles(list,""));

    }

    public static String ChangeSepatator(String path){
        return path.replace("\\","/");
    }

//    public static void main(String[] args) {
//
//        FilterFiles filterFiles = new FilterFiles();
//
//
//    }

}
