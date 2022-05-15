package com.fromGui.findMod;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class FindModForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JFormattedTextField formattedTextField1;
    private JButton finddir;
    private DialogCallBack mCallBack;
    //  JFileChooser
    public FindModForm(DialogCallBack mCallBack, String mod_file) {
        this.mCallBack = mCallBack;
        setSize(400,200); // 设置大小
        setContentPane(contentPane);
        setLocationRelativeTo(null); // 居中
        formattedTextField1.setValue(mod_file);
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

        finddir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onChoose();
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
            mCallBack.ok(formattedTextField1.getText().trim());
        }
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void onChoose() {
//        final JButton choose=new JButton("选择文件目录");
//
//        final JTextField dir=new JTextField();

        final JFileChooser chooser=new JFileChooser();

//        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue=chooser.showOpenDialog(FindModForm.this);

        if (returnValue==JFileChooser.APPROVE_OPTION) {

            File file=chooser.getSelectedFile();
            formattedTextField1.setText(file.getAbsolutePath());

        }else {
            JOptionPane.showMessageDialog(null, "请手动输入文件");

            return;

        }
    }

    public interface DialogCallBack{
        void ok(String str1);
    }


//    public static void main(String[] args) {
//        FindModForm dialog = new FindModForm();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
