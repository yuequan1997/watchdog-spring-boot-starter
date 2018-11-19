package org.yuequan.watchdog.sample;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yuequan.watchdog.configuration.EnableWatchDog;

@SpringBootApplication
@EnableWatchDog
@EnableSwagger2Doc
public class WatchdogSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(WatchdogSampleApplication.class, args);
    }
}
