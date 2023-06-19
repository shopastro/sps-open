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
class DataObjectTest {
    @Test
    public void Test() {
        C1 o = C1.builder()
                .id(1L)
                .gmtCreate(new Date())
                .p1("ppp")
                .build();
        System.out.println(JSON.toJSONString(o));
        System.out.println(o.toString());


        BaseDataObject o1 = BaseDataObject.builder()
                .id(1L)
                .gmtCreate(new Date())
                .build();
        System.out.println(JSON.toJSONString(o1));
        System.out.println(o1.toString());
    }

    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class C1 extends BaseDataObject {
        String p1;
    }
}