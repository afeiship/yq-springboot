package cn.jzyunqi.ms.system.domain;

import cn.jzyunqi.common.persistence.domain.BaseDomain;
import cn.jzyunqi.ms.system.common.enums.ParamType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * 系统参数
 *
 * @author wiiyaya
 * @date 2018/5/3
 */
@Entity
@Table(name = "sys_param")
public class Param extends BaseDomain<Long, Long> {

    private static final long serialVersionUID = -1171572711575517483L;

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

    @Enumerated(EnumType.STRING)
    @Column
    public ParamType getType() {
        return type;
    }

    public void setType(ParamType type) {
        this.type = type;
    }

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

    @Column
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "extend_01")
    public String getExtend01() {
        return extend01;
    }

    public void setExtend01(String extend01) {
        this.extend01 = extend01;
    }

    @Column(name = "extend_02")
    public String getExtend02() {
        return extend02;
    }

    public void setExtend02(String extend02) {
        this.extend02 = extend02;
    }

    @Column
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Column
    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Column
    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}
