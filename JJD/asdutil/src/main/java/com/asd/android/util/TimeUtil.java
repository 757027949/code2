package com.asd.android.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtil {
    private static TimeUtil timeUtil;

    private float day;
    private float hour;
    private float min;
    private float s;

    /**
     * 过去的天数
     *
     * @return
     */
    public float getDay() {
        return day;
    }

    /**
     * 不足一天的时长
     *
     * @return
     */
    public float getHour() {
        return hour;
    }

    /**
     * 不足一小时的分钟数
     *
     * @return
     */
    public float getMin() {
        return min;
    }

    /**
     * 不足一分钟的秒数
     *
     * @return
     */
    public float getS() {
        return s;
    }

    public synchronized static TimeUtil getInstall() {
        if (null == timeUtil) {
            timeUtil = new TimeUtil();
        }
        return timeUtil;
    }


    /**
     * 比较时间
     *
     * @param firstTime
     * @param twoTime
     * @return 小于0：firstTime < twoTime 0:firstTime = twoTime 大于0：firstTime >
     * twoTime
     */
    public int compareTime(String firstTime, String twoTime, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        try {
            calendar1.setTime(df.parse(firstTime));
            calendar2.setTime(df.parse(twoTime));
        } catch (ParseException e) {
        }
        return calendar1.compareTo(calendar2);
    }

    /**
     * 比较time时间是否为过去时间
     *
     * @param time
     * @return true:过去时间 false:反之
     */
    public boolean isPassedTime(String time, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        long l = 0;
        try {
            Date now = df.parse(time);
            l = df.parse(df.format(new Date())).getTime() - now.getTime();
            day = l / (24 * 60 * 60 * 1000);
            hour = (l / (60 * 60 * 1000) - day * 24);
            min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (l < 0) {
            return false;
        } else {
            return true;
        }
        // if (day >= 0 || hour > 0 || min > 0 || s > 0) {
        // return true;
        // } else {
        // return false;
        // }
    }

    /**
     * 与今天比较时间
     *
     * @param date
     * @return >0:有效 =0：当天 <0：过去
     * @throws Exception
     */
    public int compareToday(Date date) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        try {
            calendar1.setTime(date);
            calendar2.setTime(new Date());
        } catch (Exception e) {
            throw e;
        }
        return calendar1.compareTo(calendar2);
    }

    /**
     * 初始化时间
     *
     * @param nowTime
     * @param dateTime
     */
    public void initPassedTime(String nowTime, String dateTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date now = df.parse(checkTimeForm(nowTime));
            Date date = df.parse(checkTimeForm(dateTime));
            long l = now.getTime() - date.getTime();
            day = l / (24 * 60 * 60 * 1000);
            hour = (l / (60 * 60 * 1000) - day * 24);
            min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (Exception e) {
        }
    }

    /**
     * 获取两时间之间的小时
     *
     * @param endTime
     * @param startTime
     * @param format    endTime-startTime
     * @return
     */
    public int getHours(String endTime, String startTime, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            Date now = df.parse(endTime);
            Date date = df.parse(startTime);
            long l = now.getTime() - date.getTime();
            day = l / (24 * 60 * 60 * 1000);
            hour = (l / (60 * 60 * 1000) - day * 24);
            min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (Exception e) {
        }
        if (min > 0) {
            hour++;
        }
        return (int) hour;
    }


    /**
     * 规则时间格式
     *
     * @param time (yyyy-MM-dd或者yyyy/MM/dd) 格式后格式 ： yyyy-MM-dd HH:mm:ss
     * @return
     */
    private String checkTimeForm(String time) {
        boolean validString = true;
        String string = "";
        if (!"".equals(time)) {
            string = time.replace(time.substring(4, 5), "-");
            String[] strings = string.split("-");
            if (strings[1].length() < 2) {
                strings[1] = "0" + strings[1];
                validString = false;
            }
            if (strings[2].length() < 2) {
                strings[2] = "0" + strings[2];
                validString = false;
            }
            if (false == validString) {
                string = "";
                for (int i = 0; i < strings.length; i++) {
                    string += strings[i];
                    if (i < strings.length - 1) {
                        string += "-";
                    }
                }
            }
            if (string.length() == 10) {
                string += " 00:00:00";
            }
        }
        return string;
    }

    /**
     * 判断是否为当天
     *
     * @param date
     * @return 0：当天 -n:过去N天 +n:今后N天
     */
    public int isToday(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 号
        int i = Integer.parseInt(df.format(date).substring(8, 10));
        int j = Integer.parseInt(df.format(new Date()).substring(8, 10));
        return i - j;
    }

    /**
     * 获取当前时间月份 yyyy-MM
     *
     * @return
     */
    public String getCurrentTimeFormat() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        return df.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @param format 时间格式
     * @return
     */
    public String getCurrentTimeFormat(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * 获取时间
     *
     * @param format 时间格式
     * @param days   -1:昨天 0:今天 1;明天
     * @return
     */
    public String getTime(String format, int days) {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, days);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }


    /**
     * 星期几
     * @param days -1:昨天 0:今天 1;明天
     * @return
     */
    public String getWeekOfDate(int days) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        Date curDate = new Date(System.currentTimeMillis());
        cal.setTime(curDate);
        cal.add(Calendar.DATE, days);//把日期往后增加一天.整数往后推,负数往前移动
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


}
