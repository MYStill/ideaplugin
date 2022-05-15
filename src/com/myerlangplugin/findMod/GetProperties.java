package com.myerlangplugin.findMod;

import com.base.FileUtils;
import com.myerlangplugin.checkcirculate.utils.FilterFiles;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GetProperties {
    private String includestr = "include:";
    private String exincludestr = "exinclude:";
    private int iType = 0;
    public static List<String> includelist = new ArrayList<String>();
    public static List<String> exIncludelist = new ArrayList<String>();
    public void InitProp(String path){
        try {
            includelist.clear();
            exIncludelist.clear();

            BufferedReader in = new BufferedReader(new FileReader(path));
            String str;
            while ((str = in.readLine()) != null) {
                if(SwitchType(str)){
                    AddStr(str);
                   // System.out.println(FilterFiles.getModName("D:\\bstest\\ErlangPlugin\\.\\src\\test\\erlang\\aaa.erl"));
                };
            }
        } catch (IOException e) {
        }
    }

    public Boolean SwitchType(String str){
        if(str.equals(includestr)){
            this.iType = 1;
            return false;
        }else if(str.equals(exincludestr)){
            this.iType = 2;
            return false;
        }
        return true;
    }

    public void AddStr(String str){
        if(this.iType == 1){
            includelist.add(str);
        }else if(this.iType == 2){
            exIncludelist.add(str);
        }
    }

    public static void main(String[] args) {
       GetProperties g = new GetProperties();
       g.InitProp("D:\\bstest\\ErlangPlugin\\codeGenTmpDir\\filter.text");
    }
}
