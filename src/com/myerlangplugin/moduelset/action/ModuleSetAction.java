package com.myerlangplugin.moduelset.action;

import com.fromGui.moduleset.ModuleSetForm;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.myerlangplugin.moduelset.setHelper.GetAndSet;
import com.utils.log.handle.Log;

public class ModuleSetAction extends AnAction {

    private Project project;
    private String packageName = "";//
    private String text1; //
    private String text2; //
    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
//        DataContext dataContext = e.getDataContext();
//        this.project = e.getData(PlatformDataKeys.PROJECT);
//        String workpath = this.project.getBaseDir().getPath(); //
////        Messages.showInfoMessage(project,"123 +" + text111,workpath);
//        String url = MyReplace.getPath(dataContext);
        init();
    }

    private void init(){
        try {
//            PluginHelper pluginHelper = PluginHelper.newInstance();
//            File targetDir = pluginHelper.prepareCodeGen(workPath,filePath);
//            String className = pluginHelper.getClassName(targetDir);
//            String tempPojoClassName = com.base.FileUtils.lowFirstChar(className);
//            String tempPojoName = com.base.FileUtils.upFirstChar(className);
//            String initValue = tempPojoClassName+" - "+tempPojoName+" -> targetPojoClass - targetPojo";

//            System.out.println("initValue" + initValue);

            ModuleSetForm myDialog = new ModuleSetForm(new ModuleSetForm.DialogCallBack() {
                @Override
                public String ok(String intext1,int flag, boolean isGet, boolean pri) {

                    GetAndSet getAndSet = new GetAndSet();

                    String reMessage = getAndSet.getMessage(intext1, flag, isGet, pri);

                    Log.debug(reMessage);
                    //
                    return reMessage;
//                    Messages.showInfoMessage(project,Test1 + text2,filePath);
                }
            });
            myDialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            Messages.showInfoMessage(project, "Error", "出错." + e.getMessage());
        }

    }

    public static void main(String[] args) {
        ModuleSetAction moduleSetAction = new ModuleSetAction();
        moduleSetAction.init();
    }
}
