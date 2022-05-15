package com.fromGui.checkTwoList;




import javax.swing.*;
import java.awt.event.*;

public class RegularForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JFormattedTextField url1;
    private JFormattedTextField url2;
    private JFormattedTextField pattern21;
    private JFormattedTextField pattern22;
    private JFormattedTextField pattern11;
    private JFormattedTextField pattern12;

    private DialogCallBack mCallBack;

    public RegularForm(String listFile1,String listFile2,String listpattern11,String listpattern12,String  listpattern21,
                       String listpattern22, DialogCallBack callBack) {
        this.mCallBack = callBack;
        setTitle("设置参数");
        setSize(600,400); // 设置大小
        setLocationRelativeTo(null); // 居中
        setContentPane(contentPane);
        if(!"".equals(listFile1)) url1.setValue(listFile1);
        if(!"".equals(listFile2)) url2.setValue(listFile2);
        if(!"".equals(listpattern11)) pattern11.setValue(listpattern11);
        if(!"".equals(listpattern12)) pattern12.setValue(listpattern12);
        if(!"".equals(listpattern21)) pattern21.setValue(listpattern21);
        if(!"".equals(listpattern22)) pattern22.setValue(listpattern22);
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
            mCallBack.ok(url1.getText().trim(), url2.getText().trim(),pattern11.getText().trim(),
                    pattern12.getText().trim(),pattern21.getText().trim(), pattern22.getText().trim());
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    public interface DialogCallBack{
        void ok(String url1, String url2,String pattern11, String pattern12,String pattern21, String pattern22);
    }
}
