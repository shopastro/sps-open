package com.shopastro.sps.open.share.page;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

/**
 * @author ye.ly@shopastro-inc.com
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class PageQueryResult<E> extends ArrayList<E> {
    Pagination page;
}
