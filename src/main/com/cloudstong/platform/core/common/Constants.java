package com.cloudstong.platform.core.common;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

/**
 * @author Allan Created on 2012-11-20
 * 
 *         Date Reviser Description 
 * 
 *         Description:系统中常量定义
 */
public class Constants {
	public static final Long DEFAULT_SYSTEM_ID = 0L;
	
	public static final String DEFAULT_SYSTEM = "YUNPLATFORM";
	
	public static final String BUNDLE_KEY = "ApplicationResources";

	public static final String FILE_SEP = System.getProperty("file.separator");

	public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;

	public static final String CONFIG = "appConfig";
	
	public static final String CSS_THEME = "csstheme";

	public static final String PREFERRED_LOCALE_KEY = "org.apache.struts2.action.LOCALE";

	public static final String USER_KEY = "userForm";

	public static final String USER_LIST = "userList";

	public static final String REGISTERED = "registered";

	private static final String ROLE_SUPER = "ROLE_SUPER";
	
	private static final String ROLE_PUBLIC = "ROLE_PUBLIC";
	
	private static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
	
	public static final GrantedAuthority ROLE_GRANT_SUPER = new GrantedAuthorityImpl("ROLE_SUPER");
	
	public static final ConfigAttribute ROLE_CONFIG_PUBLIC = new SecurityConfig("ROLE_PUBLIC");
	
	public static final ConfigAttribute ROLE_CONFIG_ANONYMOUS = new SecurityConfig("ROLE_ANONYMOUS");

	/**
	 * 表关系
	 */
	public static final int RELATION_1_1 = 0;

	public static final int RELATION_1_N = 1;

	public static final int RELATION_N_1 = 2;

	public static final int RELATION_M_N = 3;

	/**
	 * 录入类型
	 */
	public static final int INPUT_TYPE_TEXTBOX = 0;// 文本框

	public static final int INPUT_TYPE_TEXTBOX_COMBOX = 1;// 下拉框

	public static final int INPUT_TYPE_TEXTBOX_FIELD = 2;// 文本域

	public static final int INPUT_TYPE_TEXTBOX_RADIO = 3;// 单选框

	public static final int INPUT_TYPE_TEXTBOX_CHECKBOX = 4;// 复选

	public static final int INPUT_TYPE_TEXTBOX_FILE = 5;// 文件上传

	public static final int INPUT_TYPE_TEXTBOX_DATE = 6;// 日历控件

	public static final int INPUT_TYPE_TEXTBOX_TREE = 7;// 树

	public static final int INPUT_TYPE_TEXTBOX_SEARCHCOMBOX = 8;// 搜索下拉框

	public static final int INPUT_TYPE_TEXTBOX_CUSTOM = 9;// 自定义

	public static final int INPUT_TYPE_TEXTBOX_EDITOR = 10;// 文本编辑器

	public static final int Input_TYPE_TEXTBOX_PASSWORD = 11;// 密码框

	public static final int Input_TYPE_TEXTBOX_OFFICE = 12;// 字处理

	public static final int Input_TYPE_TEXTBOX_CODECASECADE = 13;// 代码级联

	public static final int INPUT_TYPE_TEXTBOX_UPLOADIFY = 14;// 多附件上传

	public static final int INPUT_TYPE_TEXTBOX_PERSONCHOISE = 15;// 部门人员选择 ---已废弃
	
	public static final int INPUT_TYPE_TEXTBOX_AUTOCOMPLETE = 16;//自动补齐组件

	public static final int SELECT_DATA_TYPE_CODE = 0;

	public static final int SELECT_DATA_TYPE_RELATION = 1;

	/**
	 * 文本框类型
	 */
	public static final int TEXTBOX_TYPE_TEXTBOX = 0;

	public static final int TEXTBOX_TYPE_COMBOX = 1;

	public static final int TEXTBOX_TYPE_FIELD = 2;

	public static final int TEXTBOX_TYPE_SEARCHCOMBOX = 3;

	public static final int TEXTBOX_TYPE_FILE = 4;

	/**
	 * 按钮、按钮组类型
	 */
	public static final String TYPE_BUTTON = "0";

	public static final String TYPE_BUTTON_GROUP = "1";

	public static final String ButtonGroup_TYPE_PARENT = "按钮组类型";

	/**
	 * 代码管理
	 */
	public static final String DICTIONARY_TYPE_CLASS = "分类";

	public static final String DICTIONARY_TYPE_DATA = "数据";

	public static final Integer DICTIONARY_STATUS_USE = 0;

	public static final Integer DICTIONARY_STATUS_DELETE = 1;

	/**
	 * 编码管理
	 */
	public static final Long SEQCODE_SID_STANDARD_ITEM = 100000L;

	public static final Long SEQCODE_SID_STANDARD_TYPE = 1000L;

	public static final Integer SEQCODE_STATUS_USE = 0;

	public static final Integer SEQCODE_STATUS_DELETE = 1;

	public static final String SEQCODE_TYPE_TABLE = "TA";

	public static final String SEQCODE_NAME_TABLE = "数据表";

	public static final String SEQCODE_TYPE_FORM = "BD";

	public static final String SEQCODE_NAME_FORM = "表单";

	public static final String SEQCODE_TYPE_DICTIONARY = "DM";

	public static final String SEQCODE_NAME_DICTIONARY = "代码";

	public static final String SEQCODE_TYPE_FORMMODEL = "BM";

	public static final String SEQCODE_NAME_FORMMODEL = "表单模板";

	public static final String SEQCODE_TYPE_LISTBOX = "LB";

	public static final String SEQCODE_NAME_LISTBOX = "列表";

	public static final String SEQCODE_TYPE_LISTMODEL = "LM";

	public static final String SEQCODE_NAME_LISTMODEL = "列表模板";

	public static final String SEQCODE_TYPE_BUTTON = "AN";

	public static final String SEQCODE_NAME_BUTTON = "按钮";

	public static final String SEQCODE_TYPE_BUTTONGROUP = "AZ";

	public static final String SEQCODE_NAME_BUTTONGROUP = "按钮组";

	public static final String SEQCODE_TYPE_TEXTBOX = "WB";

	public static final String SEQCODE_NAME_TEXTBOX = "文本框";

	public static final String SEQCODE_TYPE_TREE = "TR";

	public static final String SEQCODE_NAME_TREE = "树";

	public static final String SEQCODE_TYPE_MODULE = "MK";

	public static final String SEQCODE_NAME_MODULE = "模块";

	public static final String SEQCODE_TYPE_DATEMODULE = "DC";

	public static final String SEQCODE_NAME_DATEMODULE = "日期组件";

	public static final String SEQCODE_TYPE_RADIO = "RD";

	public static final String SEQCODE_NAME_RADIO = "单选框";

	public static final String SEQCODE_TYPE_CHECKBOX = "CB";

	public static final String SEQCODE_NAME_CHECKBOX = "复选框";

	public static final String SEQCODE_TYPE_CATALOG = "CL";

	public static final String SEQCODE_NAME_CATALOG = "目录";

	public static final String SEQCODE_TYPE_RELATION = "RL";

	public static final String SEQCODE_NAME_RELATION = "表关系";

	public static final String SEQCODE_TYPE_SCRIPT = "SC";

	public static final String SEQCODE_NAME_SCRIPT = "脚本";

	public static final String SEQCODE_TYPE_STYLE = "YS";

	public static final String SEQCODE_NAME_STYLE = "样式";

	public static final String SEQCODE_TYPE_GONGWEN = "GW";

	public static final String SEQCODE_NAME_GONGWEN = "公文";

	// 日期组件编码
	public static final String DATE_MODULE_FORM_SEQCODE = "BMBD100210000020120604122743";
	public static final String DATE_MODULE_LIST_SEQCODE = "BMLB100210000020120604122746";

	// 按钮编码
	public static final String BUTTON_MODULE_FORM_SEQCODE = "BMAN100010000020120604123018";
	public static final String BUTTON_MODULE_LIST_SEQCODE = "BMAN100010000120120604123019";

	// 字段：ID
	public static final String DOMAIN_FIELD_ID = "id";

	// 字段：bianma
	public static final String DOMAIN_FIELD_BIANMA = "bianma";

	// 常量：查询列表方式
	public static final String LIST_TYPE_lISTID = "queryForListID";

	public static final String LIST_TYPE_SEQCODE = "queryForSeqcode";

	public static final int PAGE_PAGECOUNTSHOW = 5;

	public static final String COLUMN_NAME_PREFIX = "tbl_";

	public static final String TABLE_TYPE_TREE = "4";

	/**
	 * 目录表中ID为1的数据,代表"全部目录"
	 */
	public static final String CATALOG_ID_ALL = "1";
	/**
	 * 数据级权限的系统元素中文名称
	 */
	public static final String DATASEARCH_SYSTEMATOM = "查询策略";
	/**
	 * 标示系统元素表中的目录元素代码
	 */
	public static final String SYSTEM_ATOM_FUNC = "FUNC";
	/**
	 * 标示系统元素表中的表单元素代码
	 */
	public static final String SYSTEM_ATOM_FORM = "FORM";
	/**
	 * 标示系统元素表中的列表元素代码
	 */
	public static final String SYSTEM_ATOM_TABN = "TABN";
	/**
	 * 标示系统元素表中的表单按钮元素代码
	 */
	public static final String SYSTEM_ATOM_FORM_BUTN = "FORM_BUTN";
	/**
	 * 标示系统元素表中的列表按钮元素代码
	 */
	public static final String SYSTEM_ATOM_TABN_BUTN = "TABN_BUTN";
	/**
	 * 标示系统元素表中的列表按钮元素代码
	 */
	public static final String SYSTEM_ATOM_OPRT_BUTN = "OPRT_BUTN";
	/**
	 * 标示系统元素表中的选项卡按钮元素代码
	 */
	public static final String SYSTEM_ATOM_ITEM_BUTN = "ITEM_BUTN";

	/**
	 * 标示系统元素表中的字段元素代码
	 */
	public static final String SYSTEM_ATOM_COLN = "COLN";

	/**
	 * 标示系统元素表中的菜单元素代码
	 */
	public static final String SYSTEM_ATOM_MENU = "MENU";

	/**
	 * 标示系统元素表中的选项卡元素代码
	 */
	public static final String SYSTEM_ATOM_ITEM = "ITEM";
	/**
	 * 可读可写
	 */
	public static final String RULE_READ_WRITE = "777";

	/**
	 * 不可读不可写
	 */
	public static final String RULE_NO_READ_WRITE = "444";

	/**
	 * 可读不可写
	 */
	public static final String RULE_READ_NO_WRITE = "750";

	public static final String RESOURCE_TABLES = "sys_liebiaozujian,sys_button,sys_catalog,sys_checkboxmgt,sys_codecasecade,sys_combox,sys_riqizujian,sys_dictionarys,sys_texteditor,sys_liebiaooperation,sys_liebiaoorder,sys_liebiaopagination,sys_passwordbox,sys_chaxunzujian,sys_radiomgt,sys_searchcombox,sys_liebiaoselect,sys_textarea,sys_textbox,sys_uploadfilebox,sys_uploadify,sys_autocomplete";

	public static final String BUSINESS_TABLES = "bpm_type,sys_student";
	
	public static final String THEME_TABLES = "sys_theme,sys_logo,sys_enterpriseinfo";
	
	public static final String JDBC_DEFAULT_SORT_FIELD = "comm_updateDate";
	
	public static final String DEFAULT_SORT_FIELD = "comm_updateDate";

	public static final String SORT_DIRECTION_ASC = "ASC";

	public static final String SORT_DIRECTION_DESC = "DESC";
}
