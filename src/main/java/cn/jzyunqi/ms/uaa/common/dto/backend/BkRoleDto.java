package cn.jzyunqi.ms.uaa.common.dto.backend;

import cn.jzyunqi.ms.uaa.common.dto.RoleDto;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public class BkRoleDto extends RoleDto {
    private static final long serialVersionUID = 5784188111771357350L;

    /**
     * 权限名称，逗号分隔
     */
    private String privilegeNames;

    /**
     * 角色有用的权限id
     */
    private List<Long> privilegeIdList;

    public String getPrivilegeNames() {
        return privilegeNames;
    }

    public void setPrivilegeNames(String privilegeNames) {
        this.privilegeNames = privilegeNames;
    }

    public List<Long> getPrivilegeIdList() {
        return privilegeIdList;
    }

    public void setPrivilegeIdList(List<Long> privilegeIdList) {
        this.privilegeIdList = privilegeIdList;
    }
}
