package org.yuequan.watchdog.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.util.DefaultJdbcListFactory;
import org.springframework.security.oauth2.common.util.JdbcListFactory;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.yuequan.watchdog.util.FieldUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.yuequan.watchdog.util.FieldUtil.getFieldsForUpdate;

/**
 * {@link ApplicationRepository} JDBC默认实现
 * @author yuequan
 * @since 1.0
 **/
public class DefaultApplicationRepositoryImpl implements ApplicationRepository<Application> {

    private final DataSource dataSource;

    private static final Log logger = LogFactory.getLog(DefaultApplicationRepositoryImpl.class);

    private static final String CLIENT_FIELDS_FOR_UPDATE = "resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove, name, raw_client_secret";

    private static final String CLIENT_FIELDS = "client_secret, " + CLIENT_FIELDS_FOR_UPDATE;

    private static final String BASE_FIND_STATEMENT = "select client_id, " + CLIENT_FIELDS
            + " from oauth_client_details";

    private static final String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

    private static final String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

    private static final String DEFAULT_INSERT_STATEMENT = "insert into oauth_client_details (" + CLIENT_FIELDS
            + ", client_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String DEFAULT_UPDATE_STATEMENT = "update oauth_client_details " + "set "
            + CLIENT_FIELDS_FOR_UPDATE.replaceAll(", ", "=?, ") + "=? where client_id = ?";

    private static final String DEFAULT_UPDATE_SECRET_STATEMENT = "update oauth_client_details "
            + "set client_secret = ? where client_id = ?";

    private static final String DEFAULT_DELETE_STATEMENT = "delete from oauth_client_details where client_id = ?";

    private String deleteApplicationSql = DEFAULT_DELETE_STATEMENT;

    private String findApplicationSql = DEFAULT_FIND_STATEMENT;

    private String updateApplicationSql = DEFAULT_UPDATE_STATEMENT;

    private String updateApplicationSecretSql = DEFAULT_UPDATE_SECRET_STATEMENT;

    private String insertApplicationSql = DEFAULT_INSERT_STATEMENT;

    private String selectApplicationSql = DEFAULT_SELECT_STATEMENT;


    private RowMapper<Application> rowMapper = new ApplicationRowMapper();

    private final JdbcTemplate jdbcTemplate;
    private final JdbcListFactory listFactory;
    private final PasswordEncoder passwordEncoder;

    public DefaultApplicationRepositoryImpl(DataSource dataSource, PasswordEncoder passwordEncoder) {
        Assert.notNull(dataSource, "DataSource required");
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.listFactory = new DefaultJdbcListFactory(new NamedParameterJdbcTemplate(jdbcTemplate));
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Application save(ClientDetails application) {
        jdbcTemplate.update(insertApplicationSql, FieldUtil.getFields(application, passwordEncoder));
        return findByClientId(application.getClientId()).get();
    }

    @Override
    public Optional<Application> findByClientId(String clientId) {
        Application application;
        try {
            application = jdbcTemplate.queryForObject(selectApplicationSql, new ApplicationRowMapper(), clientId);
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(application);
    }

    @Override
    public Application update(ClientDetails application) {
        int count = jdbcTemplate.update(updateApplicationSql, getFieldsForUpdate(application));
        if (count != 1) {
            throw new NoSuchClientException("No client found with id = " + application.getClientId());
        }
        return findByClientId(application.getClientId()).get();
    }

    @Override
    public List<Application> findAll() {
        return listFactory.getList(findApplicationSql, Collections.emptyMap(), rowMapper);
    }

    @Override
    public List<Application> findAll(int pageNo, int pageSize) {
        return null;
    }

    @Override
    public void delete(String clientId) {
        int count = jdbcTemplate.update(deleteApplicationSql, clientId);
        if (count != 1) {
            throw new NoSuchClientException("No client found with id = " + clientId);
        }
    }

    private static class ApplicationRowMapper implements RowMapper<Application> {
        private FieldUtil.JsonMapper mapper = FieldUtil.createJsonMapper();

        @Override
        public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
            Application details = new Application(rs.getString(1), rs.getString(3), rs.getString(4),
                    rs.getString(5), rs.getString(7), rs.getString(6));
            details.setClientSecret(rs.getString(2));
            if (rs.getObject(8) != null) {
                details.setAccessTokenValiditySeconds(rs.getInt(8));
            }
            if (rs.getObject(9) != null) {
                details.setRefreshTokenValiditySeconds(rs.getInt(9));
            }
            String json = rs.getString(10);
            if (json != null) {
                try {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> additionalInformation = mapper.read(json, Map.class);
                    details.setAdditionalInformation(additionalInformation);
                }
                catch (Exception e) {
                    logger.warn("Could not decode JSON for additional information: " + details, e);
                }
            }
            String scopes = rs.getString(11);
            if (scopes != null) {
                details.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(scopes));
            }
            details.setName(rs.getString(12));
            details.setRawClientSecret(rs.getString(13));
            return details;
        }
    }
}
