package com.cloudstong.platform.resource.enterinfo.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.enterinfo.model.EnterpriseInfo;
import com.cloudstong.platform.resource.enterinfo.service.EnterpriseInfoService;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:企业信息Action
 */
public class EnterpriseInfoAction extends BaseAction {
	
	@Resource
	private EnterpriseInfoService enterpriseInfoService;
	
	/**
	 * 加载默认的企业信息，返回json对象
	 * @return NONE
	 * @throws IOException
	 */
	public String loadDefault() throws IOException {
		EnterpriseInfo info = enterpriseInfoService.findDefaultInfo();
		ObjectMapper mapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		mapper.writeValue(writer, info);
		return NONE;
	}


}
