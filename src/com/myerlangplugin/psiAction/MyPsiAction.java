package com.myerlangplugin.psiAction;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.utils.log.handle.Log;

public class MyPsiAction extends AnAction {
    private Project project;
    private String packageName = "";//文件路径
    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        final PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        FileType fileType = psiFile.getFileType();
//        PsiElement psi = new PsiElement();
        Log.error("fileType.getName()" + fileType.getName());
        final Project project = getEventProject(e);
        if (project == null) return;
    }

}
