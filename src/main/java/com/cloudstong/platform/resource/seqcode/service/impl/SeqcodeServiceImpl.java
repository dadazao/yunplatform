package com.cloudstong.platform.resource.seqcode.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.form.model.FormColumn;
import com.cloudstong.platform.resource.metadata.model.Seqcode;
import com.cloudstong.platform.resource.seqcode.dao.SeqcodeDao;
import com.cloudstong.platform.resource.seqcode.service.SeqcodeService;

/**
 * @author sam
 * Created on 2012-11-22
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:编码管理的实现
 */
@Repository("seqcodeService")
public class SeqcodeServiceImpl implements SeqcodeService {

	@Resource
	private SeqcodeDao seqcodeDao;

	@Override
	public Seqcode querySeqById(Long lsId){
		return seqcodeDao.querySeqById(lsId);
	}
		
	@Override
	public List<Seqcode> querySeq(){
		return seqcodeDao.selectSeq();
	}
	
	@Override
	public String doSaveSeqcode(Seqcode seqcode){
		return seqcodeDao.saveSeq(seqcode);
	}
	
	public void doUpdateSeqStatus(String psId, String psUsestatus){
		seqcodeDao.updateSeqStatus(psId, psUsestatus);
	}
	
	public void doUpdateSeq(String psSeqValue, String psSeqExpand){
		//seqcodeDao.updateSeq(psSeqValue, psSeqExpand);
	}
	
	public void doUpdateSeqExtends(String psSeqValue, String psParentId){
		seqcodeDao.updateSeqExtends(psSeqValue, psParentId);
	}
	
	public void doUpdateSeqName(String psSeqValue, String psName){
		seqcodeDao.updateSeqName(psSeqValue, psName);
	}
	
	public void doDeleteSeq(String psSeqValue){
		seqcodeDao.deleteSeq(psSeqValue);
	}

	@Override
	public List<Seqcode> querySeqByValue(String psSeqValue) {
		// TODO Auto-generated method stub
		return seqcodeDao.querySeqByValue(psSeqValue);
	}
	
	public void doInit(){
		seqcodeDao.init();
	}
	
	public String doKnowledgeReuse(Object[] pObjs, String psType) throws Exception{
		return seqcodeDao.knowledgeReuse(pObjs, psType);
	}

	@Override
	public void doLogicDeleteSeq(String code) {
		seqcodeDao.logicDeleteSeq(code);
	}

	@Override
	public String checkPartitionType(FormColumn formColumn) {
		return seqcodeDao.checkPartitionType(formColumn);
	}
}
