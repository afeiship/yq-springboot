package cn.jzyunqi.ms.uaa.common.dto.backend;

import cn.jzyunqi.ms.uaa.common.dto.ResourceDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Getter
@Setter
public class BkResourceDto extends ResourceDto {
    private static final long serialVersionUID = -2855503685377036887L;

    /**
     * 父节点
     */
    private BkResourceDto parent;

    /**
     * 权限名称，逗号分隔
     */
    private String privilegeNames;
}
