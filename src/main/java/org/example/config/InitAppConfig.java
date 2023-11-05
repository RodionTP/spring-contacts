package org.example.config;

import org.example.ContactWorker;
import org.example.InitContactWorker;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application-init.properties")
@Profile("init")
public class InitAppConfig {
    @Bean
    public ContactWorker contactWorker() {
        return new InitContactWorker();
    }
}
