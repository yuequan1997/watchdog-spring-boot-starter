package org.yuequan.watchdog.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.yuequan.watchdog.client.Application;
import org.yuequan.watchdog.client.ApplicationRepository;
import org.yuequan.watchdog.client.ApplicationService;
import org.yuequan.watchdog.client.DefaultApplicationRepositoryImpl;

import javax.sql.DataSource;

/**
 * AuthorizationServerConfigurerAdapter Config
 * @see AuthorizationServerConfigurerAdapter
 * @author yuequan
 * @since 1.0
 **/
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore());
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApplicationService applicationService(){
        return new ApplicationService();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApplicationRepository<Application> applicationRepository(){
        return new DefaultApplicationRepositoryImpl(dataSource, passwordEncoder);
    }
}