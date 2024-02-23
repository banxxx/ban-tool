package cn.banxx.enums;

/**
 * 时间枚举类.
 *
 */
public enum DateEnums {

    /**
     * 星期 从周日到周一
     */
    Sunday("周日","Sunday"),
    Monday("周一","Monday"),
    Tuesday("周二","Tuesday"),
    Wednesday("周三","Wednesday"),
    Thursday("周四","Thursday"),
    Friday("周五","Friday"),
    Saturday("周六","Saturday");

    /**
     * The code.
     */
    String code;

    /**
     * The Name.
     */
    String name;

    DateEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
