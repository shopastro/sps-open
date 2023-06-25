package com.shopastro.sps.open.share;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ye.ly@shopastro-inc.com
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseDataObject implements InjectIdSupport, Serializable {

    Long id;
    Date gmtCreate;
    Date gmtModified;

    String status;

    @Builder.Default
    Boolean isDeleted = false;
}
