package org.yuequan.watchdog.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yuequan.watchdog.endpoint.ApplicationEndpoint;

/**
 * @author yuequan
 * @since
 **/
@Configuration
public class WatchdogEndpointsConfiguration {
    @Bean
    public ApplicationEndpoint applicationEndpoint(){
        return new ApplicationEndpoint();
    }
}
