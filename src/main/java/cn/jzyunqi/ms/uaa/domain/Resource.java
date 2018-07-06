package cn.jzyunqi.ms.uaa.domain;

import cn.jzyunqi.common.persistence.domain.BaseDomain;
import cn.jzyunqi.ms.uaa.common.enums.PermitType;
import cn.jzyunqi.ms.uaa.common.enums.ResourceType;

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
@Table(name = "uaa_resource")
public class Resource extends BaseDomain<Long, Long> {
    private static final long serialVersionUID = 8903208959911474142L;

    /**
     * 名称
     */
    private String name;

    /**
     * 菜单的url
     */
    private String url;

    /**
     * 资源类型
     */
    private ResourceType type;

    /**
     * 是否是根菜单
     */
    private Boolean root;

    /**
     * 是不是叶子菜单
     */
    private Boolean leaf;

    /**
     * 次序
     */
    private Integer priority;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 可访问类型
     */
    private PermitType permitType;

    /**
     * 微服务id
     */
    private String msClientId;

    @Column
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    @Column
    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    @Column
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Column
    public Boolean getRoot() {
        return root;
    }

    public void setRoot(Boolean root) {
        this.root = root;
    }

    @Column
    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    @Column
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Enumerated(EnumType.STRING)
    @Column
    public PermitType getPermitType() {
        return permitType;
    }

    public void setPermitType(PermitType permitType) {
        this.permitType = permitType;
    }

    @Column
    public String getMsClientId() {
        return msClientId;
    }

    public void setMsClientId(String msClientId) {
        this.msClientId = msClientId;
    }
}
