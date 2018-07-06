package cn.jzyunqi.ms.uaa.domain;

import cn.jzyunqi.common.persistence.domain.BaseDomain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wiiyaya
 * @date 2018/1/21.
 */
@Entity
@Table(name = "uaa_oauth2_client")
public class OAuth2Client extends BaseDomain<Long, Long> {
    private static final long serialVersionUID = -8474604468831714454L;

    private String clientId;
    private String clientSecret;
    private String resourceIds;//逗号分隔
    private String scope;//逗号分隔
    private String authorizedGrantTypes;//逗号分隔
    private String webServerRedirectUri;//逗号分隔
    private String authorities;//逗号分隔
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;
    private String additionalInformation;//JSON格式存储
    private String autoApprove;//逗号分隔autoApproveScope

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getWebServerRedirectUri() {
        return webServerRedirectUri;
    }

    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(String autoApprove) {
        this.autoApprove = autoApprove;
    }
}
