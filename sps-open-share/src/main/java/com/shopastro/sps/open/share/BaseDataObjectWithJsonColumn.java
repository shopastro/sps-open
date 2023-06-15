package com.shopastro.sps.open.share;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Set;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDataObjectWithJsonColumn implements InjectIdSupport {
    Long id;
    Date gmtCreate;
    Date gmtModified;

    String status;

    @Builder.Default
    String isDeleted = "N";

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
