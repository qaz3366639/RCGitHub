package org.rc.rclibrary.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @Description 时间戳与字符串转换
 * @Author czm
 * @Version V0.1
 * @CreateDate 2014-9-17 下午2:20:25
 * @ModifyAuthor
 * @ModifyDescri
 * @ModifyDate
 */
public class TimeStamp
{
	/**
	 *  将日期时间字符串转为时间戳
	 * @param user_time 输入日期时间字符串
	 * @return 返回时间戳
	 */
	public static String getDateTime(String user_time)
	{

		if(user_time.length() == 0)
			return user_time;
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		Date d;

		try
		{

			d = sdf.parse(user_time);
			String str = String.valueOf(d.getTime() / 1000L);
			re_time = str.substring(0, 10);
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return re_time;
	}

	/**
	 * 将日期字符串转化为时间戳
	 * @param user_time 输入日期字符串
	 * @return 返回时间戳
	 */
	public static String getDate(String user_time)
	{

		if(user_time.length() == 0)
			return user_time;
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
		Date d;

		try
		{

			d = sdf.parse(user_time);
			String str = String.valueOf(d.getTime() / 1000L);
			re_time = str.substring(0, 10);
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return re_time;
	}


	/**
	 * 将时间戳转为字符串
	 * @param cc_time 输入时间戳
	 * @return 返回日期字符串
	 */
	public static String timeStamp2String(String cc_time, SimpleDateFormat sdf)
	{
		if(cc_time.length() == 0 || cc_time == "null")
			return "";
		String re_StrTime = null;

		// 例如：cc_time=1291778220

		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

    /**
     * 将时间戳转为字符串
     * @param time 输入时间戳
     * @return 返回日期字符串
     */
    public static String TimeStamp2String(long time, SimpleDateFormat sdf)
    {

        String re_StrTime = null;
        // 例如：cc_time=1291778220

        long lcc_time = Long.valueOf(time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /**
     * 获取“年-月-日”的日期格式
     * @return
     */
    public static String getStrDate(String cc_time) {
        if(cc_time.length() == 0 || cc_time == "null")
            return "";
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);

        // 例如：cc_time=1291778220

        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

	/**
	 * 获取“时:分:秒”的时间格式
	 * @param date
	 * @return
	 */
	public static String getStrTime(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		return sdf.format(date);

	}

	/**
	 * 获取“年-月-日 时:分:秒”的日期格式
	 * @param date Date类型的日期
	 * @return
	 */
	public static String getStrDateAndTime(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		return sdf.format(date);

	}

	/**
	 * 把yyyy-MM-dd HH:mm:ss.s格式的时间转换成yyyy-MM-dd HH:mm:ss
	 * @author WuRuiQiang
	 * 2014-9-19
	 * @param str 输入时间
	 * @return 输出转换格式后的时间
	 */
	public static String transFormat2Date(String str) {
		if(str == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s", Locale.SIMPLIFIED_CHINESE);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sdf2.format(date);
	}

}