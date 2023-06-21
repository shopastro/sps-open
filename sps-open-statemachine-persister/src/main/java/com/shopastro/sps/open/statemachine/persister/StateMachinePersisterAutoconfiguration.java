package com.shopastro.sps.open.statemachine.persister;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ye.ly@shopastro-inc.com
 */

@Configuration
@MapperScan({"com.shopastro.sps.open.statemachine.persister.dal.mapper"})
public class StateMachinePersisterAutoconfiguration {
}
