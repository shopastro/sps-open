package com.shopastro.sps.open.share.page;

import com.shopastro.sps.open.share.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BasePageQuery extends BaseDTO implements PageQuerySupport {

    @Builder.Default
    int pageIndex = 1;
    @Builder.Default
    int pageSize = 20;
    @Builder.Default
    boolean needTotal = true;

}
