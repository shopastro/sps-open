package com.shopastro.sps.open.share.page;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author ye.ly@shopastro-inc.com
 */

@Data
public class PageQueryResult<E> extends ArrayList<E> {
    Pagination pagination;
}
