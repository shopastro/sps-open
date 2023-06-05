package com.shopastro.sps.open.id.generator;

import com.shopastro.sps.open.id.generator.impl.CachedIDGenerator;
import com.shopastro.sps.open.id.generator.impl.DefaultIDGenerator;
import com.shopastro.sps.open.id.generator.impl.UidProperties;
import com.shopastro.sps.open.id.generator.worker.DisposableWorkerIdAssigner;
import com.shopastro.sps.open.id.generator.worker.WorkerIdAssigner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * UID 的自动配置
 *
 * @author wujun
 * @date 2019.02.20 10:57
 */
@Configuration
@ConditionalOnClass({ DefaultIDGenerator.class, CachedIDGenerator.class })
@MapperScan({ "com.shopastro.sps.open.id.generator.worker.dao" })
@EnableConfigurationProperties(UidProperties.class)
public class IDGeneratorAutoConfigure {

	@Autowired
	private UidProperties uidProperties;

	@Bean
	@ConditionalOnMissingBean
	@Lazy
	DefaultIDGenerator DefaultIDGenerator() {
		return new DefaultIDGenerator(uidProperties);
	}

	@Bean
	@ConditionalOnMissingBean
	@Lazy
	CachedIDGenerator cachedUidGenerator() {
		return new CachedIDGenerator(uidProperties);
	}

	@Bean
	@ConditionalOnMissingBean
	WorkerIdAssigner workerIdAssigner() {
		return new DisposableWorkerIdAssigner();
	}
}
