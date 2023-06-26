package com.shopastro.sps.open.datasource;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopastro.sps.open.share.page.PageQueryResult;
import com.shopastro.sps.open.share.rpc.RpcResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.instancio.Instancio;
import org.instancio.Select;
import org.instancio.generators.Generators;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        String prefix = RandomStringUtils.randomAlphabetic(8);
        for (int i = 0; i < 11; i++) {
            SampleObjectDO row = Instancio.of(SampleObjectDO.class)
                    .generate(Select.field(SampleObjectDO::getId), Generators::longs)
                    .generate(Select.field(SampleObjectDO::getName), it -> it.text().pattern(prefix + "-#a#a#a#a#a#a#a#a"))
                    .create();

            mapper.insert(row);
        }
        SampleMapper.QueryBase q = SampleMapper.QueryBase.builder().nameLike(prefix).build();
        q.setPageSize(3);
        PageQueryResult<SampleObjectDO> result = null;
        do {
            result = (PageQueryResult<SampleObjectDO>) mapper.selectPage(q);
            q.setPageIndex(q.getPageIndex() + 1);
            System.out.println(result.getPage());
            RpcResult<PageQueryResult<SampleObjectDO>> rpcResult = RpcResult.success(result);
            System.out.println(rpcResult);

            assertEquals(11, result.getPage().getTotal());
            assertEquals(4, result.getPage().getPages());
            assertEquals(11, rpcResult.getPage().getTotal());
            assertEquals(4, rpcResult.getPage().getPages());
        } while (result.getPage().isHasNextPage());
    }
}