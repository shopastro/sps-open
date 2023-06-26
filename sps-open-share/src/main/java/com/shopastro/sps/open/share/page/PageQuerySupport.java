package com.shopastro.sps.open.share.page;

/**
 * 声明当前DTO对象支持分页查询
 *
 * @author ye.ly@shopastro-inc.com
 */
public interface PageQuerySupport {

    /**
     * 页码,start with 1
     *
     * @return
     */
    int getPageIndex();

    /**
     * 每页行数
     *
     * @return
     */
    int getPageSize();

    /**
     * 是否需要查询总数
     *
     * @return
     */
    boolean isNeedTotal();


}
