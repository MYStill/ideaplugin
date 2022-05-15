package com.myerlangplugin.checkRepeatFile;

import com.base.FileUtils;
import com.fromGui.RepeatFileForm;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.myerlangplugin.MyReplace;
import com.myerlangplugin.checkcirculate.utils.FilterFiles;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckRepeatFileName extends AnAction {

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
            File file1 = new File(url);
            try {
                List<File> fileList = FileUtils.getAllErlFiles(FileUtils.getAllFilePathByDir(file1));
                HashMap<File,File> hashMap = FileUtils.getrepeatFiles(fileList);
                List<String> list1 = new ArrayList<String>();
                List<String> list2 = new ArrayList<String>();
                getHashAllKeys(hashMap,  list1,  list2);
                if(list1.size() == 0){
                    Messages.showInfoMessage(project,"无件名重复","检查文件名重复");
                    return;
                }else if (list1.size() > 10){
                    list1 = list1.subList(0, 10);
                    list2 = list2.subList(0, 10);
                }
                RepeatFileForm myDialog = new RepeatFileForm(new RepeatFileForm.FormCallBack() {
                    @Override
                    public void ok(String intext1, String intext2) {

//                    Messages.showInfoMessage(project,Test1 + text2,filePath);
                    }
                },list1,list2);
                myDialog.setVisible(true);
            } catch (Exception e1) {
                Messages.showErrorDialog(e1.getMessage(),"未知错误");
            } finally {

            }
        }
    }

    public void  getHashAllKeys(HashMap<File,File> hashMap, List<String> list1, List<String> list2) {

        for (Map.Entry<File, File> entry : hashMap.entrySet()) {
            list1.add(entry.getKey().getPath());
            list2.add(entry.getValue().getPath());
        }
    }


}
