package com.shopastro.sps.open.sample.center;

import com.shopastro.sps.open.sample.CountryRuleDTO;
import com.shopastro.sps.open.sample.RuleFacade;
import com.shopastro.sps.open.sample.center.service.RuleService;
import com.shopastro.sps.open.share.rpc.RpcResult;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @author ye.ly@shopastro-inc.com
 */

@DubboService
public class RuleFacadeImpl implements RuleFacade {
    final RuleService ruleService;

    public RuleFacadeImpl(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @Override
    public RpcResult<CountryRuleDTO> createCountryRule(CountryRuleDTO countryRuleDTO) {
        return RpcResult.success(ruleService.createCountryRule(countryRuleDTO));
    }

    @Override
    public RpcResult<CountryRuleDTO> updateCountryRule(CountryRuleDTO countryRuleDTO) {
        return RpcResult.success(ruleService.updateCountryRule(countryRuleDTO));
    }

    @Override
    public RpcResult<Integer> deleteCountryRuleById(Long id) {
        return RpcResult.success(ruleService.deleteCountryRuleById(id));
    }

    @Override
    public RpcResult<CountryRuleDTO> selectCountryRuleById(Long id) {
        return RpcResult.success(ruleService.selectCountryRuleById(id));
    }

    @Override
    public RpcResult<List<CountryRuleDTO>> listCountryRule(String countryCode) {
        return RpcResult.success(ruleService.listCountryRule(countryCode));
    }
}
