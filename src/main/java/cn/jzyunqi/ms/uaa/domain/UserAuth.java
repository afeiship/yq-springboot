package cn.jzyunqi.ms.uaa.domain;

import cn.jzyunqi.common.persistence.domain.BaseDomain;
import cn.jzyunqi.ms.uaa.common.enums.AuthType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Entity
@Table(name = "uaa_user_auth")
public class UserAuth extends BaseDomain<Long, Long> {
    private static final long serialVersionUID = 6515057974860078895L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 授权类型
     */
    private AuthType type;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否已验证
     */
    private Boolean verified;

    @Column
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column
    @Enumerated(value = EnumType.STRING)
    public AuthType getType() {
        return type;
    }

    public void setType(AuthType type) {
        this.type = type;
    }

    @Column
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column
    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}
