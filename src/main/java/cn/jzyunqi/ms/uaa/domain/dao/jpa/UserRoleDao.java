package cn.jzyunqi.ms.uaa.domain.dao.jpa;

import cn.jzyunqi.common.persistence.dao.BaseDao;
import cn.jzyunqi.ms.uaa.domain.UserRole;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public interface UserRoleDao extends BaseDao<UserRole, Long> {

    /**
     * 查找用户拥有的角色
     *
     * @param userId 用户id
     * @return 用户角色
     */
    @Query("select ur from UserRole ur where ur.userId = ?1")
    List<UserRole> findByUserId(Long userId);

    /**
     * 查找用户拥有的角色id
     *
     * @param userId 用户id
     * @return 用户角色id列表
     */
    @Query("select ur.roleId from UserRole ur where ur.userId = ?1")
    List<Long> findRoleIdListByUserId(Long userId);
}
