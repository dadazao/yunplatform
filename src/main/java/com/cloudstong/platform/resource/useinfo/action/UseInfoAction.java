package com.cloudstong.platform.resource.useinfo.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.useinfo.model.ChartComp;
import com.cloudstong.platform.resource.useinfo.service.UseInfoService;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:使用信息Action
 */
@ParentPackage("default")
@Namespace("/pages/resource/useinfo")
@Results(value = { 
		@Result(name="compStat", location = "/WEB-INF/view/resource/useinfo/compStat.jsp")
})
public class UseInfoAction extends BaseAction{

	private static final long serialVersionUID = -8946726973799917047L;
	
	private Integer number;

	/**
	 * 操作使用信息的服务接口,<code>useInfoService</code> 对象是UseInfoService接口的一个实例
	 */
	@Resource
	private UseInfoService useInfoService;

	public UseInfoService getUseInfoService() {
		return useInfoService;
	}

	public void setUseInfoService(UseInfoService useInfoService) {
		this.useInfoService = useInfoService;
	}
	
	@Action("compStat")
	public String compStat() {
		return "compStat";
	}

	/**
	 * Description:统计各类组件的数量,返回json对象
	 * @return NONE
	 * @throws Exception
	 */
	@Action("state")
	public String state() throws Exception{
		List<ChartComp> comps = useInfoService.stat(number);
		for(int i=0; i<number; i++){
			comps.get(i).setColor(randomColor());
		}
		ObjectMapper mapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		mapper.writeValue(writer, comps);
		return NONE;
	}
	
	public String randomColor(){
		 String r,g,b;    
         Random random = new Random();    
         r = Integer.toHexString(random.nextInt(256)).toUpperCase();    
         g = Integer.toHexString(random.nextInt(256)).toUpperCase();    
         b = Integer.toHexString(random.nextInt(256)).toUpperCase();    
             
         r = r.length()==1 ? "0" + r : r ;    
         g = g.length()==1 ? "0" + g : g ;    
         b = b.length()==1 ? "0" + b : b ;   
         return "#"+r+g+b;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	

}
