package com.utils.log.other;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTime {
    public static String getDateName(){
        Date date = new Date(); // this object contains the current date value

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(date);
    }

    public static String getDateTime(){
        Date date = new Date(); // this object contains the current date value

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        return formatter.format(date);
    }
}
