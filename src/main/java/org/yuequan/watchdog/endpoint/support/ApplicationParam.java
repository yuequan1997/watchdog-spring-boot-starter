package org.yuequan.watchdog.endpoint.support;

import java.util.Set;

/**
 * @author yuequan
 * @since
 **/


public class ApplicationParam {
    private String name;
    private Set<String> scope;
    private Set<String> redirectUri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(Set<String> redirectUri) {
        this.redirectUri = redirectUri;
    }

    public Set<String> getScope() {
        return scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

}
