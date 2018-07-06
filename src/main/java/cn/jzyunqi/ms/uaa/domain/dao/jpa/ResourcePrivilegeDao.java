package cn.jzyunqi.ms.uaa.domain.dao.jpa;

import cn.jzyunqi.common.persistence.dao.BaseDao;
import cn.jzyunqi.ms.uaa.domain.ResourcePrivilege;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public interface ResourcePrivilegeDao extends BaseDao<ResourcePrivilege, Long> {

    /**
     * 查找资源权限id
     *
     * @param resourceId 资源id
     * @return 权限id
     */
    @Query("select rp.privilegeId from ResourcePrivilege rp where rp.resourceId = ?1")
    List<Long> findPrivilegeIds(Long resourceId);

    /**
     * 查找权限资源id
     *
     * @param privilegeIds 权限id
     * @return 资源id
     */
    @Query("select distinct rp.resourceId from ResourcePrivilege rp where rp.privilegeId in (?1)")
    List<Long> findResourceIds(Iterable<Long> privilegeIds);

    /**
     * 查找资源权限id
     *
     * @param resourceIds 资源id
     * @return 权限id
     */
    @Query("select distinct rp.privilegeId from ResourcePrivilege rp where rp.resourceId in (?1)")
    List<Long> findPrivilegeIds(Iterable<Long> resourceIds);

    /**
     * 查找资源权限
     *
     * @param resourceId 资源id
     * @return 资源权限
     */
    @Query("select rp from ResourcePrivilege rp where rp.resourceId = ?1")
    List<ResourcePrivilege> findByResourceId(Long resourceId);
}
