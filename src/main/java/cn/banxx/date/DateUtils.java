package cn.banxx.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类.
 *
 * @author : Ban
 * @createTime: 2023-11-29  14:31
 * @version : 1.0
 * @since : 1.0
 */

public class DateUtils {

    /**
     * 判断选择的日期是否是本日
     */
    public static boolean isToday(Date time) {
        return isThisTime(time, "yyyy-MM-dd");
    }

    /**
     * 判断选择的日期是否是本月
     */
    public static boolean isMonth(Date time) {
        return isThisTime(time, "yyyy-MM");
    }

    /**
     * 判断选择的日期是否是今年
     */
    public static boolean isYear(Date time) {
        return isThisTime(time, "yyyy");
    }

    /**
     * 公共方法 <br/>
     * time - 参数时间  <br/>
     * pattern - 匹配规则
     */
    public static boolean isThisTime(Date time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(time);
        String now = sdf.format(new Date());
        return param.equals(now);
    }
}
