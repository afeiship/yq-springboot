package cn.jzyunqi.ms.uaa.domain.dao.jpa;

import cn.jzyunqi.common.persistence.dao.BaseDao;
import cn.jzyunqi.ms.uaa.domain.RolePrivilege;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public interface RolePrivilegeDao extends BaseDao<RolePrivilege, Long> {

    /**
     * 查找角色对应的权限id
     *
     * @param roleIds 角色id
     * @return 权限id
     */
    @Query("select rp.privilegeId from RolePrivilege rp where rp.roleId in (?1)")
    List<Long> findPrivilegeIdList(Iterable<Long> roleIds);

    /**
     * 查找角色对应的权限id
     *
     * @param roleId 角色id
     * @return 权限id
     */
    @Query("select rp.privilegeId from RolePrivilege rp where rp.roleId = ?1")
    List<Long> findPrivilegeIdList(Long roleId);

    /**
     * 查找角色对应的角色权限
     *
     * @param roleId 角色id
     * @return 角色权限
     */
    @Query("select rp from RolePrivilege rp where rp.roleId = ?1")
    List<RolePrivilege> findByRoleId(Long roleId);

}
