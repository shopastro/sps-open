package com.shopastro.sps.open.sample;

import com.shopastro.sps.open.share.JsonDataObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ye.ly@shopastro-inc.com
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryRuleDTO implements Serializable {


    Long id;
    Date gmtCreate;
    Date gmtModified;

    String status;

    String isDel;

    String jsonData;

    /**
     * 国家简码
     */
    String countryCode;

    /**
     * 支付渠道ID
     */
    String channelId;

}
