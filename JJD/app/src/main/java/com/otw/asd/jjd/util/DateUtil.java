package com.otw.asd.jjd.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /**
     * 计算当前时间与过去时间差
     *
     * @param dateStr 过去时间
     * @return
     */
    public static String getDateDiff(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        Date date = null;
        try {
            date = sdf.parse(dateStr);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        long past = currentDate.getTime() - date.getTime();
        long day = past / (24 * 60 * 60 * 1000);
        long hour = (past / (60 * 60 * 1000) - day * 24);
        long min = ((past / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (past / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String time = day + "D/" + hour + "H/" + min + "M/" + s + "S/";
        return time;
    }

    public static void main(String[] orgs) {
        String dateStr = "2016-09-30 12:00:00";
        String days = DateUtil.getDateDiff(dateStr);
        System.out.println(days);
    }
}
