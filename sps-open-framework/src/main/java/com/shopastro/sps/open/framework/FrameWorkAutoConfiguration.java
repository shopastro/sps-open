package com.shopastro.sps.open.framework;

import com.shopastro.sps.open.framework.validate.RpcValidateAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Configuration
public class FrameWorkAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public RpcValidateAspect rpcValidateAspect(){
        return new RpcValidateAspect();
    }
}
