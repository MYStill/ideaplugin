package com.myerlangplugin.findChinese;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.util.Url;
import com.myerlangplugin.MyReplace;
import com.myerlangplugin.checkcirculate.pojo.ModLink;
import com.myerlangplugin.findChinese.help.ChineseFile;
import com.myerlangplugin.findChinese.help.ChinesePojo;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FindChinese extends AnAction {

    private Project project;

    @Override
    public void actionPerformed(AnActionEvent e) {
        DataContext dataContext = e.getDataContext();
        this.project = e.getData(PlatformDataKeys.PROJECT);
//        Messages.showInfoMessage(project,"123 +" + text111,workpath);
        String url = MyReplace.getPath(dataContext);
        if(url == null){
            Messages.showErrorDialog(url,"路径不正确");
        }else{
            String workpath = this.project.getBaseDir().getPath(); // 获取工作空间路径
            searchChinese(workpath, url);
        }
    }

    // 查找中文
    public void searchChinese(String path, String Url){

        File file = new File(Url);

        ChineseFile chineseFile = new ChineseFile();
        List<File> fileList = chineseFile.getAllFile(file);

        List<ChinesePojo> listPojo = new ArrayList<ChinesePojo>();

        File codeGenDir = new File(path + "\\codeGenTmpDir");
        File chinese = new File(path + "\\codeGenTmpDir\\Chinese.text");
        if (!codeGenDir.exists()) {
            codeGenDir.mkdir();
        }else{

        }
        if (chinese.exists()) {
            chinese.delete();
        }
        try {
            // 如果文件找不到，就new一个
            chinese.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (File f:fileList) {
            listPojo = chineseFile.getChinese(f);
            if(listPojo.size() == 0){

            }else{
                write_file(chinese, listPojo);
            }
        }

        try {
            // 如果文件找不到，就new一个
            Desktop.getDesktop().open(codeGenDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FindChinese findChinese = new FindChinese();

        findChinese.searchChinese("D:","D:\\bstest\\ErlangPlugin\\src\\test\\erlang");
    }

    public void write_file(File chinese, List<ChinesePojo> list) {
        OutputStream outputStream = null;
        try {
            // 定义输出流，写入文件的流
            outputStream = new FileOutputStream(chinese, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        write(outputStream, list);

        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void write(OutputStream outputStream, List<ChinesePojo> list) {



        int size = list.size();
        String str = "";

        for (int i = 0; i < size; i++) {



            // 定义将要写入文件的数据
            str = list.get(i).toString();

            byte[] bs = str.getBytes();
            try {
                // 写入bs中的数据到file中
                outputStream.write(bs);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
