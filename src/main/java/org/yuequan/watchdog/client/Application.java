package org.yuequan.watchdog.client;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import java.util.Set;
import java.util.UUID;

/**
 * 应用实体，扩展{@link BaseClientDetails}，在此基础上添加了name字段
 * @author yuequan
 * @since 1.0
 **/
public class Application extends BaseClientDetails implements ClientDetails {

    private String name;
    @JsonProperty("raw_client_secret")
    @com.fasterxml.jackson.annotation.JsonProperty("raw_client_secret")
    private String rawClientSecret;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRawClientSecret() {
        return rawClientSecret;
    }

    public void setRawClientSecret(String rawClientSecret) {
        this.rawClientSecret = rawClientSecret;
    }

    public Application() {
        super();
    }

    public Application(ClientDetails prototype) {
        super(prototype);
    }

    public Application(String clientId, String resourceIds, String scopes, String grantTypes, String authorities) {
        super(clientId, resourceIds, scopes, grantTypes, authorities);
    }

    public Application(String clientId, String resourceIds, String scopes, String grantTypes, String authorities, String redirectUris) {
        super(clientId, resourceIds, scopes, grantTypes, authorities, redirectUris);
    }

    public Application(String clientId, String resourceIds, Set<String> scopes, Set<String> redirectUris, String name) {
        super(clientId, resourceIds, "", "password,refresh_token,client_credentials", "CLIENT", "");
        this.name = name;
        setScope(scopes);
        setRegisteredRedirectUri(redirectUris);
        setClientSecret(UUID.randomUUID().toString());
        setRawClientSecret(getClientSecret());
    }
}
