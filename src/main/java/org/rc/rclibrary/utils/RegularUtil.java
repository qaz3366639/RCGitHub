package org.rc.rclibrary.utils;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-02 17:15
 */
public class RegularUtil {

    public static String getMobileValidator() {
        return "^([1][3|5|7|8]\\d{9})|([6]\\d{5})$";
    }

    public static String getEmailValidator() {
        return "\\w+@(\\w+.)+[a-z]{2,3}";
    }

    public static String getNotNullValidator() {
        return "^\\S+$";
    }

    public static String getUserNameValidator() {
        return "^\\w{5,15}$";
    }

    public static String getPassWordValidator() {
        return "^[a-z0-9A-Z\\._]{6,16}$";
    }
}
