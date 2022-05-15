package com.myerlangplugin.editAction;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.IdeActions;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.project.Project;

public class EditorHandlerIllustration extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        final Editor editor =anActionEvent.getData(CommonDataKeys.EDITOR);
        EditorActionManager editorActionManager = EditorActionManager.getInstance();
        EditorActionHandler editorActionHandler = editorActionManager.getActionHandler(
                IdeActions.ACTION_EDITOR_CLONE_CARET_BELOW);
        editorActionHandler.execute(editor,editor.getCaretModel().getCurrentCaret(),anActionEvent.getDataContext());

    }

    public void update(AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        final Editor editor =e.getData(CommonDataKeys.EDITOR);
        e.getPresentation().setVisible(
                project != null
                && editor != null
                && !editor.getCaretModel().getAllCarets().isEmpty()
        );
    }
}
