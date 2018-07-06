package cn.jzyunqi.ms.job.common.enums;

/**
 * <p>类简述：</p>
 * <p>
 * <p>描述：</p>
 * <p>
 * <p>补充：</p>
 *
 * @author wiiyaya
 */
public enum HourType {
    /**
     * 每小时
     */
    A("每小时"),

    /**
     * 指定时间
     */
    T("指定时间"),;

    private String desc;

    HourType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
