package com.shopastro.sps.open.sample.center.dao;

import com.shopastro.sps.open.sample.CountryRuleDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author ye.ly@shopastro-inc.com
 */

@Mapper
public interface RuleMapper {

    String TABLE = "sample_rule";

    String ALL_COLS = "id,gmt_create,gmt_modified,is_del,status,country_code,channel_id,json_data";

    @Insert("insert into "
            + TABLE
            + " ("
            + ALL_COLS
            + ") values (#{id},now(),now(),'N',#{status},#{countryCode},#{channelId},#{jsonData})")
    Integer insert(CountryRuleDTO row);

    @Update("update " + TABLE + " set channel_id=#{channelId} where id=#{id} and is_del='N'")
    Integer updateById(CountryRuleDTO condition);

    @Update("update " + TABLE + " set is_del='Y' where id=#{id}")
    Integer deleteById(CountryRuleDTO condition);

    @Select("select " + ALL_COLS + " from " + TABLE + " where country_code=#{countryCode} and is_del='N'")
    List<CountryRuleDTO> selectByCountryCode(CountryRuleDTO condition);

    @Select("select " + ALL_COLS + " from " + TABLE + " where id=#{id} and is_del='N'")
    CountryRuleDTO selectById(CountryRuleDTO condition);
}
