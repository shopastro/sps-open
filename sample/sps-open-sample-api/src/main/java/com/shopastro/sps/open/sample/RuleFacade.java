package com.shopastro.sps.open.sample;

import com.shopastro.sps.open.share.rpc.RpcResult;

import java.util.List;

/**
 * @author ye.ly@shopastro-inc.com
 */
public interface RuleFacade {

    RpcResult<CountryRuleDTO> createCountryRule(CountryRuleDTO countryRuleDTO);

    RpcResult<CountryRuleDTO> updateCountryRule(CountryRuleDTO countryRuleDTO);

    RpcResult<Integer> deleteCountryRuleById(Long id);

    RpcResult<CountryRuleDTO> selectCountryRuleById(Long id);

    RpcResult<List<CountryRuleDTO>> listCountryRule(String countryCode);
}
