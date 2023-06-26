package com.shopastro.sps.open.datasource;

import com.shopastro.sps.open.datasource.mybatis.CommonSqlProvider;
import com.shopastro.sps.open.share.page.BasePageQuery;
import com.shopastro.sps.open.share.page.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Mapper
public interface SampleMapper {
    String BASE_COLUMNS = "id,gmt_create,gmt_modified,is_deleted";

    String JSON_COLUMN = "json_data";

    String ALL_COLUMN_TEMPLATE = BASE_COLUMNS + "%s," + JSON_COLUMN;
    String TABLE = "sample";
    String COLUMNS = ALL_COLUMN_TEMPLATE.formatted(",name,status");

    @InsertProvider(type = CommonSqlProvider.class, method = "insert")
    Integer insert(SampleObjectDO row);

    @SelectProvider(type = CommonSqlProvider.class, method = "selectById")
    SampleObjectDO selectById(@Param("id") Long id);

    @UpdateProvider(type = CommonSqlProvider.class, method = "updateById")
    Integer updateById(SampleObjectDO row);


    @DeleteProvider(type = CommonSqlProvider.class, method = "deleteById")
    Integer deleteById(@Param("id") Long id);

    @Select("select * from sample where is_deleted='N'")
    List<SampleObjectDO> selectAll();


    @PageQuery
    @Select("select * from sample where is_deleted='N' and name like concat('%',#{nameLike},'%')")
    List<SampleObjectDO> selectPage(QueryBase query);

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    class QueryBase
            extends BasePageQuery {
        String nameLike;
    }

}
