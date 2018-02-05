package com.cloudstong.platform.resource.template.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.model.Code;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.model.FormColumnExtend;
import com.cloudstong.platform.resource.form.model.Tab;
import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:主要负责打印模板的一些操作
 */
@SuppressWarnings("serial")
public class PrintAction extends CompexDomainAction{

	/**
	 * 字处理组件产生的文件的唯一标识,<code>recordId</code> 是String类的一个实例.
	 */
	private String recordId;
	
	/**
	 * Description:得到当前表单的列信息(列名称,中文名称)
	 * 
	 * Steps:
	 * 
	 * @return none
	 * @throws Exception
	 */
	public String tabColumn() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		
		List<DomainVO> domainVOs = new ArrayList<DomainVO>();
		this.form = formService.getFormByIdAndDomainVO(formId,domainVOs,user);
		List<Tab> _lstTab = form.getTabs();
		List<Map> _lstColumn = new ArrayList<Map>();
		if(_lstTab != null &&_lstTab.size()>0){
			Tab tab = _lstTab.get(0);
			List<FormColumnExtend> _lstFormColumnExtend = null;
			
			try {
				if(tab.getTemplate().getType().equals("0")){
					_lstFormColumnExtend = tab.getFormColumnExtends();
				}else{
					_lstFormColumnExtend = tab.getPartitions().get(0).getFormColumnExtends();
				}
				for (FormColumnExtend formColumnExtend : _lstFormColumnExtend) {
					Map _mapColumn = new HashMap();
					_mapColumn.put("columnName", formColumnExtend.getFormColumn().getColumnName());
					_mapColumn.put("columnZhName", formColumnExtend.getFormColumn().getColumnZhName());
					_lstColumn.add(_mapColumn);
				}
			} catch (Exception e) {}
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		Writer writer = (Writer) response.getWriter();
		writer.write(objectMapper.writeValueAsString(_lstColumn));
		writer.close();
		return NONE;
	}
	
	/**
	 * Description:查询打印模板中书签所需要替换的数据
	 * 
	 * Steps:
	 * 
	 * @return none
	 * @throws Exception
	 */
	public String bookMarkJson() throws Exception {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		
		this.form = formService.getFormByIdAndDomainVO(formId,getDomainVos(this.params),user);
		List<Tab> _lstTab = form.getTabs();
		StringBuilder _sbdHtml = new StringBuilder();
		_sbdHtml.append("[");
		for (int i=0; i<_lstTab.size(); i++) {
			Tab tab = _lstTab.get(i);
			List<FormColumnExtend> _lstFormColumnExtend = null;
			try {
				if(tab.getTemplate().getType().equals("0")){
					_lstFormColumnExtend = tab.getFormColumnExtends();
				}else{
					_lstFormColumnExtend = tab.getPartitions().get(0).getFormColumnExtends();
				}
				for (FormColumnExtend formColumnExtend : _lstFormColumnExtend) {
					if(formColumnExtend.getFormColumn().getInputType() == 1 || 
							formColumnExtend.getFormColumn().getInputType() == 3 || 
							formColumnExtend.getFormColumn().getInputType() == 8){
						List<Code> _lstCode = formColumnExtend.getCodes();
						for (Code code : _lstCode) {
							if(code.getValue().equals(formColumnExtend.getValue())){
								formColumnExtend.setValue(code.getText());
							}
						}
					}else if(formColumnExtend.getFormColumn().getInputType() == 4){
						if(formColumnExtend.getValue()==null){
							formColumnExtend.setValue("");
						}
						String[] vcs=formColumnExtend.getValue().toString().split(",");
						
						List<Code> _lstCode = formColumnExtend.getCodes();
						for(int vc=0;vc<vcs.length;vc++){
							for(int c=0;c<_lstCode.size();c++) {
								if(vcs[vc].equals(_lstCode.get(c).getValue())) {
									if(vc==0){
										formColumnExtend.setValue(_lstCode.get(c).getText());
									}else{
										formColumnExtend.setValue(formColumnExtend.getValue()+","+_lstCode.get(c).getText());
									}
								}
							}
						}
					}
					_sbdHtml.append("{\"name\":\""+formColumnExtend.getFormColumn().getColumnZhName()+"\",\"label\":\""+formColumnExtend.getFormColumn().getColumnName()+"\",\"value\":\""+formColumnExtend.getValue()+"\"},");
				}
			} catch (Exception e) {}
		}
		if (_sbdHtml.length() > 1)
			_sbdHtml.deleteCharAt(_sbdHtml.length() - 1);
		_sbdHtml.append("]");
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		Writer writer = (Writer) response.getWriter();
		writer.write(_sbdHtml.toString());
		writer.close();
		return NONE;
	}
	
	/**
	 * Description:利用公用数据库记录查询方法,将查询返回的方法转换为json子串
	 * 
	 * Steps:
	 * 
	 * @return none
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String tableDataK() throws IOException {
		List<Map> _lstResult = compexDomainService.queryTableData(mainTable,params);
		List<Map> _lstJsonTmp = new ArrayList<Map>();
		if (_lstResult != null && _lstResult.size() > 0) {
			for (int i = 0; i < _lstResult.size(); ++i) {
				Map mapEntries = (Map) _lstResult.get(i);
				Iterator itEntries = mapEntries.entrySet().iterator();
				while (itEntries.hasNext()) {
					Map.Entry entry = (Map.Entry) itEntries.next();
					String _sKey = "";
					String _sValue = "";
					if (entry.getKey() != null && entry.getValue() != null) {
						_sKey = entry.getKey().toString();
						_sValue = entry.getValue().toString();
						Map _map = new HashMap();
						_map.put("lable", _sKey);
						_map.put("value", _sValue);
						_lstJsonTmp.add(_map);
					}
				}
			}
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		Writer writer = (Writer) response.getWriter();
		writer.write(objectMapper.writeValueAsString(_lstJsonTmp));
		writer.close();
		return NONE;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
}
