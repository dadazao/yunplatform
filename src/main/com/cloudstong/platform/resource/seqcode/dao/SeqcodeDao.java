package com.cloudstong.platform.resource.seqcode.dao;

import java.util.List;

import com.cloudstong.platform.resource.form.model.FormColumn;
import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.resource.metadata.model.Types;

/**
 * @author sam
 * Created on 2012-11-22
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:编码管理的数据库接口
 */
public interface SeqcodeDao {

	/**
	 * Description:返回编码
	 * 
	 * Steps:
	 * 
	 * @param plId
	 *           编码ID
	 *           
	 * @return 编码对象
	 */
	public Seqcode querySeqById(Long id);
	
	/**
	 * Description:返回所有编码
	 * 
	 * @return 编码集合
	 */
	public List<Seqcode> selectSeq();
	
	/**
	 * Description:返回所有编码
	 * 
	 * @param psSeqValue
	 *           基本码
	 *           
	 * @return 编码集合
	 */
	public List<Seqcode> querySeqByValue(String psSeqValue);
	
	/**
	 * Description:保存编码
	 * 
	 * @param seqcode
	 *           编码对象
	 *           
	 * @return 基本编码值
	 */
	public String saveSeq(Seqcode seqcode);
	
	/**
	 * Description:更新编码,确认编码是否赋予权限
	 * 
	 * @param psId
	 *           编码Id
	 * @param psUsestatus
	 *           编码状态      0:未赋予权限 1:赋予权限
	 */
	public void updateSeqStatus(String psId, String psUsestatus);
	
	/**
	 * Description:更新编码,确认编码的上级码
	 * 
	 * @param psSeqValue
	 *           基本码
	 * @param psParentId
	 *           上级基本码
	 */
	public void updateSeq(String psSeqValue, String psParentId);
	
	/**
	 * Description:更新编码,确认编码的扩展码,以及更新该编码的上级码
	 * 
	 * @param psSeqValue
	 *           基本码
	 * @param psParentId
	 *           上级基本码
	 */
	public void updateSeqExtends(String psSeqValue, String psParentId);
	
	/**
	 * Description:更新编码,修改编码的名称
	 * 
	 * @param psSeqValue
	 *           基本码
	 * @param psName
	 *           编码名称
	 */
	public void updateSeqName(String psSeqValue, String psName);
	
	/**
	 * Description:删除编码
	 * 
	 * @param psSeqValue
	 *           基本码
	 */
	public void deleteSeq(String psSeqValue);
	
	/**
	 * Description:编码初始化
	 * 
	 * Steps:
	 * 
	 */
	public void init();
	
	public String knowledgeReuse(Object[] pObjs, String psType) throws Exception;

	/**
	 * Description:删除编码(逻辑删除)
	 * 
	 * @param psSeqValue
	 *           基本码
	 */
	public void logicDeleteSeq(String code);

	public String checkPartitionType(FormColumn formColumn);
}
