package com.example.xiao.Schedule.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiaowentao85336773 on 2018/3/6.
 * 将日期转换为所需要的形式
 */

public class DateUtil {
    public static String translatedate(Date date){
        String stringdate;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        stringdate=sf.format(date).toString();
        return stringdate;
    }

}
