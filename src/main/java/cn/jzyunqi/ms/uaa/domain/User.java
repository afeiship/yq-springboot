package cn.jzyunqi.ms.uaa.domain;

import cn.jzyunqi.common.persistence.domain.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Entity
@Table(name = "uaa_user")
public class User extends BaseDomain<Long, Long> {

    private static final long serialVersionUID = -3334107189175652114L;

    /**
     * 用户uid
     */
    private String uid;

    /**
     * 是否启用
     */
    private Boolean enabled;

    @Column
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Column
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
