package org.yuequan.watchdog.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.yuequan.watchdog.client.Application;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yuequan
 * @since
 **/
public final class FieldUtil {

    private static final JsonMapper mapper = createJsonMapper();

    private static final Log logger = LogFactory.getLog(FieldUtil.class);

    public static <T extends ClientDetails> Object[] getFields(T clientDetails, PasswordEncoder passwordEncoder) {
        Object[] fieldsForUpdate = getFieldsForUpdate(clientDetails);
        Object[] fields = new Object[fieldsForUpdate.length + 1];
        System.arraycopy(fieldsForUpdate, 0, fields, 1, fieldsForUpdate.length);
        fields[0] = clientDetails.getClientSecret() != null ? passwordEncoder.encode(clientDetails.getClientSecret())
                : null;
//        if(clientDetails instanceof Application){
//            fields[fields.length - 1] = fields[fields.length - 2];
//            fields[fields.length - 2] = ((Application) clientDetails).getName();
//        }
        return fields;
    }

    public static <T extends ClientDetails> Object[] getFieldsForUpdate(T clientDetails) {
        String json = null;
        try {
            json = mapper.write(clientDetails.getAdditionalInformation());
        }
        catch (Exception e) {
            logger.warn("Could not serialize additional information: " + clientDetails, e);
        }


        if(clientDetails instanceof Application){
            return new Object[] {
                    clientDetails.getResourceIds() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails
                            .getResourceIds()) : null,
                    clientDetails.getScope() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails
                            .getScope()) : null,
                    clientDetails.getAuthorizedGrantTypes() != null ? StringUtils
                            .collectionToCommaDelimitedString(clientDetails.getAuthorizedGrantTypes()) : null,
                    clientDetails.getRegisteredRedirectUri() != null ? StringUtils
                            .collectionToCommaDelimitedString(clientDetails.getRegisteredRedirectUri()) : null,
                    clientDetails.getAuthorities() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails
                            .getAuthorities()) : null, clientDetails.getAccessTokenValiditySeconds(),
                    clientDetails.getRefreshTokenValiditySeconds(), json, getAutoApproveScopes(clientDetails),
                    ((Application) clientDetails).getName(),
                    clientDetails.getClientId() };
        }
        return new Object[] {
                clientDetails.getResourceIds() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails
                        .getResourceIds()) : null,
                clientDetails.getScope() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails
                        .getScope()) : null,
                clientDetails.getAuthorizedGrantTypes() != null ? StringUtils
                        .collectionToCommaDelimitedString(clientDetails.getAuthorizedGrantTypes()) : null,
                clientDetails.getRegisteredRedirectUri() != null ? StringUtils
                        .collectionToCommaDelimitedString(clientDetails.getRegisteredRedirectUri()) : null,
                clientDetails.getAuthorities() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails
                        .getAuthorities()) : null, clientDetails.getAccessTokenValiditySeconds(),
                clientDetails.getRefreshTokenValiditySeconds(), json, getAutoApproveScopes(clientDetails),
                clientDetails.getClientId() };
    }

    public static String getAutoApproveScopes(ClientDetails clientDetails) {
        if (clientDetails.isAutoApprove("true")) {
            return "true"; // all scopes autoapproved
        }
        Set<String> scopes = new HashSet<String>();
        for (String scope : clientDetails.getScope()) {
            if (clientDetails.isAutoApprove(scope)) {
                scopes.add(scope);
            }
        }
        return StringUtils.collectionToCommaDelimitedString(scopes);
    }

    public interface JsonMapper {
        String write(Object input) throws Exception;

        <T> T read(String input, Class<T> type) throws Exception;
    }

    public static JsonMapper createJsonMapper() {
        if (ClassUtils.isPresent("org.codehaus.jackson.map.ObjectMapper", null)) {
            return new JacksonMapper();
        }
        else if (ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", null)) {
            return new Jackson2Mapper();
        }
        return new NotSupportedJsonMapper();
    }

    public static class JacksonMapper implements JsonMapper {
        private org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();

        @Override
        public String write(Object input) throws Exception {
            return mapper.writeValueAsString(input);
        }

        @Override
        public <T> T read(String input, Class<T> type) throws Exception {
            return mapper.readValue(input, type);
        }
    }

    public static class Jackson2Mapper implements JsonMapper {
        private com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

        @Override
        public String write(Object input) throws Exception {
            return mapper.writeValueAsString(input);
        }

        @Override
        public <T> T read(String input, Class<T> type) throws Exception {
            return mapper.readValue(input, type);
        }
    }

    public static class NotSupportedJsonMapper implements JsonMapper {
        @Override
        public String write(Object input) throws Exception {
            throw new UnsupportedOperationException(
                    "Neither Jackson 1 nor 2 is available so JSON conversion cannot be done");
        }

        @Override
        public <T> T read(String input, Class<T> type) throws Exception {
            throw new UnsupportedOperationException(
                    "Neither Jackson 1 nor 2 is available so JSON conversion cannot be done");
        }
    }
}
