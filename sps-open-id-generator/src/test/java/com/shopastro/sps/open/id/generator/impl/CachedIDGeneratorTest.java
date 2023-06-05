package com.shopastro.sps.open.id.generator.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ye.ly@shopastro-inc.com
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CachedIDGeneratorTest {

    @Autowired
    CachedIDGenerator cachedIDGenerator;

    @Test
    public void testNewId() {
        long id = cachedIDGenerator.nextId();
//        System.out.println(id);
        assertNotNull(id);
    }
}