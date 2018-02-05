/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.mail;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.mail.util.MimeMessageParser;
import org.apache.log4j.Logger;

import com.cloudstong.platform.core.util.EncryptUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.email.model.InboxMessage;
import com.cloudstong.platform.email.model.MailAccount;
import com.cloudstong.platform.email.model.MailAttachment;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.pop3.POP3Folder;
import com.sun.mail.pop3.POP3Store;

/**
 * @author Jason
 * 
 *         Created on 2014-9-17
 * 
 *         Description: 邮件收取器
 * 
 */
public class MailReceiver {
	private final Logger log = Logger.getLogger(getClass());

	// 每次执行都新建线程池
	private final ExecutorService threadPool = Executors.newCachedThreadPool();
	private final CompletionService<InboxMessage> competionService = new ExecutorCompletionService<InboxMessage>(
			threadPool);

	private List<MailAttachment> attaList = null;
	private String attachmentDir = "email" + File.separator + "inbox";
	private String email = "";
	private Date lastReceiveDate = null;
	private MailAccount mailAccount;

	public MailReceiver(MailAccount mailAccount, List<MailAttachment> attaList, String contextPath) {
		this.mailAccount = mailAccount;
		this.email = mailAccount.getAddress();
		this.attachmentDir = contextPath + File.separator + "email" + File.separator + "inbox"
				+ File.separator;
		this.lastReceiveDate = mailAccount.getLastReceiveDate();
		if (attaList == null) {
			this.attaList = Collections.synchronizedList(new ArrayList<MailAttachment>());
		} else {
			this.attaList = Collections.synchronizedList(attaList);
		}
		// 判断attachmentDir是否存在，不存在则创建
		File dir = new File(attachmentDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	public List<InboxMessage> receive() {

		List<InboxMessage> result = new ArrayList<InboxMessage>();
		Session session = Session.getInstance(
				MailPropertiesUtils.convert(mailAccount),
				new MailAuthenticator(mailAccount.getAddress(), EncryptUtil
						.getDesString(mailAccount.getPassword())));
		Store store = null;
		Folder folder = null;
		try {
			String protocol = mailAccount.getMailType().toLowerCase(Locale.ENGLISH);
			Message messages[] = null;
			if ("pop3".equals(protocol)) {
				store = (POP3Store) session.getStore("pop3");
				store.connect();
				folder = (POP3Folder) store.getFolder("INBOX");
				folder.open(Folder.READ_ONLY);
				FetchProfile fp = new FetchProfile();
				fp.add(FetchProfile.Item.ENVELOPE);
				messages = folder.getMessages();
				folder.fetch(messages, fp);
			} else { // imap
				store = (IMAPStore) session.getStore("imap");
				store.connect();
				folder = (IMAPFolder) store.getFolder("INBOX");
				folder.open(Folder.READ_ONLY);
				FetchProfile fp = new FetchProfile();
				fp.add(FetchProfile.Item.ENVELOPE);

				messages = folder.getMessages();
				folder.fetch(messages, fp);
			}
			long start = System.currentTimeMillis();
			result = parseMessageAsync(messages);
			long end = System.currentTimeMillis();
			log.info("解析邮件共消耗时间：" + (end - start) + "毫秒");

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			log.error(e);
		} catch (MessagingException e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				if (folder != null) {
					folder.close(false);
				}
				if (store != null) {
					store.close();
				}
			} catch (MessagingException e) {
			}
		}
		return result;
	}

	/**
	 * Description: 多线程异步邮件解析
	 * 
	 * @param messages
	 */
	private List<InboxMessage> parseMessageAsync(Message[] messages) {

		List<InboxMessage> result = null;
		if (messages != null && messages.length > 0) {
			int messageSize = messages.length;
			result = new ArrayList<InboxMessage>(messageSize);

			for (Message message : messages) {
				competionService.submit(new ParserTask(message));
			}

			for (int i = 0; i < messageSize; i++) {
				try {
					InboxMessage message = competionService.take().get();
					if (message != null) {
						result.add(message);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					continue;
				} catch (ExecutionException e) {
					e.printStackTrace();
					continue;
				}
			}
			threadPool.shutdown();
		} else {
			result = Collections.emptyList();
		}
		log.info("新邮件数量" + result.size());
		return result;
	}

	/**
	 * Description: 判断邮件是否已读
	 * 
	 * @param flags
	 * @return
	 */
	private boolean judgeRead(Flags flags) {
		Flags.Flag[] flag = flags.getSystemFlags();
		for (int i = 0; i < flag.length; i++) {
			if (flag[i] == Flags.Flag.SEEN) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Description:邮件地址解析，合并成以";"分隔的字符串
	 * 
	 * @param to
	 * @return
	 */
	private String parseAddress(List<Address> list) {
		if (list == null) {
			return "";
		}
		StringBuilder str = new StringBuilder();
		for (Address a : list) {
			str.append(((InternetAddress) a).getAddress()).append(";");
		}
		if (str.length() > 0) {
			str.deleteCharAt(str.length() - 1);
		}
		return str.toString();
	}

	class FileWriter implements Runnable {
		private String fileName;
		private InputStream ins;

		public FileWriter(String fileName, InputStream ins) {
			this.fileName = fileName;
			this.ins = ins;
		}

		public void run() {
			File file = new File(fileName);
			if (!file.exists()) {
				BufferedOutputStream out = null;
				try {
					byte[] data = new byte[1024];
					out = new BufferedOutputStream(new FileOutputStream(file));
					int n = 0;
					while ((n = ins.read(data)) != -1) {
						out.write(data, 0, n);
					}
					out.flush();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (ins != null) {
							ins.close();
						}
						if (out != null) {
							out.close();
						}
					} catch (IOException e) {
					}
				}
			}
		}
	}

	class ParserTask implements Callable<InboxMessage> {

		private Message messageToParse;

		public ParserTask(Message messageToParse) {
			this.messageToParse = messageToParse;
		}

		@Override
		public InboxMessage call() throws Exception {
			Date sentDate = messageToParse.getSentDate();
			if (lastReceiveDate != null && lastReceiveDate.after(sentDate)) {
				return null;
			}
			MimeMessageParser parser = new MimeMessageParser((MimeMessage) this.messageToParse);
			try {
				parser.parse(); // 耗时较大
			} catch (Exception e) {
				log.info("邮件解析出错:parser.parse()错误被忽略:" + e.getMessage());
			}
			InboxMessage inboxMessage = new InboxMessage();
			try {
				final Long mailId = UniqueIdUtil.genId();

				inboxMessage.setId(mailId);// 生成id
				inboxMessage.setUseremail(email);
				inboxMessage.setMimeMessageId(((MimeMessage) messageToParse).getMessageID());
				inboxMessage.setFrom(parser.getFrom());
				inboxMessage.setTo(parseAddress(parser.getTo()));
				inboxMessage.setCc(parseAddress(parser.getCc()));

				String plainContent = parser.getPlainContent();
				String htmlContent = parser.getHtmlContent();
				inboxMessage.setContent(htmlContent == null ? plainContent : htmlContent);

				inboxMessage.setDate(sentDate);
				inboxMessage.setRead(judgeRead(messageToParse.getFlags()));

				String subject = MimeUtility.decodeText(parser.getSubject());
				inboxMessage.setSubject((subject == null || subject.trim().length() == 0) ? "(无主题)"
						: subject);

				// files
				List<DataSource> atchmtList = parser.getAttachmentList();
				if (atchmtList != null && !atchmtList.isEmpty()) {
					for (final DataSource ds : atchmtList) {
						String fileName = ds.getName();
						int pointIndex = fileName.lastIndexOf(".");
						String ext = fileName.substring(pointIndex);
						String name = fileName.substring(0, pointIndex)
								+ System.currentTimeMillis() + ext;
						String filePath = attachmentDir + name;
						System.out.println(filePath);
						try {
							new Thread(new FileWriter(filePath, ds.getInputStream())).start();
							MailAttachment atta = new MailAttachment(ds.getName(), filePath, mailId);
							attaList.add(atta);
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
					}
				}
			} catch (MessagingException e) {
				e.printStackTrace();
				log.info("邮件解析出错...错误被忽略:" + e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				log.info("邮件解析出错...错误被忽略:" + e.getMessage());
			}
			return inboxMessage;
		}
	}
}
