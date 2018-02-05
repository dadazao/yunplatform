package com.cloudstong.platform.resource.attachment.dao;

import java.util.List;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.dao.BaseJdbcDao;
import com.cloudstong.platform.core.model.Domain;
import com.cloudstong.platform.resource.attachment.model.Attachment;
import com.cloudstong.platform.resource.attachment.rowmap.AttachmentRowMapper;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:附件操作数据库接口
 */
public interface AttachmentDao extends BaseJdbcDao<Attachment, Long> {
	/**
	 * 向附件表中插入一条附件记录
	 * @param attachment 附件记录
	 * @return 插入的记录的ID
	 */
	public Long insertAttachment(Attachment attachment);

	/**
	 * 向业务表附件字段中加入新添加的附件ID
	 * @param model 表名称
	 * @param columnname 表字段
	 * @param attachmentId 附件ID
	 * @param recordid 业务记录ID
	 */
	public void addRelByAttachment(String model, String columnname,
			String attachmentId, String recordid);

	/**
	 * 根据所有的文件ID查找文件
	 * @param fileIds 文件ID集合，多个ID之间用“，”分隔
	 * @return 文件集合
	 */
	public List<Attachment> findAttachmentByIds(String fileIds);

	/**
	 * 根据文件ID删除文件
	 * @param fileid 文件ID
	 */
	public void delete(String fileid);
	
	/**
	 * 根据表名称查找表的中文名称
	 * @param tableEnName 表名称
	 * @return 表的中文名称
	 */
	public String findTableName(String tableEnName);
	
	/**
	 * 根据表名称和字段名称查找字段的中文名称
	 * @param tableEnName 表名称
	 * @param columnEnName 字段名称
	 * @return 字段的中文名称
	 */
	public String findColumnName(String tableEnName,String columnEnName);

	/**
	 * 根据表名称，所属对象ID和字段ID查找所属对象的值
	 * @param model 表名称
	 * @param recordid 所属对象ID
	 * @param recordColumn 字段ID
	 * @return 所属对象值
	 */
	public String findRecordColumnValue(String model, String recordid,
			String recordColumn);

	/**
	 * 根据字段ID查找字段名称
	 * @param recordColumn 字段ID
	 * @return 字段名称
	 */
	public String findRecordColumnName(String recordColumn);

	/**
	 * 根据业务数据记录ID查找附件 
	 * @param id 业务数据记录ID
	 * @return 附件集合
	 */
	public List<Attachment> findAttachmentsByRid(Long id);

	/**
	 * 根据业务通用数据模型，修改附件表中的所属对象
	 * @param attachment 要修该所属对象的附件
	 * @param domain 通用数据模型
	 */
	public void updateAttachment(Attachment attachment, Domain domain, Long recordId);

	public void updateRecordId(String uploadifyValue,Long recordId);

}
