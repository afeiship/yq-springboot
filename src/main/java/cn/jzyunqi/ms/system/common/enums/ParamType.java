package cn.jzyunqi.ms.system.common.enums;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public enum ParamType {

    /**
     * 拍品庆祝时间
     */
    AUC_PD("拍卖-拍品庆祝时间"),

    /**
     * 提前入场时间
     */
    AUC_PED("拍卖-提前入场时间"),

    /**
     * 开始倒数时长
     */
    AUC_SCDD("拍卖-开始倒数时长"),

    /**
     * 店铺保证金分成比例
     */
    MC_SBBP("会员-保证金分成比例"),

    /**
     * IOS版本号
     */
    APP_V("IOS版本号"),

    /**
     * ANDROID版本号
     */
    ANDROID_V("ANDROID版本号"),;

    private String desc;

    ParamType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

}
