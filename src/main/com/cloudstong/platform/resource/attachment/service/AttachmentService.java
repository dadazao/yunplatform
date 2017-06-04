package com.cloudstong.platform.resource.attachment.service;

import java.util.List;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.attachment.model.Attachment;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:附件服务接口
 */
public interface AttachmentService {

	/**
	 * 根据所有的文件ID查找文件
	 * @param fileIds 文件ID集合，多个ID之间用“，”分隔
	 * @return 文件集合
	 */
	List<Attachment> findAttachmentByIds(String fileIds);

	/**
	 * 根据文件ID删除文件
	 * @param fileid 文件ID
	 */
	void doDeleteById(String fileid);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param queryCriteria
	 * @return
	 */
	PageResult queryAttachment(QueryCriteria queryCriteria);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param selectedIDs
	 */
	void doDeleteAttachments(Long[] selectedIDs);

}
