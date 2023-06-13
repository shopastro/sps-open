package com.shopastro.sps.open.template.resource.center;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ye.ly@shopastro-inc.com
 */

@RestController
public class HealthController {

    final String appName;

    public HealthController(String appName) {
        this.appName = appName;
    }


    @GetMapping({"/health"})
    public String health() {
        return "I'm OK!\r\nAPP_NAME:" + appName;
    }

}
