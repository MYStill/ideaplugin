package com.myerlangplugin.listPopup;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;

public class listPopup extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        DefaultActionGroup actionGroup = (DefaultActionGroup) ActionManager.getInstance().getAction("Sample_JBPopupActionGroup");
        actionGroup.removeAll();
        actionGroup.add(new AnAction("Sample Action") {
            @Override
            public void actionPerformed(AnActionEvent e) {

                Messages.showErrorDialog("Generation failed, " +
                                "your class name MUST END WITH 'Contract' or 'Presenter'.",
                        "Class Name Error");
            }
        });
        ListPopup listPopup = JBPopupFactory.getInstance().createActionGroupPopup("ListPopup Sample", actionGroup, e.getDataContext(), JBPopupFactory.ActionSelectionAid.SPEEDSEARCH, false);
        listPopup.showInBestPositionFor(e.getDataContext());
    }
}
