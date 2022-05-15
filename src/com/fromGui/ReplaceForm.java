package com.fromGui;

import javax.swing.*;
import java.awt.event.*;

public class ReplaceForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textArea1;
//    private JTextField textField1;
//    private JTextField textField2;

    private DialogCallBack mCallBack;

    public ReplaceForm(DialogCallBack  callBack,String initValue) {
        this.mCallBack = callBack;
        setTitle("文件替换");
        setSize(400,200); // 设置大小
        textArea1.setText(initValue);
        setLocationRelativeTo(null); // 居中
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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
        if (null != mCallBack){
            mCallBack.ok(textArea1.getText().trim());
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
//
//    public static void main(String[] args) {
//        ReplaceForm dialog = new ReplaceForm();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }

    public interface DialogCallBack{
        void ok(String str1);
    }
}
