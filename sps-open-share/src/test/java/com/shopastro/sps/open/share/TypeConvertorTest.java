package com.shopastro.sps.open.share;

import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ye.ly@shopastro-inc.com
 */

class TypeConvertorTest {
    @Test
    public void test(){
        C1 o1 = new C1();
        o1.name = "1";
        C1 o11 = new C1();
        o11.name="11";
        o1.sub1 = o11;

        C1 o1Copy = TypeConvertor.convert(o1, C1.class);
        System.out.println(o1 == o1Copy);
        assertFalse(o1==o1Copy);
        System.out.println(o1.sub1 == o1Copy.sub1);
        assertFalse(o1.sub1==o1Copy.sub1);
        assertEquals("1",o1Copy.name);
        assertEquals("11",o1Copy.sub1.name);

        C2 o2 = TypeConvertor.convert(o1, C2.class);
        assertEquals("1",o2.name);
        assertEquals("11",o2.sub1.name);

        C3 o3 = TypeConvertor.convert(o1, C3.class);
        assertEquals(1,o3.name);
        assertEquals(11,o3.sub1.name);

    }

    private void assertFalse(boolean b) {
    }

    @Data
    public static class C1{
        String name;

        C1 sub1;
    }
    @Data
    public static class C2{
        String name;

        C2 sub1;
    }

    @Data
    public static class C3{
        Integer name;

        C3 sub1;
    }
}