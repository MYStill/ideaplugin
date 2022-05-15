package com.myerlangplugin.checkcirculate.pojo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ModLink {

    private String Star = ""; // 开始模块名称

    private List<String> alreadyList = new ArrayList<String>();

    private LinkedList<ModAndMethod> linkedList = new LinkedList<ModAndMethod>(); // 模块调用链表

    public String getStar() {
        return Star;
    }

    public void setStar(String star) {
        Star = star;
    }

    public LinkedList<ModAndMethod> getLinkedList() {
        return linkedList;
    }

    public void setLinkedList(LinkedList<ModAndMethod> linkedList) {
        this.linkedList = linkedList;
    }

    public void addLinks(ModAndMethod modAndMethod){

        this.linkedList.addLast(modAndMethod);

    }

    public List<String> getAlreadyList() {
        return alreadyList;
    }

    public void setAlreadyList(List<String> alreadyList) {
        this.alreadyList = alreadyList;
    }

    @Override
    public String toString() {
        return "ModLink{" +
                "Star='" + Star + '\'' +
                ", alreadyList=" + alreadyList +
                ", linkedList=" + linkedList +
                '}';
    }
}
