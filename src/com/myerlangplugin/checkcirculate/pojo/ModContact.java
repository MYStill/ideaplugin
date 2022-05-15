package com.myerlangplugin.checkcirculate.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建对应目录和 其所调用模块联系
 */
public class ModContact {

    private String modName = ""; // 模块名称

    private List<ModAndMethod> list = new ArrayList<ModAndMethod>(); // 目录模块调用信息

    private List<ModLink> modLinks = new ArrayList<ModLink>(); // 模块递归调用链

    public List<ModLink> getModLinks() {
        return modLinks;
    }

    public void setModLinks(List<ModLink> modLinks) {
        this.modLinks = modLinks;
    }

    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }

    public List<ModAndMethod> getList() {
        return list;
    }

    public void setList(List<ModAndMethod> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ModContact{" +
                "modName='" + modName + '\'' +
                ", list=" + list +
                ", modLinks=" + modLinks +
                '}';
    }
}
