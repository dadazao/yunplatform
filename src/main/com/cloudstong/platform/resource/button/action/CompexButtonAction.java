package com.cloudstong.platform.resource.button.action;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.resource.button.model.Button;
import com.cloudstong.platform.resource.button.service.ButtonService;
import com.cloudstong.platform.resource.buttongroup.model.ButtonGroup;
import com.cloudstong.platform.resource.buttongroup.service.ButtonGroupService;
import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 按钮Action(配置模块)
 */
public class CompexButtonAction extends CompexDomainAction {

	private static final long serialVersionUID = 5509123074759541009L;
	
	Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 按钮
	 */
	private Button button = null;

	/**
	 * 选中的按钮ID，以“，”分隔
	 */
	private String checkboxIDs = "";

	/**
	 * 按钮ID
	 */
	private String buttonID;

	/**
	 * 按钮类型（0：按钮 1：按钮组）
	 */
	private String type;

	/**
	 * 转发路径
	 */
	private String action = "";
	
	/**
	 * 按钮ID
	 */
	private Long id;

	/**
	 * 操作按钮的服务接口,<code>buttonService</code> 对象是ButtonService接口的一个实例
	 */
	@Resource
	private ButtonService buttonService;
	/**
	 * 操作按钮组的服务接口,<code>buttonGroupService</code> 对象是ButtonGroupService接口的一个实例
	 */
	@Resource
	private ButtonGroupService buttonGroupService;
	
	/**
	 * 根据ID查找按钮信息，返回json对象
	 * 
	 * @return NONE
	 */
	public String findButtonByID() throws Exception {
		button = buttonService.findByID(id);
		ObjectMapper mapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		mapper.writeValue(writer, button);
		return NONE;
	}
	
	/**
	 * 根据按钮ID和类型查找按钮或按钮组
	 * 
	 * @return NONE
	 * @throws Exception
	 */
	public String findButtonOrGroupByID() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		if (!id.equals("-1")) {
			if (type.equals("0")) {
				button = buttonService.findByID(id);
				mapper.writeValue(writer, button);
			} else if (type.equals("1")) {
				ButtonGroup buttonGroup = buttonGroupService.findByID(id);
				mapper.writeValue(writer, buttonGroup);
			}
		}
		return NONE;
	}

	public Log getLogger() {
		return logger;
	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

	public String getCheckboxIDs() {
		return checkboxIDs;
	}

	public void setCheckboxIDs(String checkboxIDs) {
		this.checkboxIDs = checkboxIDs;
	}

	public String getButtonID() {
		return buttonID;
	}

	public void setButtonID(String buttonID) {
		this.buttonID = buttonID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
