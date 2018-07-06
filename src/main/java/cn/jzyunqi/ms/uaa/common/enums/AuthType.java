package cn.jzyunqi.ms.uaa.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Getter
@AllArgsConstructor
public enum AuthType {

    /**
     * 微信 - APP（开放平台）
     */
    WX_OPEN("微信APP"),

    /**
     * 微信 - MP（小程序）
     */
    WX_MP("微信小程序"),

    /**
     * 电话号码
     */
    PHONE("手机号"),

    /**
     * 普通输入
     */
    NORMAL("用户名");

    private String desc;
}
