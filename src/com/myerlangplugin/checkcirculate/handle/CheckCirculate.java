package com.myerlangplugin.checkcirculate.handle;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.myerlangplugin.MyReplace;
import com.myerlangplugin.checkcirculate.pojo.ModAndMethod;
import com.myerlangplugin.checkcirculate.pojo.ModLink;
import com.myerlangplugin.checkcirculate.server.Checkcirculateservice;
import com.utils.log.handle.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CheckCirculate extends AnAction {

    private Project project;

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        DataContext dataContext = e.getDataContext();

        this.project = e.getData(PlatformDataKeys.PROJECT);

        String url = MyReplace.getPath(dataContext);

        String workpath = this.project.getBaseDir().getPath(); // 获取工作空间路径

        Messages.showInfoMessage(project,System.getProperty("user.dir"),"输出");

        Log.debug("url " + url);

        Log.debug("workpath " + workpath);

        init(workpath, url);
    }

    public void init(String workpath, String url) {

        Checkcirculateservice checkcirculateservice = new Checkcirculateservice(url);

        List<ModLink> list = checkcirculateservice.checkcirculate();

//        workpath = "C:\\Users\\tl\\Desktop\\gold_fever-master\\gold_fever\\text";

        write_file(workpath, list);

    }

    public void write_file(String path, List<ModLink> list) {
        File codeGenDir = new File(path + "\\codeGenTmpDir");
        File file = new File(path + "\\codeGenTmpDir\\circul.text");
        OutputStream outputStream = null;
        if (!codeGenDir.exists()) {
            codeGenDir.mkdir();
        }
        if (!file.exists()) {
            try {
                // 如果文件找不到，就new一个
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            // 定义输出流，写入文件的流
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        write(outputStream, list);

        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void write(OutputStream outputStream, List<ModLink> oldList) {

        List<ModLink> list = filter2(oldList);


        int size = list.size();


        for (int i = 0; i < size; i++) {

            ModLink modLink = list.get(i);


            // 定义将要写入文件的数据
            String string = "\n\nStartMod: " + modLink.getStar() +
//                    "\nalreadyList :" + modLink.getAlreadyList() +
                    "\nModandMethod : " + print(modLink.getLinkedList());

            byte[] bs = string.getBytes();
            try {
                // 写入bs中的数据到file中
                outputStream.write(bs);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public List<ModLink> filter2(List<ModLink> list) {

        List<ModLink> links = new ArrayList<ModLink>();

        List<ModLink> links2 = new ArrayList<ModLink>();

        int size = list.size();


        for (int i = 0; i < size; i++) {

            ModLink modLink = list.get(i);


            modLink.setLinkedList(filter1(modLink.getStar(), modLink.getLinkedList()));

            links.add(modLink);
        }

        for (int j = 0; j < links.size(); j++) {

            boolean flag = true;

            for (int k = j + 1; k < links.size(); k++) {

                if (checkisboth(links.get(j), links.get(k))) {

                    flag = false;

                    break;
                }

            }

            if (flag) {

                links2.add(links.get(j));
            }
        }

        return links2;
    }


    public boolean checkisboth(ModLink modLink1, ModLink modLink2) {

        LinkedList<ModAndMethod> linkedList1 = modLink1.getLinkedList();

        LinkedList<ModAndMethod> linkedList2 = modLink2.getLinkedList();

        if (linkedList1.size() != linkedList2.size()) {

            return false;
        }

        for (int i = 0; i < linkedList1.size(); i++) {

            if (check(linkedList1.get(i), linkedList2)) {


            } else {

                return false;
            }

        }
        return true;

    }

    public boolean check(ModAndMethod modAndMethod, LinkedList<ModAndMethod> linkedList2) {

        for (int j = 0; j < linkedList2.size(); j++) {

            if (modAndMethod.toString().equals(linkedList2.get(j).toString())) {

                return true;
            }

        }
        return false;
    }

    /**
     * 过滤不需要的信息
     *
     * @param startMod
     * @param linkedList
     * @return
     */
    public LinkedList<ModAndMethod> filter1(String startMod, LinkedList<ModAndMethod> linkedList) {

        LinkedList<ModAndMethod> linkedList2 = new LinkedList<ModAndMethod>();

        boolean flag = false;

        for (int i = 0; i < linkedList.size(); i++) {

            ModAndMethod modAndMethod = linkedList.get(i);

            if (!flag) {

                if (startMod.equals(modAndMethod.getCurMod())) {

                    flag = true;

                    linkedList2.addLast(modAndMethod);

                } else {

                }


            } else {

                linkedList2.addLast(modAndMethod);

            }

        }

        return linkedList2;

    }

    public String print(LinkedList<ModAndMethod> linkedList) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < linkedList.size(); i++) {

            sb.append("\n" + linkedList.get(i));

        }

        return sb.toString();

    }

    public static void main(String[] args) {

        CheckCirculate c = new CheckCirculate();

        c.init("", "");
    }
}
