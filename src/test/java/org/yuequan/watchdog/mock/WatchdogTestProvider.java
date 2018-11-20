package org.yuequan.watchdog.mock;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;
import org.yuequan.watchdog.provider.WatchdogUrlRegistryProvider;

/**
 * @author yuequan
 * @since 1.0.0
 **/
@Component
public class WatchdogTestProvider implements WatchdogUrlRegistryProvider {

    @Override
    public boolean configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        return true;
    }
}
