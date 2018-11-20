package org.yuequan.watchdog.provider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author yuequan
 * @since
 **/
public interface WatchdogUrlRegistryProvider {
    /**
     * 配置权限入口
     * @param config
     * @return added result
     */
    boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
