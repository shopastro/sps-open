package com.shopastro.sps.open.template;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Configuration
public class ResourceAutoConfiguration {

    @Value("${APP_NAME:NotSet}")
    String appName;


    @Bean
    public HealthController healthController() {
        return new HealthController(appName);
    }

}
