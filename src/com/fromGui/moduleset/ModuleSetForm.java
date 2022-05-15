package com.fromGui.moduleset;



import com.utils.log.handle.Log;

import javax.swing.*;
import java.awt.event.*;

public class ModuleSetForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JButton ok2Button;
    private JCheckBox priCheckBox;
    private JCheckBox getCheckBox;

    private DialogCallBack mCallBack;

    public ModuleSetForm(DialogCallBack callBack) {
        this.mCallBack = callBack;
        setTitle("get set");
        setSize(800,600); //
        setContentPane(contentPane);
        setLocationRelativeTo(null); // 居中
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        ok2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK2();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        if (null != mCallBack){
//           System.out.println( getCheckBox.getText() + getCheckBox.isSelected());
           String reMessage = mCallBack.ok(this.textArea1.getText(), 1,
                   getCheckBox.isSelected(), priCheckBox.isSelected());
           this.textArea2.setText(reMessage);
        }
    }

    private void onOK2() {
        // add your code here
        if (null != mCallBack){
            String reMessage = mCallBack.ok(this.textArea1.getText(), 2, getCheckBox.isSelected(),
                    priCheckBox.isSelected());
            this.textArea2.setText(reMessage);
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
//
//    public static void main(String[] args) {
//        ModuleSetForm dialog = new ModuleSetForm();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }

    public interface DialogCallBack{
        public String ok(String str1, int flag,boolean IsGet,boolean isPri);
    }
}
