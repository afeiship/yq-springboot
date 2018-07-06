package cn.jzyunqi.ms.uaa.service;

import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;

/**
 * @author wiiyaya
 * @date 2018/1/22.
 */
public interface OAuth2ClientService extends ClientDetailsService, ClientRegistrationService {
}
