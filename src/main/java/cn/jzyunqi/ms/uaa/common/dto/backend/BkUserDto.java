package cn.jzyunqi.ms.uaa.common.dto.backend;

import cn.jzyunqi.ms.uaa.common.dto.UserDto;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public class BkUserDto extends UserDto {
    private static final long serialVersionUID = -4988471306674791090L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户拥有的角色名称，逗号分隔
     */
    private String roleNames;

    /**
     * 角色ID列表
     */
    private List<Long> roleIdList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }
}
