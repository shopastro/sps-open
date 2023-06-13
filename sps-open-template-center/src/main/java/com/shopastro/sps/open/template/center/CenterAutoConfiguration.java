package com.shopastro.sps.open.template.center;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Configuration
public class CenterAutoConfiguration {

    @Value("${APP_NAME:NotSet}")
    String appName;


    @Bean
    public HealthController healthController() {
        return new HealthController(appName);
    }

}
