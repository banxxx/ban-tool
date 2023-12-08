package cn.banxx.constant;

import java.util.regex.Pattern;

/**
 * 农历工具类常量.
 *
 * @author : Ban
 * @createTime: 2023-12-08  14:03
 * @version : 1.0
 * @since : 1.0
 */

public class LunarConstant {

    public final static int[] LUNAR_INFO = {0x4bd8, 0x4ae0, 0xa570, 0x54d5, 0xd260, 0xd950, 0x5554, 0x56af, 0x9ad0, 0x55d2, 0x4ae0, 0xa5b6, 0xa4d0, 0xd250, 0xd295, 0xb54f, 0xd6a0, 0xada2, 0x95b0, 0x4977, 0x497f, 0xa4b0, 0xb4b5, 0x6a50, 0x6d40, 0xab54, 0x2b6f, 0x9570, 0x52f2, 0x4970, 0x6566, 0xd4a0, 0xea50, 0x6a95,
            0x5adf, 0x2b60, 0x86e3, 0x92ef, 0xc8d7, 0xc95f, 0xd4a0, 0xd8a6, 0xb55f, 0x56a0, 0xa5b4, 0x25df, 0x92d0, 0xd2b2, 0xa950, 0xb557, 0x6ca0, 0xb550, 0x5355, 0x4daf, 0xa5b0, 0x4573, 0x52bf, 0xa9a8, 0xe950, 0x6aa0, 0xaea6, 0xab50, 0x4b60, 0xaae4, 0xa570, 0x5260, 0xf263, 0xd950, 0x5b57, 0x56a0, 0x96d0, 0x4dd5,
            0x4ad0, 0xa4d0, 0xd4d4, 0xd250, 0xd558, 0xb540, 0xb6a0, 0x95a6, 0x95bf, 0x49b0, 0xa974, 0xa4b0, 0xb27a, 0x6a50, 0x6d40, 0xaf46, 0xab60, 0x9570, 0x4af5, 0x4970, 0x64b0, 0x74a3, 0xea50, 0x6b58, 0x5ac0, 0xab60, 0x96d5, 0x92e0, 0xc960, 0xd954, 0xd4a0, 0xda50, 0x7552, 0x56a0, 0xabb7, 0x25d0, 0x92d0, 0xcab5,
            0xa950, 0xb4a0, 0xbaa4, 0xad50, 0x55d9, 0x4ba0, 0xa5b0, 0x5176, 0x52bf, 0xa930, 0x7954, 0x6aa0, 0xad50, 0x5b52, 0x4b60, 0xa6e6, 0xa4e0, 0xd260, 0xea65, 0xd530, 0x5aa0, 0x76a3, 0x96d0, 0x4afb, 0x4ad0, 0xa4d0, 0xd0b6, 0xd25f, 0xd520, 0xdd45, 0xb5a0, 0x56d0, 0x55b2, 0x49b0, 0xa577, 0xa4b0, 0xaa50, 0xb255,
            0x6d2f, 0xada0, 0x4b63, 0x937f, 0x49f8, 0x4970, 0x64b0, 0x68a6, 0xea5f, 0x6b20, 0xa6c4, 0xaaef, 0x92e0, 0xd2e3, 0xc960, 0xd557, 0xd4a0, 0xda50, 0x5d55, 0x56a0, 0xa6d0, 0x55d4, 0x52d0, 0xa9b8, 0xa950, 0xb4a0, 0xb6a6, 0xad50, 0x55a0, 0xaba4, 0xa5b0, 0x52b0, 0xb273, 0x6930, 0x7337, 0x6aa0, 0xad50, 0x4b55,
            0x4b6f, 0xa570, 0x54e4, 0xd260, 0xe968, 0xd520, 0xdaa0, 0x6aa6, 0x56df, 0x4ae0, 0xa9d4, 0xa4d0, 0xd150, 0xf252, 0xd520};

    public final static int[] SOLAR_TERM_INFO = {0, 21208, 42467, 63836, 85337, 107014, 128867, 150921, 173149, 195551, 218072, 240693, 263343, 285989, 308563, 331033, 353350, 375494, 397447, 419210, 440795, 462224, 483532, 504758};
    /**
     * 天干
     */
    public final static String[] HEAVENLY_STEMS = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    /**
     * 地支
     */
    public final static String[] TERRESTRIAL_BRANCH  = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
    /**
     * 十二生肖
     */
    public final static String[] TWELVE_ANIMALS = {"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
    /**
     * 二十四节气
     */
    public final static String[] SOLAR_TERM = {"小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"};
    /**
     * 0至9的数字字符串
     */
    public final static String[] LUNAR_STRING_ONE = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    /**
     * 朔
     */
    public final static String[] LUNAR_STRING_TWO = {"初", "十", "廿", "卅", "正", "腊", "冬", "闰"};
    /**
     * 周/星期
     */
    public final static String[] WEEKS = {"星期六", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五"};
    /**
     * 国历节日 *表示放假日
     */
    public final static String[] NATIONAL_CALENDAR_FESTIVALS = {"0101 元旦", "0214 情人节", "0308 妇女节", "0312 植树节", "0401 愚人节", "0501 劳动节", "0504 青年节", "0601 儿童节", "0701 建党节", "0801 建军节", "0910 教师节", "1001 国庆节", "1225 圣诞节"};
    /**
     * 农历节日 *表示放假日
     */
    public final static String[] LUNAR_FESTIVAL = {"0101 春节", "0115 元宵节", "0505 端午节", "0707 七夕情人节", "0815 中秋节", "0909 重阳节", "1224 小年", "0100 除夕"};
    /**
     * 外国节日
     */
    public final static String[] FOREIGN_FESTIVALS = {"0520 母亲节", "0716 合作节", "0730 被奴役国家周"};
    /**
     * 正则表达式
     */
    public final static Pattern S_FREG = Pattern.compile("^(\\d{2})(\\d{2})([\\s\\*])(.+)$");
    /**
     * 正则表达式
     */
    public final static Pattern W_FREG = Pattern.compile("^(\\d{2})(\\d)(\\d)([\\s\\*])(.+)$");
    /**
     * 年份
     */
    public final static int FIRST = 1874;
    public final static int SECOND = 1908;
    public final static int THIRD = 1909;
    public final static int FOURTH = 1911;
    public final static int FIFTH = 1912;
    public final static int SIXTH = 1949;
    public final static int SEVENTH = 1950;
    public final static int EIGHTH = 1900;
    public final static int NINTH = 1899;
    public final static int TENTH = 1950;

}
