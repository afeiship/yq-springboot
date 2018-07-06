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
public enum MinuteType {
    /**
     * 每分钟
     */
    A("每分钟"),

    /**
     * 指定时间
     */
    T("指定时间"),;

    private String desc;

    MinuteType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
