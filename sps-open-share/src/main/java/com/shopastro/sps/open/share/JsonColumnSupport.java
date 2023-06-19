package com.shopastro.sps.open.share;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Set;

/**
 * @author ye.ly@shopastro-inc.com
 */
public abstract class JsonColumnSupport implements Serializable {
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
