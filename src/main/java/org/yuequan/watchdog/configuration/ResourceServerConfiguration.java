package org.yuequan.watchdog.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.yuequan.watchdog.provider.WatchdogUrlRegistryProvider;

import java.util.Map;
import java.util.Set;

/**
 * @author yuequan
 * @since
 **/
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter implements ApplicationContextAware {

    public static final String RESOURCE_NAME = "watchdog";

    @Value("${watchdog.oauth2.protect.path:/api/**}")
    private Set<String> apiPrefPath;

    private ApplicationContext applicationContext;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_NAME).stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] needAuthenticationPaths = apiPrefPath.toArray(new String[apiPrefPath.size()]);
        apiPrefPath.add("/applications");
        apiPrefPath.add("/applications/**");
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().antMatchers(apiPrefPath.toArray(new String[apiPrefPath.size()]))
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**", "/applications", "/applications/**")
                .permitAll().antMatchers(needAuthenticationPaths).authenticated()
                .and()
                .csrf()
                .disable();
        registerWatchdogProvider(http.authorizeRequests());
    }

    private void registerWatchdogProvider(ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry authorizeRequests) {
        Map<String, WatchdogUrlRegistryProvider> watchdogProviders = applicationContext.getBeansOfType(WatchdogUrlRegistryProvider.class);
        watchdogProviders.values().forEach(provider -> {
            provider.configure(authorizeRequests);
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
