package cn.banxx.date;

import cn.banxx.constant.LunarConstant;
import cn.banxx.number.NumberUtils;

import java.util.*;
import java.util.regex.Matcher;

/**
 * 农历日期工具类.
 *
 * @author : Ban
 * @version : 1.0
 * @createTime: 2023-12-08  13:27
 * @since : 1.0
 */

public class LunarUtils {

    private Calendar solar;
    private int lunarYear;
    private int lunarMonth;
    private int lunarDay;
    private boolean isLeap;
    private boolean isLeapYear;
    private int solarYear;
    private int solarMonth;
    private int solarDay;
    private int cyclicalYear = 0;
    private int cyclicalMonth = 0;
    private int cyclicalDay = 0;
    private int maxDayInMonth = 29;

    private boolean isFind = false;
    private boolean isSFestival = false;
    private boolean isLFestival = false;
    private String sFestivalName = "";
    private String lFestivalName = "";
    private String description = "";
    private boolean isHoliday = false;

    /**
     * 获得所有二十四节气
     *
     * @author: Ban
     * @date: 2023/12/8 14:29
     * @param: [year]
     * @return: java.util.List<java.util.Date>
     */
    public static List<Date> allSolarTerm(int year) {
        Date[] temp;
        temp = solarTermList(year - 1);
        List<Date> solarTerm = new ArrayList<Date>(Arrays.asList(temp));
        temp = solarTermList(year);
        solarTerm.addAll(Arrays.asList(temp));
        temp = solarTermList(year + 1);
        solarTerm.addAll(Arrays.asList(temp));
        return solarTerm;
    }

    /**
     * 获得某天前后两个节气序号
     *
     * @author: Ban
     * @date: 2023/12/8 14:30
     * @param: [year, date]
     * @return: int[]
     */
    public static int[] getNearSolarTerm(int year, Date date) {
        List<Date> solarTerm = allSolarTerm(year);
        int[] returnValue = new int[2];
        for (int i = 0; i < solarTerm.size(); i++) {
            if (date.getTime() > solarTerm.get(i).getTime()) {
                continue;
            }
            if ((i % 2) == 0) {
                returnValue[0] = i - 2;
                returnValue[1] = i;
            } else {
                returnValue[0] = i - 1;
                returnValue[1] = i + 1;
            }
            break;
        }
        return returnValue;
    }

    /**
     * 获得某年中所有节气Date
     *
     * @author: Ban
     * @date: 2023/12/8 14:31
     * @param: [year]
     * @return: java.util.Date[]
     */
    public static Date[] solarTermList(int year) {
        Date[] value = new Date[LunarConstant.SOLAR_TERM.length];
        for (int i = 0; i < LunarConstant.SOLAR_TERM.length; i++) {
            Date t = getSolarTermCalendar(year, i);
            value[i] = t;
        }
        return value;
    }

    private static int toInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return -1;
        }
    }

    private synchronized void findFestival() {
        int sM = this.getSolarMonth();
        int sD = this.getSolarDay();
        int lM = this.getLunarMonth();
        int lD = this.getLunarDay();
        int sy = this.getSolarYear();
        Matcher m;
        for (String element : LunarConstant.NATIONAL_CALENDAR_FESTIVALS) {
            m = LunarConstant.S_FREG.matcher(element);
            if (m.find()) {
                if ((sM == LunarUtils.toInt(m.group(1))) && (sD == LunarUtils.toInt(m.group(2)))) {
                    this.isSFestival = true;
                    this.sFestivalName = m.group(4);
                    if ("*".equals(m.group(3))) {
                        this.isHoliday = true;
                    }
                    break;
                }
            }
        }
        for (String element : LunarConstant.LUNAR_FESTIVAL) {
            m = LunarConstant.S_FREG.matcher(element);
            if (m.find()) {
                if ((lM == LunarUtils.toInt(m.group(1))) && (lD == LunarUtils.toInt(m.group(2)))) {
                    this.isLFestival = true;
                    this.lFestivalName = m.group(4);
                    if ("*".equals(m.group(3))) {
                        this.isHoliday = true;
                    }
                    break;
                }
            }
        }
        // 月周节日
        int w, d;
        for (String element : LunarConstant.FOREIGN_FESTIVALS) {
            m = LunarConstant.W_FREG.matcher(element);
            if (m.find()) {
                if (this.getSolarMonth() == LunarUtils.toInt(m.group(1))) {
                    w = LunarUtils.toInt(m.group(2));
                    d = LunarUtils.toInt(m.group(3));
                    if ((this.solar.get(Calendar.WEEK_OF_MONTH) == w) && (this.solar.get(Calendar.DAY_OF_WEEK) == d)) {
                        this.isSFestival = true;
                        this.sFestivalName += "|" + m.group(5);
                        if ("*".equals(m.group(4))) {
                            this.isHoliday = true;
                        }
                    }
                }
            }
        }
        if ((sy > LunarConstant.FIRST) && (sy < LunarConstant.THIRD)) {
            this.description = "光绪" + (((sy - LunarConstant.FIRST) == 1) ? "元" : "" + (sy - LunarConstant.FIRST));
        }
        if ((sy > LunarConstant.SECOND) && (sy < LunarConstant.FIFTH)) {
            this.description = "宣统" + (((sy - LunarConstant.SECOND) == 1) ? "元" : String.valueOf(sy - LunarConstant.SECOND));
        }
        if ((sy > LunarConstant.FOURTH) && (sy < LunarConstant.SEVENTH)) {
            this.description = "民国" + (((sy - LunarConstant.FOURTH) == 1) ? "元" : String.valueOf(sy - LunarConstant.FOURTH));
        }
        if (sy > LunarConstant.SIXTH) {
            this.description = "共和国" + (((sy - LunarConstant.SIXTH) == 1) ? "元" : String.valueOf(sy - LunarConstant.SIXTH));
        }
        this.description += "年";
        this.sFestivalName = this.sFestivalName.replaceFirst("^\\|", "");
        this.isFind = true;
    }

    /**
     * 返回农历年闰月月份
     *
     * @param lunarYear 指定农历年份(数字)
     * @return 该农历年闰月的月份(数字, 没闰返回0)
     */
    private static int getLunarLeapMonth(int lunarYear) {
        // 数据表中,每个农历年用16bit来表示,
        // 前12bit分别表示12个月份的大小月,最后4bit表示闰月
        // 若4bit全为1或全为0,表示没闰, 否则4bit的值为闰月月份
        int leapMonth = LunarConstant.LUNAR_INFO[lunarYear - LunarConstant.EIGHTH] & 0xf;
        leapMonth = (leapMonth == 0xf ? 0 : leapMonth);
        return leapMonth;
    }

    /**
     * 返回农历年闰月的天数
     *
     * @param lunarYear 指定农历年份(数字)
     * @return 该农历年闰月的天数(数字)
     */
    private static int getLunarLeapDays(int lunarYear) {
        // 下一年最后4bit为1111,返回30(大月)
        // 下一年最后4bit不为1111,返回29(小月)
        // 若该年没有闰月,返回0
        return LunarUtils.getLunarLeapMonth(lunarYear) > 0 ? ((LunarConstant.LUNAR_INFO[lunarYear - LunarConstant.NINTH] & 0xf) == 0xf ? 30 : 29) : 0;
    }

    /**
     * 返回农历年的总天数
     *
     * @param lunarYear 指定农历年份(数字)
     * @return 该农历年的总天数(数字)
     */
    private static int getLunarYearDays(int lunarYear) {
        // 按小月计算,农历年最少有12 * 29 = 348天
        int daysInLunarYear = 348;
        // 数据表中,每个农历年用16bit来表示,
        // 前12bit分别表示12个月份的大小月,最后4bit表示闰月
        // 每个大月累加一天
        for (int i = 0x8000; i > 0x8; i >>= 1) {
            daysInLunarYear += ((LunarConstant.LUNAR_INFO[lunarYear - LunarConstant.EIGHTH] & i) != 0) ? 1 : 0;
        }
        // 加上闰月天数
        daysInLunarYear += LunarUtils.getLunarLeapDays(lunarYear);

        return daysInLunarYear;
    }

    /**
     * 返回农历年正常月份的总天数
     *
     * @param lunarYear  指定农历年份(数字)
     * @param lunarMonth 指定农历月份(数字)
     * @return 该农历年闰月的月份(数字, 没闰返回0)
     */
    private static int getLunarMonthDays(int lunarYear, int lunarMonth) {
        // 数据表中,每个农历年用16bit来表示,
        // 前12bit分别表示12个月份的大小月,最后4bit表示闰月
        int daysInLunarMonth = ((LunarConstant.LUNAR_INFO[lunarYear - LunarConstant.EIGHTH] & (0x10000 >> lunarMonth)) != 0) ? 30 : 29;
        return daysInLunarMonth;
    }

    /**
     * 取 Date 对象中用全球标准时间 (UTC) 表示的日期
     *
     * @param date 指定日期
     * @return UTC 全球标准时间 (UTC) 表示的日期
     */
    public static synchronized int getUTCDay(Date date) {
        LunarUtils.makeUTCCalendar();
        synchronized (utcCal) {
            utcCal.clear();
            utcCal.setTimeInMillis(date.getTime());
            return utcCal.get(Calendar.DAY_OF_MONTH);
        }
    }

    private static GregorianCalendar utcCal = null;

    private static synchronized void makeUTCCalendar() {
        if (LunarUtils.utcCal == null) {
            LunarUtils.utcCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        }
    }

    /**
     * 返回全球标准时间 (UTC) (或 GMT) 的 1970 年 1 月 1 日到所指定日期之间所间隔的毫秒数。
     *
     * @param y   指定年份
     * @param m   指定月份
     * @param d   指定日期
     * @param h   指定小时
     * @param min 指定分钟
     * @param sec 指定秒数
     * @return 全球标准时间 (UTC) (或 GMT) 的 1970 年 1 月 1 日到所指定日期之间所间隔的毫秒数
     */
    public static synchronized long UTC(int y, int m, int d, int h, int min, int sec) {
        LunarUtils.makeUTCCalendar();
        synchronized (utcCal) {
            utcCal.clear();
            utcCal.set(y, m, d, h, min, sec);
            return utcCal.getTimeInMillis();
        }
    }

    /**
     * 返回公历年节气的日期
     *
     * @param solarYear 指定公历年份(数字)
     * @param index     指定节气序号(数字,0从小寒算起)
     * @return 日期(数字, 所在月份的第几天)
     */
    private static int getSolarTermDay(int solarYear, int index) {

        return LunarUtils.getUTCDay(getSolarTermCalendar(solarYear, index));
    }

    /**
     * 返回公历年节气的日期
     *
     * @param solarYear 指定公历年份(数字)
     * @param index     指定节气序号(数字,0从小寒算起)
     * @return 日期(数字, 所在月份的第几天)
     */
    public static Date getSolarTermCalendar(int solarYear, int index) {
        long l = ((long) 31556925974.7 * (solarYear - LunarConstant.EIGHTH)) + (LunarConstant.SOLAR_TERM_INFO[index] * 60000L);
        l = l + LunarUtils.UTC(1900, 0, 6, 2, 5, 0);
        return new Date(l);
    }

    /**
     * 通过 Date 对象构建农历信息
     *
     * @param date 指定日期对象
     */
    public LunarUtils(Date date) {
        if (date == null) {
            date = new Date();
        }
        this.init(date.getTime());
    }

    public LunarUtils() {
        this.init(System.currentTimeMillis());
    }

    /**
     * 通过 TimeInMillis 构建农历信息
     *
     * @param TimeInMillis
     */
    public LunarUtils(long TimeInMillis) {
        this.init(TimeInMillis);
    }

    private void init(long TimeInMillis) {
        this.solar = Calendar.getInstance();
        this.solar.setTimeInMillis(TimeInMillis);
        Calendar baseDate = new GregorianCalendar(1900, 0, 31);
        long offset = (TimeInMillis - baseDate.getTimeInMillis()) / 86400000;
        // 按农历年递减每年的农历天数，确定农历年份
        this.lunarYear = 1900;
        int daysInLunarYear = LunarUtils.getLunarYearDays(this.lunarYear);
        while ((this.lunarYear < 2100) && (offset >= daysInLunarYear)) {
            offset -= daysInLunarYear;
            daysInLunarYear = LunarUtils.getLunarYearDays(++this.lunarYear);
        }
        // 农历年数字

        // 按农历月递减每月的农历天数，确定农历月份
        int lunarMonth = 1;
        // 所在农历年闰哪个月,若没有返回0
        int leapMonth = LunarUtils.getLunarLeapMonth(this.lunarYear);
        // 是否闰年
        this.isLeapYear = leapMonth > 0;
        // 闰月是否递减
        boolean leapDec = false;
        boolean isLeap = false;
        int daysInLunarMonth = 0;
        while ((lunarMonth < 13) && (offset > 0)) {
            if (isLeap && leapDec) { // 如果是闰年,并且是闰月
                // 所在农历年闰月的天数
                daysInLunarMonth = LunarUtils.getLunarLeapDays(this.lunarYear);
                leapDec = false;
            } else {
                // 所在农历年指定月的天数
                daysInLunarMonth = LunarUtils.getLunarMonthDays(this.lunarYear, lunarMonth);
            }
            if (offset < daysInLunarMonth) {
                break;
            }
            offset -= daysInLunarMonth;

            if ((leapMonth == lunarMonth) && (isLeap == false)) {
                // 下个月是闰月
                leapDec = true;
                isLeap = true;
            } else {
                // 月份递增
                lunarMonth++;
            }
        }
        this.maxDayInMonth = daysInLunarMonth;
        // 农历月数字
        this.lunarMonth = lunarMonth;
        // 是否闰月
        this.isLeap = ((lunarMonth == leapMonth) && isLeap);
        // 农历日数字
        this.lunarDay = (int) offset + 1;
        // 取得干支历
        this.getCyclicalData();
    }

    /**
     * 取干支历 不是历年，历月干支，而是中国的从立春节气开始的节月，是中国的太阳十二宫，阳历的。
     */
    private void getCyclicalData() {
        this.solarYear = this.solar.get(Calendar.YEAR);
        this.solarMonth = this.solar.get(Calendar.MONTH);
        this.solarDay = this.solar.get(Calendar.DAY_OF_MONTH);
        // 干支历
        int cyclicalYear = 0;
        int cyclicalMonth = 0;
        int cyclicalDay = 0;
        // 干支年 1900年立春後为庚子年(60进制36)
        int term2 = LunarUtils.getSolarTermDay(solarYear, 2); // 立春日期
        // 依节气调整二月分的年柱, 以立春为界
        if ((solarMonth < 1) || ((solarMonth == 1) && (solarDay < term2))) {
            cyclicalYear = (((solarYear - LunarConstant.EIGHTH) + 36) - 1) % 60;
        } else {
            cyclicalYear = ((solarYear - LunarConstant.EIGHTH) + 36) % 60;
        }
        // 干支月 1900年1月小寒以前为 丙子月(60进制12)
        int firstNode = LunarUtils.getSolarTermDay(solarYear, solarMonth * 2); // 传回当月「节」为几日开始
        // 依节气月柱, 以「节」为界
        if (solarDay < firstNode) {
            cyclicalMonth = (((solarYear - LunarConstant.EIGHTH) * 12) + solarMonth + 12) % 60;
        } else {
            cyclicalMonth = (((solarYear - LunarConstant.EIGHTH) * 12) + solarMonth + 13) % 60;
        }
        // 当月一日与 1900/1/1 相差天数
        // 1900/1/1与 1970/1/1 相差25567日, 1900/1/1 日柱为甲戌日(60进制10)
        cyclicalDay = (int) ((LunarUtils.UTC(solarYear, solarMonth, solarDay, 0, 0, 0) / 86400000) + 25567 + 10) % 60;
        this.cyclicalYear = cyclicalYear;
        this.cyclicalMonth = cyclicalMonth;
        this.cyclicalDay = cyclicalDay;
    }

    /**
     * 取农历年生肖
     *
     * @return 农历年生肖(例 : 龙)
     */
    public String getTwelveAnimalString() {
        return LunarConstant.TWELVE_ANIMALS[(this.lunarYear - 4) % 12];
    }

    /**
     * 返回公历日期的节气字符串
     *
     * @return 二十四节气字符串, 若不是节气日, 返回空串(例 : 冬至)
     */
    public String getTermString() {
        // 二十四节气
        String termString = "";
        if (LunarUtils.getSolarTermDay(solarYear, solarMonth * 2) == solarDay) {
            termString = LunarConstant.SOLAR_TERM[solarMonth * 2];
        } else if (LunarUtils.getSolarTermDay(solarYear, (solarMonth * 2) + 1) == solarDay) {
            termString = LunarConstant.SOLAR_TERM[(solarMonth * 2) + 1];
        }
        return termString;
    }

    /**
     * 取得干支历字符串
     *
     * @return 干支历字符串(例 : 甲子年甲子月甲子日)
     */
    public String getCyclicalDateString() {
        return this.getCyclicalYear() + "年" + this.getCyclicalMonth() + "月" + this.getCyclicalDay() + "日";
    }

    /**
     * 年份天干
     *
     * @return 年份天干
     */
    public int getHeStYear() {
        return LunarUtils.getHeSt(this.cyclicalYear);
    }

    /**
     * 月份天干
     *
     * @return 月份天干
     */
    public int getHeStMonth() {
        return LunarUtils.getHeSt(this.cyclicalMonth);
    }

    /**
     * 日期天干
     *
     * @return 日期天干
     */
    public int getHeStDay() {
        return LunarUtils.getHeSt(this.cyclicalDay);
    }

    /**
     * 年份地支
     *
     * @return 年分地支
     */
    public int getTeBrYear() {
        return LunarUtils.getTeBr(this.cyclicalYear);
    }

    /**
     * 月份地支
     *
     * @return 月份地支
     */
    public int getTeBrMonth() {
        return LunarUtils.getTeBr(this.cyclicalMonth);
    }

    /**
     * 日期地支
     *
     * @return 日期地支
     */
    public int getTeBrDay() {
        return LunarUtils.getTeBr(this.cyclicalDay);
    }

    /**
     * 取得干支年字符串
     *
     * @return 干支年字符串
     */
    public String getCyclicalYear() {
        return LunarUtils.getCyclicalString(this.cyclicalYear);
    }

    /**
     * 取得干支月字符串
     *
     * @return 干支月字符串
     */
    public String getCyclicalMonth() {
        return LunarUtils.getCyclicalString(this.cyclicalMonth);
    }

    /**
     * 取得干支日字符串
     *
     * @return 干支日字符串
     */
    public String getCyclicalDay() {
        return LunarUtils.getCyclicalString(this.cyclicalDay);
    }

    /**
     * 返回农历日期字符串
     *
     * @return 农历日期字符串
     */
    public String getLunarDayString() {
        return LunarUtils.getLunarDayString(this.lunarDay);
    }

    /**
     * 返回农历日期字符串
     *
     * @return 农历日期字符串
     */
    public String getLunarMonthString() {
        return (this.isLeap() ? "闰" : "") + LunarUtils.getLunarMonthString(this.lunarMonth);
    }

    /**
     * 返回农历日期字符串
     *
     * @return 农历日期字符串
     */
    public String getLunarYearString() {
        return LunarUtils.getLunarYearString(this.lunarYear);
    }

    /**
     * 返回农历表示字符串
     *
     * @return 农历字符串(例 : 甲子年正月初三)
     */
    public String getLunarDateString() {
        return this.getLunarYearString() + "年" + this.getLunarMonthString() + "月" + this.getLunarDayString() + "日";
    }

    /**
     * 返回农历表示字符串
     *
     * @return 农历字符串(例 : 正月初三)
     */
    public String getLunarMonthDateString() {
        return this.getLunarMonthString() + "月" + this.getLunarDayString() + "日";
    }

    /**
     * 农历年是否是闰月
     *
     * @return 农历年是否是闰月
     */
    public boolean isLeap() {
        return isLeap;
    }

    /**
     * 农历年是否是闰年
     *
     * @return 农历年是否是闰年
     */
    public boolean isLeapYear() {
        return isLeapYear;
    }

    /**
     * 当前农历月是否是大月
     *
     * @return 当前农历月是大月
     */
    public boolean isBigMonth() {
        return this.getMaxDayInMonth() > 29;
    }

    /**
     * 当前农历月有多少天
     *
     * @return 当前农历月有多少天
     */
    public int getMaxDayInMonth() {
        return this.maxDayInMonth;
    }

    /**
     * 农历日期
     *
     * @return 农历日期
     */
    public int getLunarDay() {
        return lunarDay;
    }

    /**
     * 农历月份
     *
     * @return 农历月份
     */
    public int getLunarMonth() {
        return lunarMonth;
    }

    /**
     * 农历年份
     *
     * @return 农历年份
     */
    public int getLunarYear() {
        return lunarYear;
    }

    /**
     * 公历日期
     *
     * @return 公历日期
     */
    public int getSolarDay() {
        return solarDay;
    }

    /**
     * 公历月份
     *
     * @return 公历月份 (不是从0算起)
     */
    public int getSolarMonth() {
        return solarMonth + 1;
    }

    /**
     * 公历年份
     *
     * @return 公历年份
     */
    public int getSolarYear() {
        return solarYear;
    }

    /**
     * 星期几
     *
     * @return 星期几(星期日为 : 1, 星期六为 : 7)
     */
    public int getDayOfWeek() {
        return this.solar.get(Calendar.DAY_OF_WEEK);
    }

    public String getDayOfWeekString() {
        return LunarConstant.WEEKS[this.solar.get(Calendar.DAY_OF_WEEK)];
    }

    /**
     * 黑色星期五
     *
     * @return 是否黑色星期五
     */
    public boolean isBlackFriday() {
        return ((this.getSolarDay() == 13) && (this.solar.get(Calendar.DAY_OF_WEEK) == 6));
    }

    /**
     * 是否是今日
     *
     * @return 是否是今日
     */
    public boolean isToday() {
        Calendar clr = Calendar.getInstance();
        return (clr.get(Calendar.YEAR) == this.solarYear) && (clr.get(Calendar.MONTH) == this.solarMonth) && (clr.get(Calendar.DAY_OF_MONTH) == this.solarDay);
    }

    /**
     * 取得公历节日名称
     *
     * @return 公历节日名称, 如果不是节日返回空串
     */
    public String getSFestivalName() {
        return this.sFestivalName;
    }

    /**
     * 取得农历节日名称
     *
     * @return 农历节日名称, 如果不是节日返回空串
     */
    public String getLFestivalName() {
        return this.lFestivalName;
    }

    /**
     * 是否是农历节日
     *
     * @return 是否是农历节日
     */
    public boolean isLFestival() {
        if (!this.isFind) {
            this.findFestival();
        }
        return this.isLFestival;
    }

    /**
     * 是否是公历节日
     *
     * @return 是否是公历节日
     */
    public boolean isSFestival() {
        if (!this.isFind) {
            this.findFestival();
        }
        return this.isSFestival;
    }

    /**
     * 是否是节日
     *
     * @return 是否是节日
     */
    public boolean isFestival() {
        return this.isSFestival() || this.isLFestival();
    }

    /**
     * 是否是放假日
     *
     * @return 是否是放假日
     */
    public boolean isHoliday() {
        if (!this.isFind) {
            this.findFestival();
        }
        return this.isHoliday;
    }

    /**
     * 其它日期说明
     *
     * @return 日期说明(如 : 民国2年)
     */
    public String getDescription() {
        if (!this.isFind) {
            this.findFestival();
        }
        return this.description;
    }

    /**
     * 干支字符串
     *
     * @param cyclicalNumber 指定干支位置(数字,0为甲子)
     * @return 干支字符串
     */
    private static String getCyclicalString(int cyclicalNumber) {
        return LunarConstant.HEAVENLY_STEMS[LunarUtils.getHeSt(cyclicalNumber)] + LunarConstant.TERRESTRIAL_BRANCH[LunarUtils.getTeBr(cyclicalNumber)];
    }

    /**
     * 获得地支
     *
     * @param cyclicalNumber
     * @return 地支 (数字)
     */
    private static int getTeBr(int cyclicalNumber) {
        return cyclicalNumber % 12;
    }

    /**
     * 获得天干
     *
     * @param cyclicalNumber
     * @return 天干 (数字)
     */
    private static int getHeSt(int cyclicalNumber) {
        return cyclicalNumber % 10;
    }

    /**
     * 返回指定数字的农历年份表示字符串
     *
     * @param lunarYear 农历年份(数字,0为甲子)
     * @return 农历年份字符串
     */
    private static String getLunarYearString(int lunarYear) {
        return LunarUtils.getCyclicalString((lunarYear - LunarConstant.EIGHTH) + 36);
    }

    /**
     * 返回指定数字的农历月份表示字符串
     *
     * @param lunarMonth 农历月份(数字)
     * @return 农历月份字符串 (例:正)
     */
    private static String getLunarMonthString(int lunarMonth) {
        String lunarMonthString = "";
        if (lunarMonth == 1) {
            lunarMonthString = LunarConstant.LUNAR_STRING_TWO[4];
        } else {
            if (lunarMonth > 9) {
                lunarMonthString += LunarConstant.LUNAR_STRING_TWO[1];
            }
            if ((lunarMonth % 10) > 0) {
                lunarMonthString += LunarConstant.LUNAR_STRING_ONE[lunarMonth % 10];
            }
        }
        return lunarMonthString;
    }

    /**
     * 返回指定数字的农历日表示字符串
     *
     * @param lunarDay 农历日(数字)
     * @return 农历日字符串 (例: 廿一)
     */
    private static String getLunarDayString(int lunarDay) {
        if ((lunarDay < 1) || (lunarDay > 30)) {
            return "";
        }
        int i1 = lunarDay / 10;
        int i2 = lunarDay % 10;
        String c1 = LunarConstant.LUNAR_STRING_TWO[i1];
        String c2 = LunarConstant.LUNAR_STRING_ONE[i2];
        if (lunarDay < 11) {
            c1 = LunarConstant.LUNAR_STRING_TWO[0];
        }
        if (i2 == 0) {
            c2 = LunarConstant.LUNAR_STRING_TWO[1];
        }
        return c1 + c2;
    }
}
