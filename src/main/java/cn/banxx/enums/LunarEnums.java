package cn.banxx.enums;

/**
 * 农历时间转换枚举类.
 *
 * @author : Ban
 * @version : 1.0
 * @createTime: 2023-12-08  14:38
 * @since : 1.0
 */
public enum LunarEnums {

    /**
     * 年份时间
     * @author: Ban
     * @date: 2023/12/8 14:42
     * @param:
     * @return:
     */
    // 1874
    First(1874,"1874"),
    Second(1909,"1908"),
    Third(1908,"1908"),
    Fourth(1911,"1911"),
    Fifth(1912,"1912"),
    Sixth(1949,"1949"),
    Seventh(1950,"1950");

    /**
     * The code.
     */
    int code;
    /**
     * The Name.
     */
    String name;

    LunarEnums(int code,String name) {
        this.code = code;
        this.name = name;
    }


    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
}
