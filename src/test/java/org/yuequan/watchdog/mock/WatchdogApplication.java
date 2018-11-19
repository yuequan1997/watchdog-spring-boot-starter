package org.yuequan.watchdog.mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;
import org.yuequan.watchdog.configuration.EnableWatchDog;

@SpringBootApplication
@EnableWatchDog
@ActiveProfiles("test")
public class WatchdogApplication {
    public static void main(String[] args) {
        SpringApplication.run(WatchdogApplication.class, args);
    }

}
