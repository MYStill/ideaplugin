package com.myerlangplugin.findMod;

import a.f.S;
import com.base.FileUtils;
import com.myerlangplugin.checkcirculate.pojo.ModAndMethod;
import com.myerlangplugin.checkcirculate.pojo.ModContact;
import com.myerlangplugin.checkcirculate.utils.FilterFiles;
import com.myerlangplugin.ckecktwolist.Regular;
import com.utils.log.handle.Log;
import org.bouncycastle.math.raw.Mod;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindModHelper {
    public static List<String> include_list = new ArrayList<String>();

    public static List<String> exinclude_list = new ArrayList<String>();

    public static List<String> self_list = new ArrayList<String>();

    /**
     *  得到当前目录的所有 erl 文件
     *  正则参数
     */
    public List<File> getAllErlFiles(List<File> list){

        List<File> fileList = new ArrayList<File>();

        for (File f:list) {

            String name = f.getName();

            if(Regular.checkFile(name,".*.erl")){ //判断是否是 .erl 结尾的文件

                fileList.add(f);

            };

        }

        return fileList;

    }


    public void init(String workPath, String selfStr){

        // 清除内容保证是最新的
        include_list.clear();
        exinclude_list.clear();
        self_list.clear();
        AddInclude(workPath, GetProperties.includelist);

        AddExInclude(GetProperties.exIncludelist);;

        List<File> selfList = FileUtils.getAllFilePathByDir(selfStr);

        selfList = getAllErlFiles(selfList);

        for (File f: selfList) {
            self_list.add(f.getAbsolutePath());
        }

    }

    // 添加包含文件
    public void AddInclude(String workPath, List<String> list){
        File dir = null;
        List<File> filelist = null;
        for (String includeStr:list) {
            dir = new File(workPath + includeStr);

            if(dir.isDirectory()){

                filelist = FileUtils.getAllFilePathByDir(workPath + includeStr);

                filelist = getAllErlFiles(filelist);

                for (File f: filelist) {
                    include_list.add(f.getAbsolutePath());
                }
            }
        }

    }
    // 添加非包含文件
    public void AddExInclude(List<String> list){
        List<String> list1 = new ArrayList<String>();
        for (String IncludeStr:include_list) {
            if(!reContains(IncludeStr, list)){
                list1.add(IncludeStr);
            }
        }
        include_list = list1;
    }

    public HashMap<String, ModContact> GetAllMod(String path){
        FilterFiles ff = new FilterFiles();

        List<File> list = FileUtils.getAllFilePathByDir(path);

        list = getAllErlFiles(list);

        HashMap<String, ModContact> modHash = new HashMap<String, ModContact>();

        modHash = ff.getAllModContact(list);

        return modHash;

    }

    public HashMap<String, ModContact> FilterFile(HashMap<String, ModContact> hashMap){

        HashMap<String, ModContact> newHashMap = new HashMap<String, ModContact>();

        for (Map.Entry<String, ModContact> entry : hashMap.entrySet()) {

            String key = entry.getKey();

            ModContact value = entry.getValue();

            value = filterMod(value);

            newHashMap.put(key, value);
        }

        return newHashMap;
    }

    // 判断 mod list 是不是都是包含的 并且不再过滤list 返回新的
    private ModContact filterMod(ModContact modCon){

        List<ModAndMethod> list =modCon.getList();

        List<ModAndMethod> newList = new ArrayList<ModAndMethod>();

        String mod = "";

        for (ModAndMethod maf:list) {

            mod = maf.getMod();

            if(contains(mod, include_list)){
                if(contains(mod, self_list)){
                    continue;
                }else{
                    newList.add(maf);
                }
            }

        }
        modCon.setList(newList);

        return modCon;
    }

    private Boolean contains(String str, List<String> list){
        for (int i = 0; i < list.size(); i ++){
            if(str.equals(FilterFiles.getModName(list.get(i)))){
                return true;
            }
        }
        return false;
    }

    private Boolean reContains(String includeStr, List<String> list){
        for (int i = 0; i < list.size(); i ++){
            if(Regular.checkFile(FilterFiles.ChangeSepatator(includeStr), list.get(i))){
                return true;
            }
        }
        return false;
    }
}
