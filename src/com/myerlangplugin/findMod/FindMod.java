package com.myerlangplugin.findMod;

import com.fromGui.findMod.FindModForm;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.myerlangplugin.MyReplace;
import com.myerlangplugin.cfg.CfgProperties;
import com.myerlangplugin.checkcirculate.pojo.ModAndMethod;
import com.myerlangplugin.checkcirculate.pojo.ModContact;
import com.myerlangplugin.checkcirculate.pojo.ModLink;
import com.utils.log.handle.Log;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FindMod extends AnAction {

    private Project project;
    @Override
    public void actionPerformed(AnActionEvent e) {
        DataContext dataContext = e.getDataContext();
        this.project = e.getData(PlatformDataKeys.PROJECT);
        String workpath = this.project.getBaseDir().getPath(); // 获取工作空间路径
//        Messages.showInfoMessage(project,"123 +" + text111,workpath);
        String url = getPath(dataContext);
        init(workpath,url);
    }

    public static String getPath(DataContext dataContext) {
        VirtualFile file = DataKeys.VIRTUAL_FILE.getData(dataContext);
        return file == null ? null : file.getPath();
    }
    /**
     *
     * @param workPath
     * @param filePath
     */
    private void init(String workPath,String filePath){
        try {
            String mod_file = CfgProperties.getConfig("mod_path",workPath , workPath);
            FindModForm myDialog = new FindModForm(new FindModForm.DialogCallBack() {
                @Override
                public void ok(String includeStr) {
                    try {
                        CfgProperties.setConfig("mod_path", includeStr, workPath);

                        FindModHelper fh = new FindModHelper();

                        GetProperties getProperties = new GetProperties();

                        getProperties.InitProp(includeStr);

                        fh.init(workPath, filePath);

                        HashMap<String, ModContact> hashMap= null;

                        hashMap = fh.GetAllMod(filePath);

                        hashMap = fh.FilterFile(hashMap);

                        write_file(workPath, hashMap);

                    } catch (Exception e) {
                        Messages.showInfoMessage(project,getStackTrace(e),filePath);
                    }

                }
            }, mod_file);
            myDialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            Messages.showInfoMessage(project, "Error", "出错." + e.getMessage());
        }

    }
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try{
            throwable.printStackTrace(pw);
            return sw.toString();
        }finally {
            pw.close();
        }
    }

    public void write_file(String path, HashMap<String, ModContact> hsahMap) {
        File codeGenDir = new File(path + "\\codeGenTmpDir");
        File file = new File(path + "\\codeGenTmpDir\\findMod.text");
        OutputStream outputStream = null;
        if (!codeGenDir.exists()) {
            codeGenDir.mkdir();
        }
        if (!file.exists()) {
            try {
                // 如果文件找不到，就new一个
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            // 定义输出流，写入文件的流
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        write(outputStream, hsahMap);

        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void write(OutputStream outputStream, HashMap<String, ModContact> hashMap) {


        int size = hashMap.size();


        for (Map.Entry<String, ModContact> entry : hashMap.entrySet()) {

            String key = entry.getKey();

            ModContact value = entry.getValue();

            String string = "";
            if (!value.getList().isEmpty()){
                // 定义将要写入文件的数据
                string = "\n\nMod: " + key +
//                    "\nalreadyList :" + modLink.getAlreadyList() +
                        "\nModAndMethod : " + print(value.getList());
            }

            byte[] bs = string.getBytes();
            try {
                // 写入bs中的数据到file中
                outputStream.write(bs);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    public String print(List<ModAndMethod> list) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {

            sb.append("\n" + list.get(i));

        }

        return sb.toString();
    }
    public static void main(String[] args) {
//        File[] files = file.listFiles();
//        System.out.println(files.length);
//        System.out.println(files.length);
        FindMod fm = new FindMod();
        fm.init("D:\\bstest\\ErlangPlugin\\src","D:\\bstest\\ErlangPlugin\\src\\test\\erlang\\aaa");
//        File file1 = new File("D:\\codeGenTmpDir\\input\\text\\text1");
//        File file2 = new File("D:\\codeGenTmpDir\\input\\{Pojo}\\{Pojo}1");;
//        Boolean flg = file1.renameTo(file2);
        System.out.println("end");
    }
}
