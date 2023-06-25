package com.shopastro.sps.open.statemachine.persister;

import com.shopastro.sps.open.statemachine.impl.LogOnlyAction;
import com.shopastro.sps.open.statemachine.impl.TrueCondition;
import com.shopastro.sps.open.statemachine.persister.dal.mapper.StateMachineTraceMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ye.ly@shopastro-inc.com
 */

@Configuration
@MapperScan(basePackageClasses = StateMachineTraceMapper.class)
public class StateMachinePersisterAutoconfiguration {
    @Bean
    public LogOnlyAction logOnlyAction() {
        return new LogOnlyAction();
    }

    @Bean
    public TrueCondition trueCondition() {
        return new TrueCondition();
    }

}
