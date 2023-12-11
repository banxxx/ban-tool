package cn.banxx.constant;

import java.util.regex.Pattern;

/**
 * 字符串常量.
 *
 * @author : Ban
 * @createTime: 2023-12-10  02:02
 * @version : 1.0
 * @since : 1.0
 */

public class StringContant {

    /**
     * 驼峰转下划线正则表达式规则
     */
    public static final Pattern TPATTERN = Pattern.compile("[A-Z0-9]");

    /**
     * 下划线转驼峰正则表达式规则
     */
    public static final char UNICON = '_';
}
