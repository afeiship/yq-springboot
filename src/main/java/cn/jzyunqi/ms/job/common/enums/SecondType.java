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
public enum SecondType {
    /**
     * 每秒
     */
    A("每秒"),

    /**
     * 指定秒
     */
    T("指定秒"),;

    private String desc;

    SecondType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
