package com.cloudstong.platform.resource.attachment.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.attachment.dao.AttachmentDao;
import com.cloudstong.platform.resource.attachment.model.Attachment;
import com.cloudstong.platform.resource.attachment.rowmap.AttachmentRowMapper;
import com.cloudstong.platform.resource.attachment.service.AttachmentService;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:附件服务接口实现类
 */
@Repository("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {
	
	@Resource
	private AttachmentDao attachmentDao;

	public AttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	@Override
	public List<Attachment> findAttachmentByIds(String fileIds) {
		return this.attachmentDao.findAttachmentByIds(fileIds);
	}

	@Override
	public void doDeleteById(String fileid) {
		this.attachmentDao.delete(fileid);
	}

	@Override
	public PageResult queryAttachment(QueryCriteria queryCriteria) {
		return attachmentDao.query("select a.*,u.tbl_yonghuxingming from sys_attachment a left join sys_user u on a.comm_createBy = u.id where 1=1 ",queryCriteria, new AttachmentRowMapper());
	}

	@Override
	public void doDeleteAttachments(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			attachmentDao.delete(id);
		}
	}
	
}
