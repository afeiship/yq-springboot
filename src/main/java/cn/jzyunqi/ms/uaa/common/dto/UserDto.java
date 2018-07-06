package cn.jzyunqi.ms.uaa.common.dto;

import cn.jzyunqi.common.model.BaseDto;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public class UserDto extends BaseDto<Long, Long> {

    private static final long serialVersionUID = -6554401070177136005L;

    /**
     * 免登陆信息查看id
     */
    private String uid;

    /**
     * 是否启用
     */
    private Boolean enabled;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
