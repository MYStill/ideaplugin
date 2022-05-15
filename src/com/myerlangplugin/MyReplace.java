package com.myerlangplugin;

import com.fromGui.ReplaceForm;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MyReplace extends AnAction {

    private Project project;
    private String packageName = "";//文件路径
    private String text1; //文件内容替换
    private String text2; //存放路径

    @Override
    public void actionPerformed(AnActionEvent e) {
        DataContext dataContext = e.getDataContext();
        this.project = e.getData(PlatformDataKeys.PROJECT);
        String workpath = this.project.getBaseDir().getPath(); // 获取工作空间路径
//        Messages.showInfoMessage(project,"123 +" + text111,workpath);
        String url = MyReplace.getPath(dataContext);
        init(workpath,url);
    }

    /**
     *
     * @param workPath
     * @param filePath
     */
    private void init(String workPath,String filePath){
        try {
            PluginHelper pluginHelper = PluginHelper.newInstance();
            File targetDir = pluginHelper.prepareCodeGen(workPath,filePath);
            String className = pluginHelper.getClassName(targetDir);
            String tempPojoClassName = com.base.FileUtils.lowFirstChar(className);
            String tempPojoName = com.base.FileUtils.upFirstChar(className);
            String initValue = tempPojoClassName+" - targetPojoClass\n" +
                    tempPojoName+" - targetPojo";

//            System.out.println("initValue" + initValue);

            ReplaceForm myDialog = new ReplaceForm(new ReplaceForm.DialogCallBack() {
                @Override
                public void ok(String inText) {
                    // 实例化ok事件
                    text1 = inText;


                    // 获取 token map
                    HashMap<String, String> tokenMap = new HashMap<String, String>();
                    tokenMap = PluginHelper.getTokenMap(inText);
                    File inputDir = pluginHelper.getCodeGenInputDir();
                    File tempDir = pluginHelper.getCodeGenTempDir();
                    File outputDir = pluginHelper.getCodeGenOutputDir();


                    try {
                        com.codeGenHelper.CodeGenHelper.newInstance(inputDir , tempDir, outputDir)
                                .withTempClass(tokenMap)
                                .withBsClass(tokenMap).doGenBS();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    Messages.showInfoMessage(project,Test1 + text2,filePath);
                }
            },initValue);
            myDialog.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
            Messages.showInfoMessage(project, "Error", "出错." + e.getMessage());
        }

    }
//
//    public void myRun(){
//        try {
//            PluginHelper pluginHelper = PluginHelper.newInstance();
//            File targetDir = pluginHelper.prepareCodeGen();
//            String className = pluginHelper.getClassName(targetDir);
//
//            String tempPojoClassName = com.zenmind.base.FileUtils.upFirstChar(className);
//            String tempPojoName = com.zenmind.base.FileUtils.lowFirstChar(className);
//            String initValue = tempPojoClassName+" - "+tempPojoName+" -> targetPojoClass - targetPojo";
//            InputDialog inputDialog = new InputDialog(this.shell, "代码生成", "把TargetClass换成需要替换成的类名", initValue, null);
//            if (Window.OK == inputDialog.open()) {
//                String inputValue = inputDialog.getValue();
//
//                String[] split = inputValue.split("->");
//                String[] tempClass = split[0].split("-");
//                String[] bsClass = split[1].split("-");
//                File inputDir = pluginHelper.getCodeGenInputDir();
//                File tempDir = pluginHelper.getCodeGenTempDir();
//                File outputDir = pluginHelper.getCodeGenOutputDir();
//
//                com.zenmind.codeGenHelper.CodeGenHelper.newInstance(inputDir , tempDir, outputDir)
//                        .withTempClass(tempClass[0].trim(),tempClass[1].trim())
//                        .withBsClass(bsClass[0].trim(),bsClass[1].trim()).doGenBS();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            MessageDialog.openInformation(shell, "Error", "出错." + e.getMessage());
//        }
//    }

//    @Override
//    public void update(AnActionEvent e) {
//
//    }

    public static String getPath(DataContext dataContext) {
        VirtualFile file = DataKeys.VIRTUAL_FILE.getData(dataContext);
        return file == null ? null : file.getPath();
    }

    /**
     * 执行 bat 脚本
     * @param args
     */

    public static void main(String[] args) {
        File file = new File("D:\\bstest\\ErlangPlugin\\src\\com\\base\\FileUtils.java");
//        File[] files = file.listFiles();
//        System.out.println(files.length);
//        System.out.println(files.length);
        MyReplace mr = new MyReplace();
        mr.init(null,"D:\\bstest\\ErlangPlugin\\src\\com\\text\\cod");
//        File file1 = new File("D:\\codeGenTmpDir\\input\\text\\text1");
//        File file2 = new File("D:\\codeGenTmpDir\\input\\{Pojo}\\{Pojo}1");;
//        Boolean flg = file1.renameTo(file2);
        System.out.println("end");
    }
}
