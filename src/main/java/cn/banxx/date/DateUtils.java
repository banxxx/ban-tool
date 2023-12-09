package cn.banxx.date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
    private static boolean isThisTime(Date time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(time);
        String now = sdf.format(new Date());
        return param.equals(now);
    }

    /**
     * 计算传入时间加上一年后与今天的时间差<br/>
     * 一般用于计算会员剩余时间.
     * @author: Ban
     * @date: 2023/12/9 1:05
     * @param: [date]
     */
    public static Long dateTo(Date date) {
        //将传过来的 date 转 localDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //通过当前日期计算一年后的日期
        LocalDate nextYears = localDate.plus(1, ChronoUnit.YEARS);
        //返回相差时间
        return ChronoUnit.DAYS.between(LocalDate.now(),nextYears);
    }


    public static void main(String[] args) {
    }
}
