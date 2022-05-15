package test;

import java.util.ArrayList;
import java.util.List;

public class Test2 {
    /**
     * 递归测试
     */

    public static void re(List<Integer> list){

        for(int i = 0;i < 3 ; i ++){

            if(list.size() > 3){

                System.out.println(list);
            }else{

                list.add(i);

                re(list);
            }
        }
    }

    public static void main(String[] args) {

        re(new ArrayList<Integer>());
    }
}
