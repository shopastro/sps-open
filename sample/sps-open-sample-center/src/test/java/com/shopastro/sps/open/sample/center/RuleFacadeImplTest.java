package com.shopastro.sps.open.sample.center;


import com.alibaba.fastjson.JSON;
import com.shopastro.sps.open.sample.CountryRuleDTO;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ye.ly@shopastro-inc.com
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RuleFacadeImplTest {

    @Autowired
    RuleFacadeImpl ruleFacade;

    @Transactional
    @Test
    void createCountryRule() {
//        System.out.println(ruleFacade);
        CountryRuleDTO row = insertOneRow("CN");
//        System.out.println(JSON.toJSONString(row,true));
        assertNotNull(row.getId());
    }

    @Transactional
    @Test
    void selectCountryRuleById() {
        CountryRuleDTO row = insertOneRow("CN");
        assertNotNull(row.getId());
        // test select by id
        row = ruleFacade.selectCountryRuleById(row.getId()).tryGetSuccessfulData();
        assertNotNull(row);
        System.out.println(JSON.toJSONString(row,true));
    }

    @Transactional
    @Test
    void listCountryRule() {
        String countryCode = "Country-" + RandomUtils.nextLong();
        int cnt = RandomUtils.nextInt(1, 10);
        for (int i = 0; i < cnt; i++) {
            insertOneRow(countryCode);
        }
        //
        List<CountryRuleDTO> rows = ruleFacade.listCountryRule(countryCode).tryGetSuccessfulData();
        assertEquals(rows.size(), cnt);
    }

//    @Transactional
    @Test
    void updateCountryRule() {
        CountryRuleDTO row = insertOneRow("CN");
        String channelId = "updated-by-test";
        row.setChannelId(channelId);
        row = ruleFacade.updateCountryRule(row).tryGetSuccessfulData();
        System.out.println(JSON.toJSONString(row,true));
        assertEquals(channelId, row.getChannelId());
    }

    @Test
    void deleteCountryRuleById(){
        CountryRuleDTO row = insertOneRow("CN");
        ruleFacade.deleteCountryRuleById(row.getId());
        row = ruleFacade.ruleService.selectCountryRuleById(row.getId());
        assertNull(row);
    }


    private CountryRuleDTO insertOneRow(String countryCode) {
        CountryRuleDTO row = CountryRuleDTO.builder()
                .countryCode(countryCode)
                .channelId("adyen-" + RandomUtils.nextLong())
                .build();
        row.setStatus("enabled");
        return ruleFacade.createCountryRule(row).tryGetSuccessfulData();
    }
}