package org.yuequan.watchdog.endpoint.support;

import java.util.Set;

/**
 * @author yuequan
 * @since
 **/


public class ApplicationParam {
    private String name;
    private Set<String> scope;

    public final static String DEFAULT_REDIRECT_URL = "urn:ietf:wg:oauth:2.0:oob";

    @org.codehaus.jackson.annotate.JsonProperty("redirect_uri")
    @com.fasterxml.jackson.annotation.JsonProperty("redirect_uri")
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


    public void populateDefault(){
        if(this.getRedirectUri().size() == 0){
            getRedirectUri().add(DEFAULT_REDIRECT_URL);
        }

        if(getScope().size() == 0){
            getScope().add("DEFAULT");
        }
    }
}
