package com.shopastro.sps.open.sample.center.service;

import com.shopastro.sps.open.id.generator.impl.CachedIDGenerator;
import com.shopastro.sps.open.sample.CountryRuleDTO;
import com.shopastro.sps.open.sample.center.dao.RuleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Service
public class RuleService {

    final RuleMapper ruleMapper;
    final CachedIDGenerator cachedIDGenerator;

    public RuleService(RuleMapper ruleMapper, CachedIDGenerator cachedIDGenerator) {
        this.ruleMapper = ruleMapper;
        this.cachedIDGenerator = cachedIDGenerator;
    }

    public CountryRuleDTO createCountryRule(CountryRuleDTO countryRuleDTO) {
        countryRuleDTO.setId(cachedIDGenerator.getUID());
        ruleMapper.insert(countryRuleDTO);
        return countryRuleDTO;

    }

    public List<CountryRuleDTO> listCountryRule(String countryCode) {
        CountryRuleDTO condition = CountryRuleDTO.builder()
                .countryCode(countryCode)
                .build();
        return ruleMapper.selectByCountryCode(
                condition
        );
    }

    public CountryRuleDTO selectCountryRuleById(Long id) {
        CountryRuleDTO condition = CountryRuleDTO.builder()
                .build();
        condition.setId(id);
        return ruleMapper.selectById(condition);
    }

    public CountryRuleDTO updateCountryRule(CountryRuleDTO countryRuleDTO) {
        ruleMapper.updateById(countryRuleDTO);
        return selectCountryRuleById(countryRuleDTO.getId());
    }

    public Integer deleteCountryRuleById(Long id) {
        CountryRuleDTO condition = CountryRuleDTO.builder()
                .build();
        condition.setId(id);
        return ruleMapper.deleteById(condition);
    }
}
