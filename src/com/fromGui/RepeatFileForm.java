package com.fromGui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.List;

public class RepeatFileForm extends JDialog{
    private JPanel contentPane;
    private FormCallBack mCallBack;
    private JList list1;
    private JList list2;

    public RepeatFileForm(FormCallBack callBack,List<String> initList1,List<String> initList2) {
        this.mCallBack = callBack;
        setTitle("文件名称重复检查");
        setSize(800,400); // 设置大小

        list1.setModel(getdefaultModle(initList1));
        list2.setModel(getdefaultModle(initList2));
        setLocationRelativeTo(null); // 居中

        setContentPane(contentPane);
        setModal(true);
//        getRootPane().setDefaultButton(buttonOK);
//
//        buttonOK.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                onOK();
//            }
//        });
//
//        buttonCancel.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                onCancel();
//            }
//        });
//
//        // call onCancel() when cross is clicked
//        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//        addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                onCancel();
//            }
//        });
//
//        // call onCancel() on ESCAPE
//        contentPane.registerKeyboardAction(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                onCancel();
//            }
//        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
//        if (null != mCallBack){
//            mCallBack.ok(textField1.getText().trim(), textField2.getText().trim());
//        }
//        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
//        dispose();
    }
//
//    public static void main(String[] args) {
//        ReplaceForm dialog = new ReplaceForm();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
    public DefaultListModel getdefaultModle(List<String> list){
        DefaultListModel dlm = new DefaultListModel();

        for (String s: list) {
            dlm.addElement(s);
        };


        return dlm;
    }

    public interface FormCallBack{
        void ok(String str1, String str2);
    }
}
