package com.myerlangplugin.ckecktwolist;

import com.base.FileUtils;

import java.io.File;
import java.util.List;

public class CheckTwoListHelp {
    public void checkfilelist(String src,String pattern1,String pattern2,List<String> list) throws Exception {
        List<File> list1 = FileUtils.getAllFilePathByDir(src);
//        System.out.println(list1.size());
        if(list1 == null){
//            Messages.showErrorDialog(src + "路径下没有文件","路径错误");
        }else{
            for (File f:list1) {
                if(Regular.checkFile(f.getName(),pattern2)){
//                    System.out.println(f.getName());
                    String fileStr = FileUtils.readTxt(f,"UTF-8");
//                    System.out.println(fileStr);
//                    System.out.println(pattern1);
                    Regular.getMatckString(pattern1,fileStr,list);
                }
            }
        }
        System.out.println(list);
    }

}
