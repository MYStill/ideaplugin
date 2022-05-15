package com.fromGui.checkTwoList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class ShowListForm extends JDialog {
    private JPanel contentPane;
    private JScrollBar scrollBar1;
    private JTable table1;
    private JPanel jpanel;
    private JButton buttonOK;

    public ShowListForm(List<String> list1, List<String> list2) {
        table1.setModel(message(list1,list2));
        setTitle("设置参数");
        setSize(600,400); // 设置大小
        setLocationRelativeTo(null); // 居中
        setContentPane(contentPane);
//        jpanel.add(scrollBar1);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }

//    public static void main(String[] args) {
//        ShowListForm dialog = new ShowListForm();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
    public DefaultTableModel message(List<String> list1,List<String> list2){
        Vector vData = new Vector();
        Vector vName = new Vector();
        vName.add("list1");
        vName.add("list2");
        Vector vRow = new Vector();
        vRow.add("list1");
        vRow.add("list2");
        getData(list1,list2,vData);
//        vData.add(vRow.clone());
//        vData.add(vRow.clone());
        DefaultTableModel model = new DefaultTableModel(vData, vName);
        return model;
//        JTable jTable1 = new JTable();
//        jTable1.setModel(model);
    }
    public void getData(List<String> list1,List<String> list2,Vector vData){
        Vector vRow = null;
        for (String s:list1) {
            if(list2.contains(s)){
//                System.out.println(list2.contains(s) + "   " +s);
//                vRow = new Vector();
//                vRow.add(s);
//                vRow.add(s);
//                vData.add(vRow.clone());
            }else{
                vRow = new Vector();
                vRow.add(s);
                vRow.add("");
                vData.add(vRow.clone());
            }
        }
        for (String s2:list2) {
            if(list1.contains(s2)){
            }else{
                vRow = new Vector();
                vRow.add("");
                vRow.add(s2);
                vData.add(vRow.clone());
            }
        }
    }

}
