package com.tirion.config;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringValueResolver;

@Configuration
@ConditionalOnBean(annotation = EnableSchoolConfiguration.class)
public class SchoolBaseAutoConfiguration implements ApplicationContextAware, EmbeddedValueResolverAware {

	protected ApplicationContext applicationContext;

	protected StringValueResolver resolver;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * Set the StringValueResolver to use for resolving embedded definition values.
	 *
	 * @param resolver
	 */
	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		this.resolver = resolver;
	}
}

