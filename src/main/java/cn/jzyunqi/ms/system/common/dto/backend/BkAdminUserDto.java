package cn.jzyunqi.ms.system.common.dto.backend;

import cn.jzyunqi.ms.system.common.dto.AdminUserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Getter
@Setter
public class BkAdminUserDto extends AdminUserDto {
    private static final long serialVersionUID = -4988471306674791090L;

    /**
     * uid
     */
    private String uid;

    /**
     * token
     */
    private String token;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否启用，true启用，false禁用
     */
    private Boolean enabled;

    /**
     * 用户拥有的角色名称，逗号分隔
     */
    private String roleNames;

    /**
     * 角色ID列表
     */
    private List<Long> roleIdList;

    /**
     * 权限code列表
     */
    private List<String> privilegeCodeList;
}
