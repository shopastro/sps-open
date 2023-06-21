package com.shopastro.sps.open.statemachine.persister.dal.mapper;

import com.shopastro.sps.open.datasource.mybatis.CommonSqlProvider;
import com.shopastro.sps.open.id.generator.aop.InjectId;
import com.shopastro.sps.open.statemachine.persister.dal.entiry.StateMachineTraceDO;
import org.apache.ibatis.annotations.*;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Mapper
public interface StateMachineTraceMapper {
    String TABLE = "statemachine_trace";
    String COLUMNS = "id,gmt_create,gmt_modified,is_deleted,tenant_id,biz_id,biz_type,statemachine_id,before_state,after_state,error,json_data";

    @InjectId
    @SelectProvider(type = CommonSqlProvider.class, method = "insert")
    Integer insert(StateMachineTraceDO row);

    @SelectProvider(type = CommonSqlProvider.class, method = "selectById")
    StateMachineTraceDO selectById(@Param("id") Long id);

    @UpdateProvider(type = CommonSqlProvider.class, method = "updateById")
    Integer updateById(StateMachineTraceDO row);


    @DeleteProvider(type = CommonSqlProvider.class, method = "deleteById")
    Integer deleteById(@Param("id") Long id);
}
