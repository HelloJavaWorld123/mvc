package com.jzy.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneCheckUtil {

    //大陆手机号规则
    public static String regExpCH = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
    //香港手机号规则
    public static String regExpHK = "^(5|6|8|9)\\\\d{7}$";

    public static boolean isPhoneLegal(String num){
        return isPhoneLegal(num,regExpCH) || isPhoneLegal(num,regExpHK);
    }
    public static boolean isPhoneLegal(String num,String regExp){
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(num);
        return matcher.matches();
    }

}
