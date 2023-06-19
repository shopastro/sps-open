package com.shopastro.sps.open.share;

import com.alibaba.fastjson.JSON;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ye.ly@shopastro-inc.com
 */
class BaseDataObjectWithJsonColumnTest {
    @Test
    public void Test() {
        C2 o = C2.builder()
                .id(1L)
                .gmtCreate(new Date())
                .p1("ppp")
                .build();
        System.out.println(JSON.toJSONString(o));
        System.out.println(o.toString());


        BaseDataObjectWithJsonColumn o1 = BaseDataObjectWithJsonColumn.builder()
                .id(1L)
                .gmtCreate(new Date())
                .build();
        System.out.println(JSON.toJSONString(o1));
        System.out.println(o1.toString());


        System.out.println(new BaseDataObjectWithJsonColumn());
    }

    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class C2 extends BaseDataObjectWithJsonColumn {
        String p1;
    }
}