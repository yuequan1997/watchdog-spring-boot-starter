package org.yuequan.watchdog.configuration;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Convenience annotation for enabling an Watchdog Authorization Server
 * @author yuequan
 * @since 1.0
 **/
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@Import({ResourceServerConfiguration.class, AuthorizationServerConfiguration.class, WatchdogEndpointsConfiguration.class})
@EnableWebSecurity
public @interface EnableWatchDog {
    /**
     *  Alias for the {@link #debug()} attribute. Allows for more concise annotation declarations e.g.:
     * @return if true, enables debug support with Spring Security
     */
    @AliasFor(annotation = EnableWebSecurity.class)
    boolean debug() default false;
}
