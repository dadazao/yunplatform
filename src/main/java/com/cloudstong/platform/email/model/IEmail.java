/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.model;

import java.util.Date;

/**
 * @author Jason
 * 
 *         Created on 2014-9-16
 * 
 *         Description: 邮件接口
 * 
 */
public interface IEmail {

	public Long getId();

	public String getTo();

	public String getCc();

	public String getFrom();

	public String getSubject();

	public String getContent();

	public boolean isMulti(); // 是否有附件

	public String getAttachments(); // 获取附件在磁盘的位置，绝对路径，用英文":"隔开

	public Date getDate();// 发送或者接收时间

}
