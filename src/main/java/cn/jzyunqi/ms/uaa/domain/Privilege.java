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
@Table(name = "uaa_privilege")
public class Privilege extends BaseDomain<Long, Long> {
    private static final long serialVersionUID = 6244860870586273500L;

    /**
     * 权限code
     */
    private String code;

    /**
     * 权限名称
     */
    private String name;

    @Column
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
