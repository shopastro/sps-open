package com.shopastro.sps.open.datasource;

import com.shopastro.sps.open.share.BaseDataObjectWithJsonColumn;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author ye.ly@shopastro-inc.com
 */

@Data
@SuperBuilder
@NoArgsConstructor
public class SampleObjectDO extends BaseDataObjectWithJsonColumn {
    String name;
}
