/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.desktop.engine;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateExceptionHandler;

/**
 * @author Jason
 * 
 *         Created on 2014-9-27
 * 
 *         Description:
 * 
 */
public enum FreeMarkerEngine {
	CONFIGURATION;

	private final Configuration config;

	private FreeMarkerEngine() {
		this.config = new Configuration();
		config.setObjectWrapper(new DefaultObjectWrapper());
		config.setDefaultEncoding("UTF-8");
		// For production systems TemplateExceptionHandler.RETHROW_HANDLER is
		// better.
		config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}

	public Configuration getConfiguration() {
		return this.config;
	}
}
