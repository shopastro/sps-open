package com.shopastro.sps.open.share.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pagination implements Serializable {

    /**
     * 页码，从1开始
     */
    @Builder.Default
    int pageIndex = 1;
    /**
     * 页面大小
     */
    @Builder.Default
    int pageSize = 20;
    /**
     * 起始行
     */
    long startRow;
    /**
     * 末行
     */
    long endRow;
    /**
     * 总数
     */
    long total;
    /**
     * 总页数
     */
    int pages;

    /**
     * 是否为第一页
     */
    boolean isFirstPage;
    /**
     * 是否为最后一页
     */
    boolean isLastPage;
    /**
     * 是否有前一页
     */
    boolean hasPreviousPage;
    /**
     * 是否有下一页
     */
    boolean hasNextPage;
}
