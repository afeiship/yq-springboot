package cn.jzyunqi.ms.uaa.domain.dao.jpa;

import cn.jzyunqi.common.persistence.dao.BaseDao;
import cn.jzyunqi.ms.uaa.domain.UserAuth;
import cn.jzyunqi.ms.uaa.common.enums.AuthType;
import org.springframework.data.jpa.repository.Query;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
public interface UserAuthDao extends BaseDao<UserAuth, Long> {

    /**
     * 查找指定身份授权信息
     *
     * @param username 用户名
     * @return 信息
     */
    @Query("select ua from UserAuth ua where type = ?1 and ua.username = ?2")
    UserAuth findByAuthTypeAndUsername(AuthType authType, String username);

    /**
     * 查找指定身份授权信息
     *
     * @param authType 授权类型
     * @param userId   用户id
     * @return 信息
     */
    @Query("select ua from UserAuth ua where ua.type = ?1 and ua.userId = ?2")
    UserAuth findByAuthTypeAndUserId(AuthType authType, Long userId);

}
