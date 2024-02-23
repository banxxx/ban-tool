package cn.banxx.string;

import cn.banxx.constant.StringContant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 判断是否包含中文
     * @param value 内容
     * @return 返回结果
     */
    public static boolean containChinese(String value) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(value);
        return m.find();
    }

    /**
     * 驼峰转下划线
     * @param value 待转换值
     * @return 返回结果
     */
    public static String camelToUnderscore(String value) {
        Matcher matcher = StringContant.TPATTERN.matcher(value);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     * @param value 待转换值
     * @return 返回结果
     */
    public static String underscoreToCamel(String value) {
        if (org.apache.commons.lang3.StringUtils.isBlank(value)) {
            return "";
        }
        int len = value.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = Character.toLowerCase(value.charAt(i));
            if (c == StringContant.UNICON) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(value.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
