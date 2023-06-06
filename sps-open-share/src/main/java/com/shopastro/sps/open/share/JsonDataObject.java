package com.shopastro.sps.open.share;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.*;

/**
 * Created by liye on 2022/11/28.
 */
@Data
public abstract class JsonDataObject<T> implements Serializable {

    Long id;
    Date gmtCreate;
    Date gmtModified;

    String status;

    String isDel;

    String uuid;
    protected Map<String, String> properties = Maps.newHashMap();


    protected Set<String> getIgnoreJsonFieldNames() {
        return Sets.newHashSet("jsonData", "gmtCreate", "gmtModified", "isDel", "status");
    }

    protected Set<String> getAdditionIgnoreJsonFieldNames() {
        return Sets.newHashSet();
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public String getJsonData() {
        return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public void setJsonData(String jsonData) {
        Object obj = JSON.parseObject(jsonData, this.getClass());
        Set<String> ignores = Sets.newHashSet();
        if (getIgnoreJsonFieldNames() != null) {
            ignores.addAll(getIgnoreJsonFieldNames());
        }
        if (getAdditionIgnoreJsonFieldNames() != null) {
            ignores.addAll(getAdditionIgnoreJsonFieldNames());
        }
        BeanUtils.copyProperties(obj, this, ignores.toArray(new String[0]));
    }

    public T setProperty(String key, String val) {
        properties = Optional.ofNullable(properties).orElse(Maps.newHashMap());
        properties.put(key, val);
        return (T) this;
    }

    public T putAllProperty(Map<String, String> source) {
        if (source != null) {
            properties.putAll(source);
        }
        return (T) this;
    }

    public T generateUuid() {
        this.uuid = UUID.randomUUID().toString();
        return (T) this;
    }

    public String getProperty(String key) {
        return Optional.ofNullable(properties).orElse(Maps.newHashMap()).get(key);
    }
}
