package cn.banxx.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 数字工具类常量.
 *
 * @author : Ban
 * @createTime: 2023-12-08  17:34
 * @version : 1.0
 * @since : 1.0
 */

public class NumberConstant {

    /**
     * num 表示数字，lower表示小写，upper表示大写.
     */
    public static final String[] NUM_LOWER = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
    public static final String[] NUM_UPPER = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

    /**
     * unit 表示单位权值，lower表示小写，upper表示大写.
     */
    public static final String[] UNIT_LOWER = { "", "十", "百", "千" };
    public static final String[] UNIT_UPPER = { "", "拾", "佰", "仟"};
    public static final String[] UNIT_COMMON = {"","万", "亿","兆","京","垓","秭","穰","沟","涧","正","载"};

    /**
     * 允许的格式.
     */
    public static final List<String> PROMISS_TYPES = Arrays.asList("INTEGER","INT","LONG","DECIMAL","FLOAT","DOUBLE","STRING","BYTE","TYPE","SHORT");

}
