package com.shopastro.sps.open.share;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

import java.util.Date;

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

    @JSONField(serialize = false)
    public String getJsonData() {
        return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
    }

    public void setJsonData(String json) {
        Object obj = JSON.parseObject(json, this.getClass());
        BeanUtils.copyProperties(obj, this);
    }

}
