package cn.jzyunqi.ms.uaa.domain.dao.jpa;

import cn.jzyunqi.common.persistence.dao.BaseDao;
import cn.jzyunqi.ms.uaa.domain.Privilege;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public interface PrivilegeDao extends BaseDao<Privilege, Long> {

    /**
     * 查找角色拥有的权限名称
     *
     * @param roleId 角色id
     * @return 权限名称
     */
    @Query("select p.name from Privilege p join RolePrivilege rp on p.id = rp.privilegeId where rp.roleId = ?1")
    List<String> findNamesByRoleId(Long roleId);

    /**
     * 查找资源拥有的权限名称
     *
     * @param resourceId 资源id
     * @return 权限名称
     */
    @Query("select p.name from Privilege p join ResourcePrivilege rp on p.id = rp.privilegeId where rp.resourceId = ?1")
    List<String> findNamesByResourceId(Long resourceId);

    /**
     * 获取权限代码
     *
     * @param privilegeIds 权限id
     * @return 权限代码
     */
    @Query("select p.code from Privilege p where p.id in(?1)")
    List<String> findCodes(Iterable<Long> privilegeIds);

    /**
     * 查找除了指定id以外的权限
     *
     * @param privilegeIds 权限id
     * @return 权限
     */
    @Query("select p from Privilege p where p.id not in(?1)")
    List<Privilege> findAllNotIn(Iterable<Long> privilegeIds);

    /**
     * 根据权限code查询权限数量
     *
     * @param code 权限code
     * @return 数量
     */
    @Query("select count(p.id) from Privilege p where p.code = ?1")
    Long countCode(String code);

}
