package org.example.config;

import org.example.ContactWorker;
import org.example.DefaultContactWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-default.properties")
@Profile("default")
public class DefaultAppConfig {
    @Bean
    public ContactWorker contactWorker() {
        return new DefaultContactWorker();
    }
}
