package com.myerlangplugin.findChinese.help;

import com.base.FileUtils;
import com.myerlangplugin.checkcirculate.utils.FilterFiles;
import com.myerlangplugin.ckecktwolist.Regular;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChineseFile {
    public List<File> getAllFile(File dir){
//        FilterFiles files = new FilterFiles();
        List<File> fileList = FileUtils.getAllFilePathByDir(dir);
        fileList = getAllFiles(fileList, "");

        return fileList;
    }


    // 找到是否含有中文
    public List<ChinesePojo> getChinese(File file){

        List<ChinesePojo> list = new ArrayList<ChinesePojo>();

        try {

            HashMap<Integer,String> map = FileUtils.getLineByText(file);

            String modName = FilterFiles.getModName(file);

            getAllChinese(modName,map,list);

//            System.out.println(list.toString());

        } catch (IOException e) {

            e.printStackTrace();

        }
        return list;

    }

    public void getAllChinese(String modName,HashMap<Integer,String> map, List<ChinesePojo> list){
        for (Map.Entry<Integer,String> entry:map.entrySet()){

            Integer index = entry.getKey();

            String content = entry.getValue();

            content = FilterFiles.fileTextByAnnotation(content,'%');

            String chineseStr = getContext(content);

            if(chineseStr.equals("")){

            }else{
                ChinesePojo chinesePojo = new ChinesePojo();

                chinesePojo.setLine(index);

                chinesePojo.setModName(modName);

                chinesePojo.setChinese(chineseStr);

                list.add(chinesePojo);
            }
        }

    }

    public String getContext(String content){

        String pattern = "[\u4e00-\u9fa5]";

        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(content);

        StringBuilder sb = new StringBuilder();

        while (m.find()){
            sb.append(m.group(0));
        }
        return sb.toString();

    }

    public List<File> getAllFiles(List<File> list, String pattern){

        List<File> fileList = new ArrayList<File>();

        for (File f:list) {

            String name = f.getName();

            if(Regular.checkFile(name,".*.erl") && !Regular.checkFile(name,"^sys_.*")){ //判断是否是 .erl 结尾的文件

                fileList.add(f);

            };

        }

        return fileList;

    }

}
