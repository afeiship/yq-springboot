package cn.jzyunqi.ms.system.common.dto;

import cn.jzyunqi.common.model.BaseDto;
import cn.jzyunqi.ms.system.common.enums.ParamType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Getter
@Setter
public class ParamDto extends BaseDto<Long, Long> {
    private static final long serialVersionUID = 4271242249562354615L;

    /**
     * 类型
     */
    private ParamType type;

    /**
     * 代码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 备注
     */
    private String remark;

    /**
     * 扩展1
     */
    private String extend01;

    /**
     * 扩展2
     */
    private String extend02;

    /**
     * 顺序
     */
    private Integer priority;

    /**
     * 父key
     */
    private String parentCode;

    /**
     * 是否默认值
     */
    private Boolean isDefault;
}
