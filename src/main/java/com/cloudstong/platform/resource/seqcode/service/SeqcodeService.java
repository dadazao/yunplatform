package com.cloudstong.platform.resource.seqcode.service;

import java.util.List;

import com.cloudstong.platform.resource.form.model.FormColumn;
import com.cloudstong.platform.resource.metadata.model.Seqcode;

/**
 * @author sam
 * Created on 2012-11-22
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:编码管理的接口
 */
public interface SeqcodeService {

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
	public Seqcode querySeqById(Long plId);
	/**
	 * Description:返回所有编码
	 * 
	 * @return 编码集合
	 */
	public List<Seqcode> querySeq();
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
	public String doSaveSeqcode(Seqcode seqcode);
	
	/**
	 * Description:更新编码,确认编码是否赋予权限
	 * 
	 * @param psId
	 *           编码Id
	 * @param psUsestatus
	 *           编码状态      0:未赋予权限 1:赋予权限
	 */
	public void doUpdateSeqStatus(String psId, String psUsestatus);
	/**
	 * Description:更新编码,确认编码的上级码
	 * 
	 * @param psSeqValue
	 *           基本码
	 * @param psParentId
	 *           上级基本码
	 */
	public void doUpdateSeq(String psSeqValue, String psParentId);
	/**
	 * Description:更新编码,确认编码的扩展码,以及更新该编码的上级码
	 * 
	 * @param psSeqValue
	 *           基本码
	 * @param psParentId
	 *           上级基本码
	 */
	public void doUpdateSeqExtends(String psSeqValue, String psParentId);
	/**
	 * Description:更新编码,修改编码的名称
	 * 
	 * @param psSeqValue
	 *           基本码
	 * @param psName
	 *           编码名称
	 */
	public void doUpdateSeqName(String psSeqValue, String psName);
	
	/**
	 * Description:删除编码
	 * 
	 * @param psSeqValue
	 *           基本码
	 */
	public void doDeleteSeq(String psSeqValue);
	
	/**
	 * Description:编码初始化
	 * 
	 * Steps:
	 * 
	 */
	public void doInit();
	
	/**
	 * Description:知识复用中的编码生成
	 * 
	 * Steps:
	 * 
	 * @param pObjs
	 * 			可以为一个对象,也可以为两个对象
	 * 			如果为一个对象,你有两种选择(1)传递一个String,代表目录的ID;(2)传递一个Catalog,代表目录对象
	 * 			如果为两个对象,你有一种选择.即第一个对象为Tabulation,代表一个列表;第二个对象为Catalog,代表目录对象
	 * @param psType
	 * 			如果psType为1,代表你进行的为选择1;psType为2,代表你进行的为选择2,psType为3,代表你进行的为选择3
	 * @return
	 * @throws Exception
	 */
	public String doKnowledgeReuse(Object[] pObjs, String psType) throws Exception;
	
	/**
	 * Description:删除编码(逻辑删除)
	 * 
	 * @param psSeqValue
	 *           基本码
	 */
	public void doLogicDeleteSeq(String code);
	
	public String checkPartitionType(FormColumn formColumn);
}
