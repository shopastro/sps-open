package com.shopastro.sps.open.share;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO implements Serializable {
    public final static String ATTACHMENT_KEY_IP = "_ip";

    /**
     * 店铺
     */
    Long shopId;
    /**
     * 站点
     */
    String websiteId;
    /**
     * 用于RPC透传
     */
    @Builder.Default
    private Map<String, String> attachments = Maps.newHashMap();

    public void putAttachment(String key, String value) {
        attachments.put(StringUtils.trimToEmpty(key), StringUtils.trimToEmpty(value));
    }

    public String getAttachment(String key) {
        return attachments.get(key);
    }
}
