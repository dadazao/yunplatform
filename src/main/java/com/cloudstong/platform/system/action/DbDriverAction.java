package com.cloudstong.platform.system.action;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.system.service.DataSourceService;

/**
 * @author sam
 * Created on 2012-11-22
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:数据源管理操作
 */
public class DbDriverAction extends CompexDomainAction {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据源管理服务, <code>dataSourceService</code> 是DataSourceService接口的一个实例.
	 */
	@Resource
	private DataSourceService dataSourceService;

	/**
	 * Description:切换数据源
	 * 
	 * Steps:
	 * 
	 * @return none
	 */
	public String enabled() throws Exception {
		try {
			String _sId = "";
			if (params != null) {
				_sId = params.split(":")[1].toString();
			}
			String result = dataSourceService.doChangeDataBase(_sId);
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(result);
			out.flush();
			out.close();
		} catch (Exception e) {
			printJSON("fail");
		}
		return NONE;
	}
}
