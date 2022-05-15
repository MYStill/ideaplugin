package com.myerlangplugin.checkcirculate.server;

import com.myerlangplugin.checkcirculate.pojo.ModAndMethod;
import com.myerlangplugin.checkcirculate.pojo.ModContact;
import com.myerlangplugin.checkcirculate.pojo.ModLink;
import com.myerlangplugin.checkcirculate.utils.FilterFiles;

import java.io.*;
import java.util.*;

public class Checkcirculateservice {

    HashMap<String, ModContact> modList = null;

    HashMap<String, ModContact> modFilterList = null; // 过滤不可能的方法调用

    List<String> modKeyList = null;

    List<ModContact> modValueList = null;

    public Checkcirculateservice() {

    }

    public Checkcirculateservice(String path) {

        FilterFiles filterFiles = new FilterFiles();

        HashMap<String, ModContact> modList = new HashMap<String, ModContact>();

        modList = filterFiles.getModContactByDir(path);

        this.modList = modList;

        setKeyAndValueList();

        filtercanotlist();

    }

    public void setKeyAndValueList() {

        List<String> modKeyList = new ArrayList<String>();

        List<ModContact> modValueList = new ArrayList<ModContact>();

        for (Map.Entry<String, ModContact> entry : modList.entrySet()) {

            String key = entry.getKey();

            ModContact value = entry.getValue();

            modKeyList.add(key);

            modValueList.add(value);

        }

        this.modKeyList = modKeyList;

        this.modValueList = modValueList;

    }

    public void filtercanotlist() {

        HashMap<String, ModContact> newhashMap = new HashMap<String, ModContact>();


        for (Map.Entry<String, ModContact> entry : modList.entrySet()) {

            String key = entry.getKey();

            ModContact value = entry.getValue();

            filterCanotMod(value);

            newhashMap.put(key, value);

        }

        this.modFilterList = newhashMap;
    }

    /**
     * 只考虑当前已有的模块 减少递归次数
     *
     * @param modContact
     */
    public void filterCanotMod(ModContact modContact) {

        List<ModAndMethod> modAndMethods = modContact.getList();

        List<ModAndMethod> newmodAndMethods = new ArrayList<ModAndMethod>();

        String modName = null;

        for (ModAndMethod modAndMethod : modAndMethods) {

            modName = modAndMethod.getMod();

            if (contain(modName, this.modKeyList)) {

                newmodAndMethods.add(modAndMethod);

            }
        }

        modContact.setList(newmodAndMethods);
    }

    public List<ModLink> checkcirculate() {

        List<ModLink> list = new ArrayList<ModLink>();

        for (ModContact modContact : this.modValueList) {

            checkiscircul(list, modContact);
        }

        return list;
    }

    public void checkiscircul(List<ModLink> modLinks, ModContact modContact) {

        List<String> alreadyList = new ArrayList<String>();

//        List<ModLink> modLinks = new ArrayList<ModLink>();

        String modName = modContact.getModName();

        ModLink modLink = new ModLink();

        modLink.setStar(modName);

//        alreadyList.add(modName);

        recircul(modContact, modLinks, alreadyList, modLink);

//        list.addAll()

//        print(modLinks);

    }

    public void recircul(ModContact modContact, List<ModLink> modLinks, List<String> alreadyList, ModLink modLink) {

        String conModname = modContact.getModName();

        List<ModAndMethod> modAndMethods = modContact.getList();

        for (ModAndMethod modAndMethod : modAndMethods) {

            ModLink newModLink = copy(modLink);

            newModLink.addLinks(modAndMethod);

            String ModName = modAndMethod.getMod();

            if (contain(ModName, alreadyList)) {

                newModLink.setAlreadyList(alreadyList);

                newModLink.setStar(ModName);

//                System.out.println(ModName + "  :" + alreadyList + "\n" +  modLink);

                modLinks.add(newModLink);

            } else {

                ModContact newModContact = this.modFilterList.get(ModName);

                List<String> newAlreadyList = new ArrayList<String>();

                newAlreadyList.addAll(alreadyList);

                newAlreadyList.add(conModname);

                recircul(newModContact, modLinks, newAlreadyList, newModLink);

            }

        }

    }


    public ModLink copy(ModLink modLink) {

        ModLink modLink1 = new ModLink();

        modLink1.setStar(modLink.getStar());

        LinkedList<ModAndMethod> linkedList = new LinkedList<>();

        linkedList.addAll(modLink.getLinkedList());

        modLink1.setLinkedList(linkedList);

        return modLink1;

    }

    public void print(List<ModLink> list) {

        int size = list.size();

        for (int i = 0; i < size; i++) {

            ModLink modLink = list.get(i);

            System.out.println("name: " + modLink.getStar() + "list :" + modLink.getLinkedList());

        }
    }


    public boolean contain(String s, List<String> list) {

        for (int i = 0; i < list.size(); i++) {

            if (s.equals(list.get(i))) {

                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {

        Checkcirculateservice checkcirculateservice = new Checkcirculateservice("C:\\Users\\tl\\Desktop\\gold_fever-master\\gold_fever\\src");

//
//        for (Map.Entry<String,ModContact> entry:checkcirculateservice.modFilterList.entrySet()){
//
//            String key = entry.getKey();
//
//            ModContact value = entry.getValue();
//
//            System.out.println("key :" + key);
//
//            System.out.println("value :" + value );
//
//        }
//
//        System.out.println(checkcirculateservice.modFilterList);
        checkcirculateservice.checkcirculate();


    }

}
