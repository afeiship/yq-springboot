package cn.jzyunqi.ms.job.common.enums;

/**
 * <p>类简述：月间隔类型</p>
 * <p>
 * <p>描述：</p>
 * <p>
 * <p>补充：</p>
 *
 * @author wiiyaya
 */
public enum MonthType {
    /**
     * 每月
     */
    A("每月"),

    /**
     * 指定月份
     */
    I("指定月份"),;

    private String desc;

    MonthType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
