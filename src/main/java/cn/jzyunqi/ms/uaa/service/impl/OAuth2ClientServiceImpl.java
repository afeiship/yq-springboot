package cn.jzyunqi.ms.uaa.service.impl;

import cn.jzyunqi.ms.uaa.domain.dao.jpa.OAuth2ClientDao;
import cn.jzyunqi.ms.uaa.domain.OAuth2Client;
import cn.jzyunqi.ms.uaa.service.OAuth2ClientService;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/1/21.
 */
@Service("oauth2ClientService")
public class OAuth2ClientServiceImpl implements OAuth2ClientService {

    @Resource
    private OAuth2ClientDao oauth2ClientDao;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OAuth2Client oauth2Client = oauth2ClientDao.findByClientId(clientId);
        BaseClientDetails clientDetails = new BaseClientDetails(
                oauth2Client.getClientId()
                , oauth2Client.getResourceIds()
                , oauth2Client.getScope()
                , oauth2Client.getAuthorizedGrantTypes()
                , oauth2Client.getAuthorities()
                , oauth2Client.getWebServerRedirectUri()
        );

        clientDetails.setClientSecret(oauth2Client.getClientSecret());
        clientDetails.setAccessTokenValiditySeconds(oauth2Client.getAccessTokenValidity());
        clientDetails.setRefreshTokenValiditySeconds(oauth2Client.getRefreshTokenValidity());
//        clientDetails.setAdditionalInformation(oauth2Client.getAdditionalInformation());
        clientDetails.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(oauth2Client.getAutoApprove()));
        return clientDetails;
    }

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {

    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {

    }

    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {

    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {

    }

    @Override
    public List<ClientDetails> listClientDetails() {
        return null;
    }
}
