package com.cloudstong.platform.desktop.service;

import java.util.List;

import com.cloudstong.platform.email.model.InboxMessage;
import com.cloudstong.platform.message.model.ReceivedMessage;
import com.cloudstong.platform.system.model.SysOrg;
import com.cloudstong.platform.system.model.SysUser;

public interface DesktopService {

	// 机构
	public List<SysOrg> loadOrgs();

	// 署内新闻
	public List<List<String>> loadNews();

	// 行业公告
	public List<List<String>> loadAnnouncements();

	// 邮件
	public List<InboxMessage> loadInbox(final SysUser user);

	// 消息
	public List<ReceivedMessage> loadMessages(final SysUser user);
}
