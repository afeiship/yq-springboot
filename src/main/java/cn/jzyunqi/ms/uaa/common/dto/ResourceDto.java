package cn.jzyunqi.ms.uaa.common.dto;

import cn.jzyunqi.common.model.BaseDto;
import cn.jzyunqi.ms.uaa.common.enums.PermitType;
import cn.jzyunqi.ms.uaa.common.enums.ResourceType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Getter
@Setter
public class ResourceDto extends BaseDto<Long, Long> {

    private static final long serialVersionUID = -733690850342760299L;

    /**
     * 资源路径
     */
    private String url;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源类型
     */
    private ResourceType type;
    /**
     * 是否根节点资源，true是，false不是，null不属于目录资源
     */
    private Boolean root;
    /**
     * 是否叶子节点资源，true是，false不是，null不属于目录资源
     */
    private Boolean leaf;
    /**
     * 资源排序
     */
    private Integer priority;
    /**
     * 父资源id
     */
    private Long parentId;
    /**
     * 资源授权类型
     */
    private PermitType permitType;

    /**
     * 微服务id
     */
    private String msClientId;
}
