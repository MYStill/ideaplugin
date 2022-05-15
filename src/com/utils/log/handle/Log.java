package com.utils.log.handle;

import com.utils.log.other.LogConst;
import com.utils.log.other.MyTime;
import com.utils.log.other.WriteLog;

public class Log {

    public static void error(Object message){

        if(LogConst.LOG_LEVEL <= 5){

            String message2 = MyTime.getDateTime() + ":[error] \n" +
                    WriteLog.stackTrace() + message.toString() + "\n\r";

            WriteLog.writeLog(message2);

        }

    }

    public static void error(String message){

        if(LogConst.LOG_LEVEL <= 5){

            message = MyTime.getDateTime() + ":[error] \n" + WriteLog.stackTrace() + message + "\n\r";

            WriteLog.writeLog(message);

        }

    }

    public static void warning(Object message){

        if(LogConst.LOG_LEVEL <= 4){

            String message2 = MyTime.getDateTime() + ":[warning] \n" + WriteLog.stackTrace() + message.toString() + "\n\r";

            WriteLog.writeLog(message2);

        }

    }

    public static void warning(String message){

        if(LogConst.LOG_LEVEL <= 4){

            message = MyTime.getDateTime() + ":[info] \n" + WriteLog.stackTrace() + message + "\n\r";

            WriteLog.writeLog(message);

        }

    }

    public static void info(String message){

        if(LogConst.LOG_LEVEL <= 3){

            message = MyTime.getDateTime() + ":[info] \n" + WriteLog.stackTrace() + message + "\n\r";

            WriteLog.writeLog(message);

        }

    }

    public static void debug(Object message){

        if(LogConst.LOG_LEVEL <= 2){

            String message2 = MyTime.getDateTime() + ":[debug] \n" + WriteLog.stackTrace() + message.toString() + "\n\r";

            WriteLog.writeLog(message2);

        }

    }


    public static void debug(String message){

        if(LogConst.LOG_LEVEL <= 2){

            message = MyTime.getDateTime() + ":[debug] \n" + WriteLog.stackTrace() + message + "\n\r";

            WriteLog.writeLog(message);

        }

    }

    public static void trace(String message){

        if(LogConst.LOG_LEVEL <= 1){

            message = MyTime.getDateTime() + ":[trace] \n\r" + WriteLog.stackTrace() + message + "\n\r";

            WriteLog.writeLog(message);

        }

    }
}
