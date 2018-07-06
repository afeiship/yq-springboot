package cn.jzyunqi.ms.uaa.domain.dao.jpa;

import cn.jzyunqi.common.persistence.dao.BaseDao;
import cn.jzyunqi.ms.uaa.domain.OAuth2Client;
import org.springframework.data.jpa.repository.Query;

/**
 * @author wiiyaya
 * @date 2018/1/27.
 */
public interface OAuth2ClientDao extends BaseDao<OAuth2Client, Long> {

    /**
     * 根据微服务id查询
     *
     * @param clientId 微服务id
     * @return 微服务信息
     */
    @Query("select oc from OAuth2Client oc where oc.clientId = ?1")
    OAuth2Client findByClientId(String clientId);
}
