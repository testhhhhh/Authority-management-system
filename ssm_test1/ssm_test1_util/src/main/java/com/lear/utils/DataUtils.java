package com.lear.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {
    //日期转换成字符串
    public static String dateToString(Date data, String patt){
        SimpleDateFormat sdf = new SimpleDateFormat(patt);
        return sdf.format(data);
    }
    //字符串转换成日期
    public static Date stringToDate(String str,String patt) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(patt);
        return sdf.parse(str);
    }
}
