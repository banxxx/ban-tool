package cn.banxx.number;

import cn.banxx.constant.NumberConstant;

import java.util.Objects;
import java.util.UUID;

/**
 * 数字工具类
 */

public class NumberUtils {

    /**
     * 数字转化为小写的汉字
     *
     * @param number 将要转化的数字
     * @return 小写汉字
     */
    public static String toChineseLower(Object number) {
        return format(number, NumberConstant.NUM_LOWER, NumberConstant.UNIT_LOWER);
    }

    /**
     * 数字转化为大写的汉字
     *
     * @param number 将要转化的数字
     * @return 大写汉字
     */
    public static String toChineseUpper(Object number) {
        return format(number, NumberConstant.NUM_UPPER, NumberConstant.UNIT_UPPER);
    }

    /**
     * 数字转化为小写的纯汉字(不带进制)
     *
     * @param number 将要转化的数字
     * @return 小写汉字
     */
    public static String toPureChineseLower(Object number) {
        return format(number, NumberConstant.NUM_LOWER, null);
    }

    /**
     * 数字转化为大写的纯汉字(不带进制)
     *
     * @param number 将要转化的数字
     * @return 大写汉字
     */
    public static String toPureChineseUpper(Object number) {
        return format(number, NumberConstant.NUM_UPPER, null);
    }

    /**
     * 格式化数字
     *
     * @param number      原数字
     * @param numArray 数字大小写数组
     * @param unit     单位权值
     * @return
     */
    private static String format(Object number, String[] numArray, String[] unit) {
        if (!NumberConstant.PROMISS_TYPES.contains(number.getClass().getSimpleName().toUpperCase())) {
            throw new RuntimeException("不支持的格式类型");
        }
        //获取整数部分
        String intNum = getInt(String.valueOf(number));
        //获取小数部分
        String decimal = getFraction(String.valueOf(number));
        //格式化整数部分
        String result = formatIntPart(intNum, numArray, unit);
        //小数部分不为空
        if (!"".equals(decimal)) {
            //格式化小数部分
            result += "点" + formatFractionalPart(decimal, numArray);
        }
        return result;
    }

    /**
     * 格式化整数部分
     *
     * @param number 整数部分
     * @param numArray 数字大小写数组
     * @return
     */
    private static String formatIntPart(String number, String[] numArray, String[] unit) {
        StringBuilder sb = new StringBuilder();
        if (Objects.isNull(unit)) {
            for (char s : number.toCharArray()) {
                int anInt = Integer.parseInt(String.valueOf(s));
                sb.append(numArray[anInt]);
            }
        } else {
            //按4位分割成不同的组(不足四位的前面补0)
            Integer[] intNums = split2IntArray(number);
            boolean zero = false;
            for (int i = 0; i < intNums.length; i++) {
                //格式化当前4位
                String r = formatInt(intNums[i], numArray, unit);
                if ("".equals(r)) {
                    if ((i + 1) == intNums.length) {
                        //结果中追加“零”
                        sb.append(numArray[0]);
                    } else {
                        zero = true;
                    }
                }
                //当前4位格式化结果不为空(即不为0)
                else {
                    //如果前4位为0，当前4位不为0
                    if (zero || (i > 0 && intNums[i] < 1000)) {
                        // 结果中追加“零”
                        sb.append(numArray[0]);
                    }
                    sb.append(r);
                    //在结果中添加权值
                    sb.append(NumberConstant.UNIT_COMMON[intNums.length - 1 - i]);
                    zero = false;
                }
            }
        }
        return sb.toString();
    }

    /**
     * 格式化小数部分
     *
     * @param decimal 小数部分
     * @param numArray 数字大小写数组
     * @return
     */
    private static String formatFractionalPart(String decimal, String[] numArray) {
        char[] val = String.valueOf(decimal).toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : val) {
            int n = Integer.parseInt(c + "");
            sb.append(numArray[n]);
        }
        return sb.toString();
    }

    /**
     * 获取整数部分
     *
     * @param number 数字
     * @return 整数部分
     */
    private static String getInt(String number) {
        //检查格式
        checkNum(number);
        char[] val = String.valueOf(number).toCharArray();
        StringBuilder sb = new StringBuilder();
        int t, s = 0;
        for (char c : val) {
            if (c == '.') {
                break;
            }
            t = Integer.parseInt(c + "", 16);
            if (s + t == 0) {
                continue;
            }
            sb.append(t);
            s += t;
        }
        return (sb.length() == 0 ? "0" : sb.toString());
    }

    /**
     * 获取小数部分
     *
     * @param number 数字
     * @return 数字小数部分
     */
    private static String getFraction(String number) {
        int i = number.lastIndexOf(".");
        if (number.indexOf(".") != i) {
            throw new RuntimeException("数字格式不正确，最多只能有一位小数点！");
        }
        String fraction = "";
        if (i >= 0) {
            fraction = getInt(new StringBuffer(number).reverse().toString());
            if ("0".equals(fraction)) {
                return "";
            }
        }
        return new StringBuffer(fraction).reverse().toString();
    }

    /**
     * 检查数字格式
     *
     * @param number 数字
     */
    private static void checkNum(String number) {
        if (number.indexOf(".") != number.lastIndexOf(".")) {
            throw new RuntimeException("数字[" + number + "]格式不正确!");
        }
        if (number.indexOf("-") != number.lastIndexOf("-") || number.lastIndexOf("-") > 0) {
            throw new RuntimeException("数字[" + number + "]格式不正确！");
        }
        if (number.indexOf("+") != number.lastIndexOf("+")) {
            throw new RuntimeException("数字[" + number + "]格式不正确！");
        }
        if (number.replaceAll("[\\d|\\.|\\-|\\+]", "").length() > 0) {
            throw new RuntimeException("数字[" + number + "]格式不正确！");
        }
    }


    /**
     * 分割数字，每4位一组
     *
     * @param number 数字
     * @return
     */
    private static Integer[] split2IntArray(String number) {
        String prev = number.substring(0, number.length() % 4);
        String stuff = number.substring(number.length() % 4);
        if (!"".equals(prev)) {
            number = String.format("%04d", Integer.valueOf(prev)) + stuff;
        }
        Integer[] ints = new Integer[number.length() / 4];
        int idx = 0;
        for (int i = 0; i < number.length(); i += 4) {
            String n = number.substring(i, i + 4);
            ints[idx++] = Integer.valueOf(n);
        }
        return ints;
    }


    /**
     * 格式化4位整数
     *
     * @param number 传参数字
     * @param numArray 数字大小写数组
     * @param unit 单位权值
     * @return 返回结果
     */
    private static String formatInt(int number, String[] numArray, String[] unit) {
        char[] val = String.valueOf(number).toCharArray();
        int len = val.length;
        StringBuilder sb = new StringBuilder();
        boolean isZero = false;
        for (int i = 0; i < len; i++) {
            //获取当前位的数值
            int n = Integer.parseInt(val[i] + "");
            if (n == 0) {
                isZero = true;
            } else {
                if (isZero) {
                    sb.append(numArray[Integer.parseInt(val[i - 1] + "")]);
                }
                sb.append(numArray[n]);
                sb.append(unit[(len - 1) - i]);
                isZero = false;
            }
        }
        return sb.toString();
    }

    /**
     * 获取UUID,去除 -
     * @param prefix 前缀
     * @return 返回结果
     */
    public static String UUID(String prefix){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        if(!Objects.isNull(prefix)){
            uuid = prefix+uuid;
        }
        return uuid;
    }

    /**
     * 获取UUID
     * @return 返回结果
     */
    public static String UUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
