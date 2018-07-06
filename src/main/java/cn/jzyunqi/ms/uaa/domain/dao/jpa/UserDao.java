package cn.jzyunqi.ms.uaa.domain.dao.jpa;

import cn.jzyunqi.common.persistence.dao.BaseDao;
import cn.jzyunqi.ms.uaa.domain.User;
import org.springframework.data.jpa.repository.Query;


/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public interface UserDao extends BaseDao<User, Long> {

    /**
     * 查询有效的用户
     *
     * @param userId 会员id
     * @return 会员信息
     */
    @Query("select u from User u where u.id = ?1")
    User findValidById(Long userId);

    /**
     * 根据uid查询用户
     *
     * @param uid uid
     * @return 会员信息
     */
    @Query("select u from User u where u.uid = ?1")
    User findValidByUid(String uid);
}
