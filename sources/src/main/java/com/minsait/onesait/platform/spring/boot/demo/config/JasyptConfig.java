package com.minsait.onesait.platform.spring.boot.demo.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

	@Value("${onesaitplatform.jasypt.phrase}")
	private String phrase;

	@Value("${onesaitplatform.password.encryptor.algorithm:PBEWithMD5AndDES}")
	private String algorithm;

	@Value("${onesaitplatform.password.encryptor.providerName:SunJCE}")
	private String providerName;

	@Value("${onesaitplatform.password.encryptor.saltGeneratorClassName:org.jasypt.salt.RandomSaltGenerator}")
	private String saltGeneratorClassName;

	@Value("${onesaitplatform.password.encryptor.ivGeneratorClassName:org.jasypt.salt.NoOpIVGenerator}")
	private String ivGeneratorClassName;

	@Value("${onesaitplatform.password.encryptor.outputType:base64}")
	private String outputType;

	@Value("${onesaitplatform.password.encryptor.iterations:1000}")
	private String iterations;

	@Value("${onesaitplatform.password.encryptor.poolSize:1}")
	private String poolSize;

	@Bean(name = "encryptorBean")
	public StringEncryptor stringEncryptor() {
		final PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		final SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(phrase);
		config.setAlgorithm(algorithm);
		config.setKeyObtentionIterations(iterations);
		config.setPoolSize(poolSize);
		config.setProviderName(providerName);
		config.setSaltGeneratorClassName(saltGeneratorClassName);
		config.setStringOutputType(outputType);
		encryptor.setConfig(config);
		return encryptor;
	}

}
