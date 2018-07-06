package cn.jzyunqi.ms.job.common.enums;

/**
 * <p>类简述：年间隔类型</p>
 * <p>
 * <p>描述：</p>
 * <p>
 * <p>补充：</p>
 *
 * @author wiiyaya
 */
public enum YearType {
    /**
     * 每年
     */
    A("每年"),

    /**
     * 每隔X年
     */
    E("每隔X年"),

    /**
     * 指定XXXX年
     */
    I("指定XXXX年"),;

    private String desc;

    YearType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
