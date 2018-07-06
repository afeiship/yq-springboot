package cn.jzyunqi.ms.uaa.common.dto;

import cn.jzyunqi.common.model.BaseDto;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public class PrivilegeDto extends BaseDto<Long, Long> {

    private static final long serialVersionUID = -8102009358441795250L;

    /**
     * 权限编码
     */
    private String code;
    /**
     * 权限名称
     */
    private String name;

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
