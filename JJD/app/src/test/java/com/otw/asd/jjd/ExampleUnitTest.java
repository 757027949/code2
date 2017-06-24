package com.otw.asd.jjd;

import com.alibaba.fastjson.JSON;
import com.asd.android.util.EncryptUtil;
import com.asd.android.util.TimeUtil;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void show() {
        System.err.println(EncryptUtil.MD5("aaa"));
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        System.err.println(calendar.get(Calendar.YEAR));

        String[] strings=new String[2];
        strings[0]="aaa";
        strings[1]="bbb";
        System.err.println(JSON.toJSONString(strings));
    }

}