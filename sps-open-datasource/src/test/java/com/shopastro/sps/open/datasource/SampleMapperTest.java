package com.shopastro.sps.open.datasource;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopastro.sps.open.share.page.PageQueryResult;
import com.shopastro.sps.open.share.rpc.RpcResult;
import org.apache.commons.lang3.RandomUtils;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author ye.ly@shopastro-inc.com
 */

@SpringBootTest
class SampleMapperTest {
    @Autowired
    SampleMapper mapper;

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            SampleObjectDO row = Instancio.create(SampleObjectDO.class);
            mapper.insert(row);
        }
        long count = PageHelper.count(() -> mapper.selectAll());
        System.out.println("total: " + count);

        PageInfo<List<SampleObjectDO>> pageInfo;
        int pageNum = 1;
        do {
            System.out.println(pageNum);
            pageInfo = PageHelper.offsetPage((pageNum - 1) * 3, 3, true).doSelectPageInfo(() -> mapper.selectAll());
            System.out.println("total:" + pageInfo.getTotal());
            System.out.println("page:" + pageInfo.getPageNum());
//            System.out.println(page.getPageSize());
            System.out.println(JSON.toJSONString(pageInfo.getList()));
            pageNum++;

        } while (!pageInfo.isIsLastPage());

    }

    @Test
    public void testPage() {
        for (int i = 0; i < 11; i++) {
            SampleObjectDO row = Instancio.create(SampleObjectDO.class);
            row.setId(RandomUtils.nextLong());
            row.setName(row.getName() + "a");
            mapper.insert(row);
        }
        SampleMapper.QueryBase q = SampleMapper.QueryBase.builder().nameLike("a").build();
        PageQueryResult<SampleObjectDO> result = null;
        do {
            result = (PageQueryResult<SampleObjectDO>) mapper.selectPage(q);
            q.setPageIndex(q.getPageIndex() + 1);
            System.out.println(result.getPagination());
            System.out.println(RpcResult.success(result));

        } while (result.getPagination().isHasNextPage());
//        System.out.println(JSON.toJSONString(result, true));
    }
}