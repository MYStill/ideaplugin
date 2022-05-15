package com.myerlangplugin.ckecktwolist;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regular {
    public static void aa(){
        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK?";
        String pattern = "(\\D*)(\\d+)(.*)";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if (m.find( )) {
            System.out.println("Found value: " + m.group(0) );
            System.out.println("Found value: " + m.group(1) );
            System.out.println("Found value: " + m.group(2) );
            System.out.println("Found value: " + m.group(3) );
        } else {
            System.out.println("NO MATCH");
        }

    }
    public static void main(String[] args) {
        String a = "  case erlang:whereis(gf_kathy:process_name(State#state.node)) of\n" +
                "    undefined ->\n" +
                "      _ = lager:notice(\"No pid yet for ~p\", [State#state.node]),\n" +
                "      {noreply, State, 100};\n" +
                "    KathyPid ->\n" +
                "      MonRef = erlang:monitor(process, KathyPid),\n" +
                "      {noreply, State#state{kathy = KathyPid, ref = MonRef}}\n" +
                "  end;";
//        List list = getContext(a);
//        System.out.println(list);
        checkFile("sys_asdasd.erl", ".*.erl");
        System.out.println(checkFile("sys_asdasd.erl", "^sys_.*"));
    }

    public static void bb() {
        String content = "Test1.java,你好啊";

        String pattern = "\\u4e00-\\u9fa5";

        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);
    }

    public static void cc() {
        String line = "我不好 Test1.java,你好啊";

        String pattern = "[\u4e00-\u9fa5]";

        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        while (m.find()){
            System.out.println("Found value: " + m.group(0) );
        }
    }
    public static List getContext(String html) {
        List resultList = new ArrayList();
        Pattern p = Pattern.compile("(\\w+):(\\w+)");//正则表达式 commend by danielinbiti
        Matcher m = p.matcher(html );//
        while (m.find()) {
            resultList.add(m.group(2));//
            System.out.println(m.group(0));
            System.out.println(m.groupCount());
        }
        return resultList;
    }

    /**
     *
     * @param fileName 文件名称
     * @param pattern 正则表达式
     * @return
     */
    public static boolean checkFile(String fileName,String pattern){
        return Pattern.matches(pattern, fileName);
    }

    public static void getMatckString(String pattern,String fileString,List<String> list){
        Pattern p = Pattern.compile(pattern);//正则表达式 commend by danielinbiti
        Matcher m = p.matcher(fileString );//
        while (m.find()) {
            list.add(m.group());//
        }
    }
}
