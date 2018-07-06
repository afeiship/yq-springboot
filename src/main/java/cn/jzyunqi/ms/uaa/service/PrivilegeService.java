package cn.jzyunqi.ms.uaa.service;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.ms.uaa.common.dto.PrivilegeDto;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public interface PrivilegeService {

    /**
     * 获取所有的权限列表
     *
     * @return 权限列表
     */
    List<PrivilegeDto> getAllPrivilegeList();

    /**
     * 获取资源的权限列表
     *
     * @param resourceId 资源id
     * @return 资源拥有的权限列表
     */
    List<PrivilegeDto> getResourcePrivilege(Long resourceId);

    /**
     * 获取资源不包含的权限列表
     *
     * @param resourceId 资源id
     * @return 资源不包含的权限列表
     */
    List<PrivilegeDto> getOtherPrivilege(Long resourceId);

    /**
     * 获取指定权限id对应的权限信息
     *
     * @param privilegeIdList 资源id列表
     * @return 权限列表
     */
    List<PrivilegeDto> getPrivilegesByIdList(List<Long> privilegeIdList);

    /**
     * 新增一个权限
     *
     * @param privilegeDto 权限信息
     * @return 权限信息，含id
     * @throws cn.jzyunqi.common.exception.BusinessException
     */
    PrivilegeDto savePrivilege(PrivilegeDto privilegeDto) throws BusinessException;

    /**
     * 获取角色所有权限id
     *
     * @param roleId 角色id
     * @return 权限id
     */
    List<Long> getRolePrivilegeIds(Long roleId);

    /**
     * 获取角色所有权限id
     *
     * @param roleIdList 角色id列表
     * @return 权限id
     */
    List<Long> getRolePrivilegeIdsByRoleIdList(List<Long> roleIdList);

}
