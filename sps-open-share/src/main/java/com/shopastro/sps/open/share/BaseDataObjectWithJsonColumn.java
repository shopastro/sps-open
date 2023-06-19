package com.shopastro.sps.open.share;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Set;

/**
 * @author ye.ly@shopastro-inc.com
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
public class BaseDataObjectWithJsonColumn extends BaseDataObject {


    @JsonIgnore
    @JSONField(serialize = false)
    public String getJsonData() {
        return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public void setJsonData(String json) {
        Object obj = JSON.parseObject(json, this.getClass());
        BeanUtils.copyProperties(obj, this, getJsonIgnoreFieldNames().toArray(new String[0]));
    }

    protected Set<String> getJsonIgnoreFieldNames() {
        return Sets.newHashSet("jsonData", "gmtCreate", "gmtModified");
    }

}
