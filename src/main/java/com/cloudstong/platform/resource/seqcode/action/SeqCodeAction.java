package com.cloudstong.platform.resource.seqcode.action;

import java.io.IOException;

import javax.annotation.Resource;

import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.resource.seqcode.service.SeqcodeService;

/**
 * @author sam
 * Created on 2012-11-22
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:编码管理操作
 */
public class SeqCodeAction extends CompexDomainAction {

	private static final long serialVersionUID = -8107468432359066430L;
	@Resource
	private SeqcodeService seqcodeService;
	
	/**
	 * Description:编码初始化操作
	 * 
	 * Steps:
	 * 
	 * @return none
	 * @throws IOException
	 */
	public String init() throws IOException{
//		Object[] pObjs = new Object[]{"297"};
//		try {
//			seqcodeService.doKnowledgeReuse(pObjs, "1");
//		} catch (Exception e) {
//			// TODO Process Exception here. 
//			//throw new OtherException(msg,e); //or
//			e.printStackTrace();
//		}
//		printJSON("success");
//		return NONE;
		seqcodeService.doInit();
		printJSON("success");
		return NONE;
	}
}

