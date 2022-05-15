package com.myerlangplugin.winCmd;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.myerlangplugin.MyReplace;
import com.myerlangplugin.PluginHelper;

import java.io.File;
import java.io.IOException;

public class WinCmdAction extends AnAction {
    private Project project;
    private String packageName = "";//文件路径
    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
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
//        try {
//            PluginHelper pluginHelper = PluginHelper.newInstance();
//            File targetDir = pluginHelper.prepareCodeGen(workPath, filePath);
//            String className = pluginHelper.getClassName(targetDir);
//            String tempPojoClassName = com.base.FileUtils.lowFirstChar(className);
//            String tempPojoName = com.base.FileUtils.upFirstChar(className);
//            String initValue = tempPojoClassName + " - " + tempPojoName + " -> targetPojoClass - targetPojo";

////            System.out.println("initValue" + initValue);
//
//            WinBatForm myDialog = new WinBatForm(new WinBatForm.DialogCallBack() {
//                @Override
//                public void ok(String intext1) {
//                    // 实例化ok事件
//                    String text1 = intext1;
//
//                    String[] split = text1.split("->");
//                    String[] tempClass = split[0].split("-");
//                    String[] bsClass = split[1].split("-");
//                    File inputDir = pluginHelper.getCodeGenInputDir();
//                    File tempDir = pluginHelper.getCodeGenTempDir();
//                    File outputDir = pluginHelper.getCodeGenOutputDir();
//
//
//                    try {
//                        com.codeGenHelper.CodeGenHelper.newInstance(inputDir , tempDir, outputDir)
//                                .withTempClass(tempClass[0].trim(),tempClass[1].trim())
//                                .withBsClass(bsClass[0].trim(),bsClass[1].trim()).doGenBS();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
////                    Messages.showInfoMessage(project,Test1 + text2,filePath);
//                }
//            },initValue);
//            myDialog.setVisible(true);
//        } catch (IOException e) {
//            e.printStackTrace();
//            Messages.showInfoMessage(project, "Error", "出错." + e.getMessage());
//        }
//
//    }
        }


}
