package com.myerlangplugin.editAction;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;

public class CustomEditorIllustration extends AnAction {
    static {
        final EditorActionManager editorActionManager = EditorActionManager.getInstance();
        TypedAction typedAction = editorActionManager.getTypedAction();
        typedAction.setupHandler(new CustomTypedActionHandler());
    }
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

    }
}
