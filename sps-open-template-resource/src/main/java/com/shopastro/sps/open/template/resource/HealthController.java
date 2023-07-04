package com.shopastro.sps.open.template.resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ye.ly@shopastro-inc.com
 */

@CrossOrigin("*")
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
