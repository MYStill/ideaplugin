package com.utils.log.other;

import com.intellij.openapi.ui.Messages;
import com.utils.log.handle.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class WriteLog {

    public static void writeLog(String message){

        try {
            String name = MyTime.getDateName();

            String path = System.getProperty("user.dir") + LogConst.LOG_PATH;

            message = message + path;

            path = "D:\\bstest\\ErlangPlugin\\log\\";

            System.out.println(path);

            File file = new File(path);

            if(!file.exists()){

                file.mkdirs();

            }

            String log = path + name + ".txt";

            File logFile = new File(log);

            if (!logFile.exists()){

                logFile.createNewFile();
            }


            // 下面这也会抛出异常，这次我们为了代码结构清晰起见，直接throw给main吧
            // 这里改变了writer的类型，变成了追加型
            Writer writer = new FileWriter(logFile, true);

            writer.write(message);

          // 在这一定要记得关闭流
            writer.close();
        } catch (IOException e) {

            e.printStackTrace();

        } finally {


        }
    }

    public static String stackTrace(){

        Throwable ex = new Throwable();

        StackTraceElement[] stackElements = ex.getStackTrace();

        StringBuffer sb = new StringBuffer();

        int initSeq = 0;

        if(stackElements.length > LogConst.STATCK_DEEP){

            initSeq = stackElements.length - LogConst.STATCK_DEEP;

        }

        if(stackElements != null)
        {
            for(int i = initSeq; i < 1; i++)
            {
                sb.append(
                stackElements[i].getClassName() + " file:" +
                stackElements[i].getFileName() + " line:" +
                stackElements[i].getLineNumber() + " Method:" +
                stackElements[i].getMethodName() + "\n\r");
//                System.out.println();
//                System.out.println();
//                System.out.println();
//                System.out.println();
//                System.out.println("-----------------------------------");
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        Log.error("你好啊！");

    }
}
