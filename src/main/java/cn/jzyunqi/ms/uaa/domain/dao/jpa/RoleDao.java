package cn.jzyunqi.ms.uaa.domain.dao.jpa;


import cn.jzyunqi.common.persistence.dao.BaseDao;
import cn.jzyunqi.ms.uaa.domain.Role;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public interface RoleDao extends BaseDao<Role, Long> {

    /**
     * 查找用户拥有的角色名称
     *
     * @param userId 用户id
     * @return 角色名称
     */
    @Query("select r.name from Role r join UserRole ur on r.id = ur.roleId where ur.userId = ?1")
    List<String> findNamesByUserId(Long userId);
}
