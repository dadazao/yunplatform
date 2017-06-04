package com.cloudstong.platform.third.bpm.form.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.cloudstong.platform.core.engine.GroovyScriptEngine;
import com.cloudstong.platform.core.keygenerator.IKeyGenerator;
import com.cloudstong.platform.core.keygenerator.impl.GuidGenerator;
import com.cloudstong.platform.core.keygenerator.impl.IdentityGenerator;
import com.cloudstong.platform.core.keygenerator.impl.TimeGenerator;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.FileUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.third.bpm.form.model.BpmFormField;
import com.cloudstong.platform.third.bpm.form.model.BpmFormTable;
import com.cloudstong.platform.third.bpm.form.model.Pair;
import com.cloudstong.platform.third.bpm.form.model.ParseReult;

public class FormUtil {
	public static Map<String, Short> controlTypeMap = new HashMap();

	private static Log logger = LogFactory.getLog(FormUtil.class);

	private static final Pattern regex = Pattern.compile("\\[(.*?)\\]", 98);

	static {
		controlTypeMap.put("textinput", Short.valueOf((short) 1));
		controlTypeMap.put("textarea", Short.valueOf((short) 2));
		controlTypeMap.put("dictionary", Short.valueOf((short) 3));
		controlTypeMap.put("user", Short.valueOf((short) 4));
		controlTypeMap.put("rolepicker", Short.valueOf((short) 5));
		controlTypeMap.put("departmentpicker", Short.valueOf((short) 6));
		controlTypeMap.put("positionpicker", Short.valueOf((short) 7));
		controlTypeMap.put("userMulti", Short.valueOf((short) 8));
		controlTypeMap.put("attachement", Short.valueOf((short) 9));
		controlTypeMap.put("ckeditor", Short.valueOf((short) 10));
		controlTypeMap.put("selectinput", Short.valueOf((short) 11));
		controlTypeMap.put("officecontrol", Short.valueOf((short) 12));
		controlTypeMap.put("checkbox", Short.valueOf((short) 13));
		controlTypeMap.put("radioinput", Short.valueOf((short) 14));
		controlTypeMap.put("datepicker", Short.valueOf((short) 15));
		controlTypeMap.put("hidedomain", Short.valueOf((short) 16));
	}

	public static Object calcuteField(String script, Map<String, Object> map, String colPreFix) {
		GroovyScriptEngine engine = (GroovyScriptEngine) AppUtil.getBean(GroovyScriptEngine.class);
		script = parseScript(script, map, colPreFix);
		return engine.executeObject(script, null);
	}

	private static String parseScript(String script, Map<String, Object> map, String colPreFix) {
		if ((map == null) || (map.size() == 0))
			return script;

		Matcher regexMatcher = regex.matcher(script);
		while (regexMatcher.find()) {
			String tag = regexMatcher.group(0);

			String key = colPreFix + regexMatcher.group(1);
			String value = map.get(key).toString();
			script = script.replace(tag, value);
		}
		return script;
	}

	public static Object getKey(int keyType, String alias) throws Exception {
		IKeyGenerator generator = null;
		switch (keyType) {
		case 1:
			generator = new GuidGenerator();
			break;
		case 2:
			generator = new IdentityGenerator();
			break;
		case 3:
			generator = new TimeGenerator();
		}

		if (generator == null)
			return "-1";
		generator.setAlias(alias);

		return generator.nextId();
	}

	private static Element getContainer(Element node, String containerName) {
		Element parent = node;
		while ((parent = parent.parent()) != null) {
			String name = parent.attr("name");
			if (containerName.equals(name)) {
				return parent;
			}
		}
		return node;
	}

	public static String getFreeMarkerTemplate(String html) {
		Document doc = Jsoup.parseBodyFragment(html);

		parseMainField(doc);

		parseSubTable(doc);

		parseOpinion(doc);

		String rtn = doc.body().html();

		rtn = rtn.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		rtn = "<#setting number_format=\"#\">\n" + rtn;

		return rtn;
	}

	private static void parseMainField(Document doc) {
		Elements list = doc.select("input[name^=m:],textarea[name^=m:],select[name^=m:],a.link");
		for (Iterator it = list.iterator(); it.hasNext();) {
			Element el = (Element) it.next();
			String name = el.attr("name");

			String fieldName = name.replaceAll("^.*:", "").toLowerCase();
			if (StringUtil.isEmpty(name)) {
				name = el.attr("field");

				if ((StringUtil.isNotEmpty(name)) && (name.matches("m:(\\w*):(\\w*)"))) {
					fieldName = name.replaceAll("^.*:", "").toLowerCase();
				}
			}

			parseMainField(el, fieldName);
		}
	}

	private static void parseOpinion(Document doc) {
		parseOpinion(doc, null);
	}

	private static void parseOpinion(Document doc, ParseReult result) {
		Elements list = doc.select("[name^=opinion:]");
		for (Iterator it = list.iterator(); it.hasNext();) {
			Element el = (Element) it.next();
			String name = el.attr("name");
			String memo = el.attr("opinionname");
			String opinionName = name.replaceFirst("^opinion:", "");

			String str = "&lt;#assign " + opinionName + "Opinion&gt;" + el.toString() + " &lt;/#assign&gt;" + "\r\n${service.getOpinion('"
					+ opinionName + "'," + opinionName + "Opinion, model, permission)}";

			el.before(str);
			el.remove();
			if (result != null)
				result.addOpinion(opinionName, memo);
		}
	}

	private static Map<String, Object> handNewRow(Element subTable, Document doc) {
		String tableName = subTable.attr("tableName").toLowerCase();

		subTable.attr("right", "${permission.table." + tableName + "}");

		Elements rows = subTable.select("[formtype=edit],[formtype=form]");
		if (rows.size() == 0) {
			logger.debug("no formtype row defined");
			return null;
		}
		Element row = rows.get(0);

		String name = "s:" + tableName + ":id";

		Element elPkId = doc.createElement("input").attr("type", "hidden").attr("name", name).attr("value", "${table.id}");

		row.appendChild(elPkId);

		String mode = row.attr("formtype");

		Element newRow = row.clone().attr("formtype", "newrow");

		row.after(newRow);

		newRow.before("&lt;#if model.sub." + tableName + " != null&gt; &lt;#list model.sub." + tableName + ".dataList as table&gt;");
		newRow.after("&lt;/#list> &lt;/#if&gt;");

		Map map = new HashMap();
		map.put("newRow", newRow);
		map.put("mode", mode);

		return map;
	}

	private static void parseSubTable(Document doc) {
		Elements list = doc.select("div[type=subtable]");
		for (Iterator it = list.iterator(); it.hasNext();) {
			Element subTable = (Element) it.next();
			String tableName = subTable.attr("tableName").toLowerCase();
			if (StringUtil.isEmpty(tableName)) {
				logger.debug("subtable tableName is not specialed");
			} else {
				Map map = handNewRow(subTable, doc);

				if (map != null) {
					Element newRow = (Element) map.get("newRow");
					String mode = (String) map.get("mode");

					if ("edit".equals(mode)) {
						parseSubTableEditField(newRow);
					} else if ("form".equals(mode)) {
						Elements windowRows = subTable.select("[formtype=window]");
						if (windowRows.size() != 1) {
							logger.debug("window mode hasn't window defined");
							return;
						}
						Element window = windowRows.get(0);
						parseSubTableFormField(doc, newRow, window);
					}
				}
			}
		}
	}

	private static void parseSubTableFormField(Document doc, Element newRow, Element window) {
		Elements fields = newRow.select("[fieldname^=s:]");
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Element el = (Element) it.next();
			String name = el.attr("fieldname");

			String fieldName = name.replaceAll("^.*:", "").toLowerCase();

			el.append("${table." + fieldName + "}");
		}

		Elements windowFields = window.select("[name^=s:]");
		for (Iterator it = windowFields.iterator(); it.hasNext();) {
			Element el = (Element) it.next();
			String name = el.attr("name");

			String fieldName = name.replaceAll("^.*:", "").toLowerCase();
			Element appendTag = doc.createElement("input").attr("type", "hidden").attr("name", name);
			appendTag.attr("value", "${table." + fieldName + "}");
			newRow.children().last().after(appendTag);
		}
	}

	private static void parseSubTableEditField(Element newRow) {
		Elements fields = newRow.select("[name^=s:]");
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Element el = (Element) it.next();
			handSubFieldValuePermission(el);
		}
	}

	private static void handSubFieldValuePermission(Element el) {
		String nodeName = el.nodeName();
		String name = el.attr("name");
		String type = el.attr("type").toLowerCase();

		String fieldName = name.replaceAll("^.*:", "").toLowerCase();

		if ("textarea".equals(nodeName)) {
			el.append("${table." + fieldName + "}");
		} else if (("checkbox".equals(type)) || ("radio".equals(type))) {
			el.attr("chk", "1");
			String value = el.attr("value");
			el.before("${service.getRdoChkBox('" + fieldName + "', '" + el.toString() + "','" + value + "', table)}");
			el.remove();
		} else if ("select".equals(nodeName)) {
			el.attr("val", "${table." + fieldName + "}");
		} else if ("input".equals(nodeName)) {
			el.attr("value", "${table." + fieldName + "}");
		}
	}

	public static String getDesignTemplatePath() {
		return FileUtil.getClassesPath() + "template" + File.separator + "design" + File.separator;
	}

	public static ParseReult parseHtmlNoTable(String html, String tableName, String comment) {
		ParseReult result = new ParseReult();
		Document doc = Jsoup.parseBodyFragment(html);

		List subTableList = parseSubTableHtml(doc, result);

		BpmFormTable mainTable = parseMainTable(doc, tableName, comment, result);

		mainTable.setSubTableList(subTableList);

		result.setBpmFormTable(mainTable);

		parseOpinion(doc, result);

		String template = doc.body().html();
		template = template.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "'");
		template = "<#setting number_format=\"#\">\r\n" + template;

		result.setTemplate(template);
		return result;
	}

	public static void main(String[] args) {
		String str = FileUtil.readFile("E:\\work\\bpm\\src\\main\\webapp\\test.jsp");
		ParseReult map = parseHtmlNoTable(str, "test", "测试表");
		BpmFormTable bpmFormTable = map.getBpmFormTable();
		for (Iterator it = bpmFormTable.getFieldList().iterator(); it.hasNext();) {
			BpmFormField bpmFormField = (BpmFormField) it.next();
			System.out.println(bpmFormField.getFieldName() + "," + bpmFormField.getFieldType());
		}
		String template = map.getTemplate();
		System.out.println(template);
		String options = "中共党员 #newline#中共预备党员#newline#共青团员#newline#民革党员#newline#民盟盟员";
		options = options.replace("#newline#", "\r\n");
		System.out.println(options);
	}

	private static BpmFormTable parseMainTable(Document doc, String tableName, String comment, ParseReult result) {
		BpmFormTable bpmFormTable = new BpmFormTable();

		bpmFormTable.setTableName(tableName);
		bpmFormTable.setTableDesc(comment);
		bpmFormTable.setIsMain(Short.valueOf((short) 1));
		bpmFormTable.setGenByForm(Integer.valueOf(1));

		Elements mainFields = doc.select("[external]");

		if (mainFields.size() == 0) {
			result.addError("主表【" + tableName + "," + comment + "】还未定义任何字段");
			return bpmFormTable;
		}

		for (Iterator it = mainFields.iterator(); it.hasNext();) {
			Element el = (Element) it.next();

			BpmFormField bpmFormField = parseExternal(el, result);

			if (bpmFormField != null) {
				String controlName = "m:" + tableName + ":" + bpmFormField.getFieldName();

				boolean rtn = bpmFormTable.addField(bpmFormField);
				if (!rtn) {
					result.addError("表单中主表:【" + tableName + "】，字段:【" + bpmFormField.getFieldName() + "】重复");
				} else {
					List list = parseChilds(doc, el, controlName, bpmFormField);

					for (Iterator tmpIt = list.iterator(); tmpIt.hasNext();) {
						Pair pair = (Pair) tmpIt.next();
						String error = parseMainField(pair.getEl(), pair.getFieldName());
						if (StringUtil.isNotEmpty(error)) {
							result.addError(error);
						}
					}

					removeWrap(el);
				}
			}
		}
		return bpmFormTable;
	}

	private static List<Pair> parseChilds(Document doc, Element parentEl, String controlName, BpmFormField bpmFormField) {
		List list = new ArrayList();
		Elements elList = parentEl.select("input,select,textarea");
		for (Iterator it = elList.iterator(); it.hasNext();) {
			Element el = (Element) it.next();
			Pair pair = new Pair(el, bpmFormField.getFieldName());
			list.add(pair);
			short controlType = bpmFormField.getControlType().shortValue();
			String fieldType = bpmFormField.getFieldType();
			el.attr("name", controlName);

			String valid = handValidate(bpmFormField);
			if (StringUtil.isNotEmpty(valid)) {
				el.attr("validate", valid);
			}
			if (BeanUtils.isNotEmpty(bpmFormField.getStyle())) {
				el.attr("style", bpmFormField.getStyle());
			}
			switch (controlType) {
			case 1:
				el.wrap("<span></span>");
				if ("varchar".equals(fieldType) ? 1 == bpmFormField.getIsDateString().shortValue() : "date".equals(fieldType)) {
					el.addClass("Wdate");
					Map optionsMap = bpmFormField.getPropertyMap();
					String dateformat = "yyyy-MM-dd";
					if (optionsMap.containsKey("format")) {
						dateformat = (String) optionsMap.get("format");
					}
					el.attr("dateFmt", dateformat);
				}
				break;
			case 3:
				el.attr("class", "dicComboTree");
				el.attr("nodeKey", bpmFormField.getDictType());
				el.wrap("<span></span>");
				break;
			case 4:
			case 5:
			case 6:
			case 7:
				el.wrap("<span></span>");
				Element elInput = doc.createElement("input").attr("name", controlName + "ID").attr("type", "hidden").attr("class", "hidden");

				el.before(elInput);

				Pair pairSelect = new Pair(elInput, bpmFormField.getFieldName() + "ID");
				list.add(pairSelect);

				el.attr("readonly", "readonly");
				Elements links = parentEl.select("a.link");
				if (links.size() > 0) {
					Element link = links.get(0);
					link.attr("name", controlName);
					el.after(link);
					Pair pLink = new Pair(link, bpmFormField.getFieldName());
					list.add(pLink);
				}
				break;
			case 8:
				el.wrap("<span></span>");
				Element elUsers = doc.createElement("input").attr("name", controlName + "ID").attr("type", "hidden").attr("class", "hidden");

				Pair pairUsers = new Pair(elUsers, bpmFormField.getFieldName() + "ID");
				list.add(pairUsers);

				el.before(elUsers);

				el.attr("readonly", "readonly");
				Elements linkUsers = parentEl.select("a.link");
				if (linkUsers.size() > 0) {
					Element link = linkUsers.get(0);
					link.attr("name", controlName);
					link.removeClass("user");
					link.addClass("users");
					el.after(link);
					Pair pLink = new Pair(link, bpmFormField.getFieldName());
					list.add(pLink);
				}
				break;
			case 9:
				el.tagName("textarea");
				el.removeAttr("type");

				el.wrap("<div name='div_attachment_container' ></div>");
				el.before("<div  class='attachement' ></div>");
				el.attr("controltype", "attachment");
				el.attr("style", "display:none;");

				Elements linkFile = parentEl.select("a.attachement");
				if (linkFile.size() > 0) {
					Element link = linkFile.get(0);
					link.attr("field", controlName);
					link.attr("onclick", "AttachMent.addFile(this);");
					el.after(link);
					el.removeClass("attachement").addClass("selectFile");
				} else {
					el.after("<a href='#' field='" + controlName + "' class='link selectFile' onclick='AttachMent.addFile(this);'>选择</a>");
				}
				break;
			case 10:
				el.attr("class", "ckeditor");
				break;
			case 13:
			case 14:
				break;
			case 12:
				el.attr("type", "hidden");
				el.attr("class", "hidden");
				el.attr("controltype", "office");

				String html = "<div id='div_" + controlName.replace(":", "_") + "'";
				String style = el.attr("style");
				if (StringUtil.isNotEmpty(style)) {
					html = html + " style='" + style + "' ";
				}
				html = html + " class='office-div'></div>";
				el.after(html);

				break;
			case 15:
				el.wrap("<span></span>");
				el.addClass("Wdate");
				Map map = bpmFormField.getPropertyMap();
				String format = "yyyy-MM-dd";
				if (map.containsKey("format")) {
					format = (String) map.get("format");
				}
				el.attr("dateFmt", format);
				break;
			case 16:
				el.wrap("<span></span>");
				el.attr("type", "hidden");
				break;
			case 2:
			case 11:
			default:
				el.wrap("<span></span>");
			}
		}

		return list;
	}

	private static void removeWrap(Element parentEl) {
		for (Iterator it = parentEl.children().iterator(); it.hasNext();) {
			Node elClone = (Node) it.next();
			parentEl.before(elClone);
		}
		parentEl.remove();
	}

	private static String parseMainField(Element el, String fieldName) {
		String controltype = el.attr("controltype");

		String type = el.attr("type").toLowerCase();

		if ("attachment".equalsIgnoreCase(controltype)) {
			Element parent = getContainer(el, "div_attachment_container");
			parent.attr("right", "${service.getFieldRight('" + fieldName + "',  permission)}");

			el.val("${service.getFieldValue('" + fieldName + "',model)}");
		} else if ("office".equalsIgnoreCase(controltype)) {
			el.attr("value", "${service.getFieldValue('" + fieldName + "',model)}");

			el.attr("right", "${service.getFieldRight('" + fieldName + "',  permission)}");
		} else if (("checkbox".equalsIgnoreCase(type)) || ("radio".equalsIgnoreCase(type))) {
			String value = el.attr("value");

			el.attr("chk", "1").attr("disabled", "disabled");
			Element elParent = el.parent();
			String parentNodeName = elParent.nodeName();
			if (!parentNodeName.equals("label")) {
				return fieldName + "的html代码必须为<label><input type='checkbox|radio' value='是'/>是</label>的形式";
			}

			String tmp = parentNodeName.equals("label") ? elParent.toString() : el.toString();

			String str = "<span>&lt;#assign " + fieldName + "Html&gt;" + tmp + " &lt;/#assign&gt;" + "\r\n${service.getRdoChkBox('" + fieldName
					+ "', " + fieldName + "Html,'" + value + "', model, permission)}</span>";
			elParent.before(str);
			elParent.remove();
		} else if (el.nodeName().equalsIgnoreCase("textarea")) {
			el.append("#value");
			String str = "<span>&lt;#assign " + fieldName + "Html&gt;" + el.toString() + " &lt;/#assign&gt;" + "\r\n${service.getField('" + fieldName
					+ "'," + fieldName + "Html, model, permission)}</span>";
			el.before(str);
			el.remove();
		} else if (el.nodeName().equalsIgnoreCase("input")) {
			el.attr("value", "#value");
			String str = "&lt;#assign " + fieldName + "Html&gt;" + el.toString() + " &lt;/#assign&gt;" + "\r\n${service.getField('" + fieldName
					+ "'," + fieldName + "Html, model, permission)}";

			if ("hidden".equalsIgnoreCase(type)) {
				str = "&lt;#assign " + fieldName + "Html&gt;" + el.toString() + " &lt;/#assign&gt;" + "\r\n${service.getHiddenField('" + fieldName
						+ "'," + fieldName + "Html, model, permission)}";
			}
			el.before(str);
			el.remove();
		} else if (el.nodeName().equalsIgnoreCase("select")) {
			el.attr("val", "#value");
			String str = "&lt;#assign " + fieldName + "Html&gt;" + el.toString() + " &lt;/#assign&gt;" + "\r\n${service.getField('" + fieldName
					+ "'," + fieldName + "Html, model, permission)}";
			el.before(str);
			el.remove();
		} else if (el.nodeName().equalsIgnoreCase("a")) {
			String str = "&lt;#assign " + fieldName + "Html&gt;" + el.toString() + " &lt;/#assign&gt;" + "\r\n${service.getLink('" + fieldName + "',"
					+ fieldName + "Html, model, permission)}";
			el.before(str);
			el.remove();
		}
		return "";
	}

	private static List<BpmFormTable> parseSubTableHtml(Document doc, ParseReult result) {
		List subList = new ArrayList();
		Elements list = doc.select("div[type=subtable]");

		for (Iterator it = list.iterator(); it.hasNext();) {
			Element subTable = (Element) it.next();

			if (subTable.hasAttr("external")) {
				subTable.removeAttr("external");
				subTable.removeClass("subtable");
			}

			BpmFormTable table = new BpmFormTable();

			String tableName = subTable.attr("tablename").toLowerCase();
			if (StringUtil.isEmpty(tableName)) {
				result.addError("有子表对象没有设置表名。");
			} else {
				String comment = subTable.attr("tabledesc");

				table.setTableName(tableName);
				table.setTableDesc(comment);
				table.setIsMain(Short.valueOf((short) 0));
				table.setGenByForm(Integer.valueOf(1));
				subList.add(table);

				subTable.attr("right", "${service.getSubTablePermission('" + tableName + "', permission)}");

				Elements rows = subTable.select("[formtype=edit],[formtype=form]");
				if (rows.size() == 0) {
					logger.debug("no formtype row defined");
					result.addError("子表【" + tableName + "】没有定义属性【formtype】。");
				} else {
					Element row = rows.get(0);

					String mode = row.attr("formtype");

					if ("edit".equals(mode)) {
						parseSubTableEditField(doc, row, table, result);
					}

					row.appendElement("input").attr("name", "s:" + tableName + ":id").attr("type", "hidden").attr("value", "${table.id}");

					Element newRow = row.clone().attr("formtype", "newrow");

					row.after(newRow);

					newRow.before("&lt;#if model.sub." + tableName + " != null&gt; &lt;#list model.sub." + tableName + ".dataList as table&gt;");
					newRow.after("&lt;/#list> &lt;/#if&gt;");

					if ("edit".equals(mode)) {
						parseSubTableEditField(newRow);
					} else if ("form".equals(mode)) {
						Elements windowRows = subTable.select("[formtype=window]");
						if (windowRows.size() != 1) {
							logger.debug("window mode hasn't window defined");
							result.addError("在弹出窗口模式下，子表【" + tableName + "】没有设置window属性的对象。");
						} else {
							Element window = windowRows.get(0);
							parseSubTableFormField(doc, row, newRow, window, table, result);
						}
					}
				}
			}
		}
		return subList;
	}

	private static void parseSubTableFormField(Document doc, Element row, Element newRow, Element window, BpmFormTable table, ParseReult result) {
		String tableName = table.getTableName();
		Elements rowFields = row.select("[fieldname]");
		if (rowFields.size() == 0) {
			result.addError("表:" + table.getTableName() + "," + table.getTableDesc() + ",弹窗编辑模式，显示行没有定义任何字段");
			return;
		}

		for (Iterator it = rowFields.iterator(); it.hasNext();) {
			Element el = (Element) it.next();
			String fieldname = el.attr("fieldname").toLowerCase();

			el.attr("fieldname", "s:" + tableName + ":" + fieldname);
		}

		Elements newRowfields = newRow.select("[fieldname]");
		for (Iterator it = newRowfields.iterator(); it.hasNext();) {
			Element el = (Element) it.next();
			String fieldname = el.attr("fieldname").toLowerCase();

			el.attr("fieldname", "s:" + tableName + ":" + fieldname);

			el.append("${table." + fieldname + "}");
		}

		Elements windowFields = window.select("[external]");

		if (rowFields.size() == 0) {
			result.addError("表:" + table.getTableName() + "," + table.getTableDesc() + ",弹窗编辑模式，窗口没有定义任何字段");
			return;
		}
		for (Iterator it = windowFields.iterator(); it.hasNext();) {
			Element el = (Element) it.next();

			BpmFormField bpmFormField = parseExternal(el, result);
			if (bpmFormField != null) {
				boolean rtn = table.addField(bpmFormField);

				String fieldName = bpmFormField.getFieldName();

				if (!rtn) {
					result.addError("表单中子表表:【" + tableName + "】，字段:【" + fieldName + "】重复!");
				} else {
					String name = "s:" + tableName + ":" + fieldName;
					Element appendTag = doc.createElement("input").attr("type", "hidden").attr("name", name)
							.attr("value", "${table." + fieldName + "}");
					newRow.children().last().after(appendTag);

					parseChilds(doc, el, name, bpmFormField);

					removeWrap(el);
				}
			}
		}
	}

	private static void parseSubTableEditField(Document doc, Element newRow, BpmFormTable table, ParseReult result) {
		Elements fields = newRow.select("[external]");
		String tableName = table.getTableName();
		if (fields.size() == 0) {
			result.addError("子表【" + tableName + "," + table.getTableDesc() + "】尚未定义任何字段");
			return;
		}
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Element el = (Element) it.next();

			BpmFormField bpmFormField = parseExternal(el, result);

			boolean rtn = table.addField(bpmFormField);

			String fieldName = bpmFormField.getFieldName();

			if (!rtn) {
				result.addError("表单中子表表:【" + tableName + "】，字段:【" + fieldName + "】重复!");
			} else {
				String controlName = "s:" + tableName + ":" + fieldName;
				parseChilds(doc, el, controlName, bpmFormField);

				removeWrap(el);
			}
		}
	}

	private static BpmFormField parseExternal(Element el, ParseReult result) {
		BpmFormField bpmFormField = new BpmFormField();

		String external = el.attr("external").replace("&#39;", "\"");

		el.removeAttr("external");
		JSONObject jsonObject = null;
		try {
			jsonObject = JSONObject.fromObject(external);
		} catch (Exception ex) {
			result.addError(external + "错误的JSON格式!");
			return null;
		}

		String fieldName = jsonObject.getString("name");

		if (StringUtil.isEmpty(fieldName)) {
			result.addError(external + "没有定义字段名");
			return null;
		}

		bpmFormField.setFieldName(fieldName);

		JSONObject dbType = (JSONObject) jsonObject.get("dbType");

		if (dbType == null) {
			result.addError(fieldName + ",没有定义字段类型。");
			return null;
		}

		handFieldType(dbType, bpmFormField, result);

		String comment = (String) jsonObject.get("comment");
		bpmFormField.setFieldDesc(comment == null ? fieldName : comment);

		handStyle(jsonObject, bpmFormField);

		handDictType(jsonObject, bpmFormField);

		handValueFrom(jsonObject, bpmFormField);

		handOption(jsonObject, bpmFormField);

		handCondition(jsonObject, bpmFormField);

		short controlType = handControlType(el, jsonObject);

		bpmFormField.setControlType(Short.valueOf(controlType));

		handOptions(jsonObject, bpmFormField);

		handFormUser(jsonObject, bpmFormField);

		handShowCurUser(jsonObject, bpmFormField);

		handShowCurOrg(jsonObject, bpmFormField);

		return bpmFormField;
	}

	private static void handOptions(JSONObject jsonObject, BpmFormField bpmFormField) {
		if ((bpmFormField.getControlType().shortValue() == 11) || (bpmFormField.getControlType().shortValue() == 13)
				|| (bpmFormField.getControlType().shortValue() == 14)) {
			Object objOptions = jsonObject.get("options");
			if (objOptions == null)
				return;
			String options = objOptions.toString();
			options = options.replace("#newline#", "\r\n");
			bpmFormField.setOptions(options);
		}
	}

	private static void handFormUser(JSONObject jsonObject, BpmFormField bpmFormField) {
		if ((bpmFormField.getControlType().shortValue() == 4) || (bpmFormField.getControlType().shortValue() == 6)
				|| (bpmFormField.getControlType().shortValue() == 8)) {
			Object isformuser = jsonObject.get("isformuser");
			if (isformuser == null)
				return;
			String ctlProperty = bpmFormField.getCtlProperty();
			JSONObject jsonObj = null;
			if (StringUtil.isNotEmpty(ctlProperty)) {
				jsonObj = JSONObject.fromObject(ctlProperty);
			} else {
				jsonObj = JSONObject.fromObject("{}");
			}
			jsonObj.element("isformuser", isformuser);
			bpmFormField.setCtlProperty(jsonObj.toString());
		}
	}

	private static void handShowCurUser(JSONObject jsonObject, BpmFormField bpmFormField) {
		if (bpmFormField.getControlType().shortValue() == 4) {
			Object showCurUser = jsonObject.get("showCurUser");
			if (showCurUser == null)
				return;
			String ctlProperty = bpmFormField.getCtlProperty();
			JSONObject jsonObj = null;
			if (StringUtil.isNotEmpty(ctlProperty)) {
				jsonObj = JSONObject.fromObject(ctlProperty);
			} else {
				jsonObj = JSONObject.fromObject("{}");
			}
			jsonObj.element("showCurUser", showCurUser);
			bpmFormField.setCtlProperty(jsonObj.toString());
		}
	}

	private static void handShowCurOrg(JSONObject jsonObject, BpmFormField bpmFormField) {
		if (bpmFormField.getControlType().shortValue() == 6) {
			Object showCurOrg = jsonObject.get("showCurOrg");
			if (showCurOrg == null)
				return;
			String ctlProperty = bpmFormField.getCtlProperty();
			JSONObject jsonObj = null;
			if (StringUtil.isNotEmpty(ctlProperty)) {
				jsonObj = JSONObject.fromObject(ctlProperty);
			} else {
				jsonObj = JSONObject.fromObject("{}");
			}
			jsonObj.element("showCurOrg", showCurOrg);
			bpmFormField.setCtlProperty(jsonObj.toString());
		}
	}

	private static short handControlType(Element el, JSONObject jsonObject) {
		String clsName = el.attr("class").toLowerCase();
		Short controlType = (Short) controlTypeMap.get("textinput");
		if (clsName.equals("personpicker")) {
			if (jsonObject.containsKey("singleselect")) {
				if (jsonObject.get("singleselect").toString().equals("1")) {
					controlType = (Short) controlTypeMap.get("user");
				} else {
					controlType = (Short) controlTypeMap.get("userMulti");
				}
			} else {
				controlType = (Short) controlTypeMap.get("user");
			}

		} else if (controlTypeMap.containsKey(clsName)) {
			controlType = (Short) controlTypeMap.get(clsName);
		}

		return controlType.shortValue();
	}

	private static String handValidate(BpmFormField bpmFormField) {
		JSONObject valid = JSONObject.fromObject("{}");

		if (bpmFormField.getIsRequired().shortValue() == 1) {
			valid.put("required", Boolean.valueOf(true));
		}

		if (StringUtil.isNotEmpty(bpmFormField.getValidRule())) {
			valid.put(bpmFormField.getValidRule(), Boolean.valueOf(true));
		}

		String fieldType = bpmFormField.getFieldType();
		if ("varchar".equals(fieldType)) {
			valid.put("maxlength", bpmFormField.getCharLen());
		} else if ("date".equals(fieldType)) {
			valid.put("date", Boolean.valueOf(true));
		} else if ("number".equals(fieldType)) {
			valid.put("number", Boolean.valueOf(true));
			valid.put("maxIntLen", bpmFormField.getIntLen());
			valid.put("maxDecimalLen", bpmFormField.getDecimalLen());
		}

		return valid.toString();
	}

	private static void handDictType(JSONObject jsonObject, BpmFormField bpmFormField) {
		String dictType = (String) jsonObject.get("dictType");
		if (StringUtil.isEmpty(dictType))
			return;

		bpmFormField.setDictType(dictType);
	}

	private static void handStyle(JSONObject jsonObject, BpmFormField bpmFormField) {
		StringBuffer style = new StringBuffer();
		Object objWidthOptions = jsonObject.get("width");
		Object objHeightOptions = jsonObject.get("height");
		Object objWidthUnit = jsonObject.get("widthUnit");
		Object objHeightUnit = jsonObject.get("heightUnit");
		if (BeanUtils.isEmpty(objWidthUnit)) {
			objWidthUnit = "px";
		}
		if (BeanUtils.isEmpty(objHeightUnit)) {
			objHeightUnit = "px";
		}
		if (BeanUtils.isNotEmpty(objWidthOptions)) {
			style.append("width:" + objWidthOptions + objWidthUnit + ";");
		}
		if (BeanUtils.isNotEmpty(objHeightOptions)) {
			style.append("height:" + objHeightOptions + objHeightUnit + ";");
		}
		bpmFormField.setStyle(style.toString());
	}

	private static void handValueFrom(JSONObject jsonObject, BpmFormField bpmFormField) {
		JSONObject valueFrom = (JSONObject) jsonObject.get("valueFrom");
		if (valueFrom == null)
			return;

		Short value = Short.valueOf(Short.parseShort(valueFrom.get("value").toString()));
		bpmFormField.setValueFrom(value);
		switch (value.shortValue()) {
		case 0:
			if (valueFrom.containsKey("content")) {
				String validRule = valueFrom.getString("content");
				if (StringUtil.isNotEmpty(validRule)) {
					bpmFormField.setValidRule(validRule);
				}
			}
			break;
		case 1:
		case 2:
			String content = valueFrom.getString("content");
			bpmFormField.setScript(content);
			break;
		case 3:
			String identity = valueFrom.getString("content");
			bpmFormField.setIdentity(identity);
		}
	}

	private static void handCondition(JSONObject jsonObject, BpmFormField bpmFormField) {
		if (bpmFormField.getIsQuery().shortValue() != 1)
			return;

		JSONObject obj = (JSONObject) jsonObject.get("search");

		if (obj == null) {
			bpmFormField.setIsQuery(Short.valueOf((short) 0));
		}
		JSONObject jsonObj = null;
		String ctlProperty = bpmFormField.getCtlProperty();
		if (StringUtil.isNotEmpty(ctlProperty)) {
			jsonObj = JSONObject.fromObject(ctlProperty);
		} else {
			jsonObj = JSONObject.fromObject("{}");
		}
		String searchFrom = obj.getString("searchFrom");
		String searchValue = "";
		int condValFrom = 1;
		if (searchFrom.equalsIgnoreCase("fromStatic")) {
			condValFrom = 2;
			searchValue = obj.getString("searchValue");
		} else if (searchFrom.equalsIgnoreCase("fromScript")) {
			condValFrom = 3;
			searchValue = obj.getString("searchValue");
		}

		jsonObj.element("condition", obj.getString("condition"));
		jsonObj.element("condValFrom", condValFrom);
		jsonObj.element("condValue", searchValue);

		bpmFormField.setCtlProperty(jsonObj.toString());
	}

	private static void handOption(JSONObject jsonObject, BpmFormField bpmFormField) {
		Object isRequired = jsonObject.get("isRequired");
		if (isRequired == null) {
			bpmFormField.setIsRequired(Short.valueOf((short) 0));
		} else {
			bpmFormField.setIsRequired(Short.valueOf(Short.parseShort(isRequired.toString())));
		}

		Object isList = jsonObject.get("isList");
		if (isList == null) {
			bpmFormField.setIsList(Short.valueOf((short) 0));
		} else {
			bpmFormField.setIsList(Short.valueOf(Short.parseShort(isList.toString())));
		}

		Object isQuery = jsonObject.get("isQuery");
		if (isQuery == null) {
			bpmFormField.setIsQuery(Short.valueOf((short) 0));
		} else {
			bpmFormField.setIsQuery(Short.valueOf(Short.parseShort(isQuery.toString())));
		}

		Object isFlowVar = jsonObject.get("isFlowVar");
		if (isFlowVar == null) {
			bpmFormField.setIsFlowVar(Short.valueOf((short) 0));
		} else {
			bpmFormField.setIsFlowVar(Short.valueOf(Short.parseShort(isFlowVar.toString())));
		}
		Object isAllowMobile = jsonObject.get("isAllowMobile");
		if (isAllowMobile == null)
			bpmFormField.setIsAllowMobile(Short.valueOf((short) 0));
		else {
			bpmFormField.setIsAllowMobile(Short.valueOf(Short.parseShort(isAllowMobile.toString())));
		}

		Object isDateString = jsonObject.get("isDateString");
		if (("varchar".equals(bpmFormField.getFieldType())) && (isDateString != null)) {
			bpmFormField.setIsDateString(Short.valueOf(Short.parseShort(isDateString.toString())));
			String displayDate = (String) bpmFormField.getPropertyMap().get("displayDate");
			if (displayDate != null)
				bpmFormField.setIsCurrentDateStr(Short.valueOf(Short.parseShort(displayDate)));
		}
	}

	private static void handFieldType(JSONObject dbType, BpmFormField bpmFormField, ParseReult result) {
		if (!dbType.containsKey("type")) {
			result.addError("字段:" + bpmFormField.getFieldName() + "," + bpmFormField.getFieldDesc() + ",没有设置数据类型!");
			return;
		}

		String type = dbType.getString("type");
		if (StringUtil.isEmpty(type)) {
			result.addError("字段:" + bpmFormField.getFieldName() + "," + bpmFormField.getFieldDesc() + ",没有设置数据类型!");
			return;
		}
		if (!isValidType(type)) {
			result.addError("字段:" + bpmFormField.getFieldName() + "," + bpmFormField.getFieldDesc() + ",数据类型设置错误:" + type);
			return;
		}

		bpmFormField.setCharLen(Integer.valueOf(0));
		bpmFormField.setIntLen(Integer.valueOf(0));
		bpmFormField.setDecimalLen(Integer.valueOf(0));

		bpmFormField.setFieldType(type);
		if ("varchar".equals(type)) {
			if (!dbType.containsKey("length")) {
				result.addError("字段:" + bpmFormField.getFieldName() + "," + bpmFormField.getFieldDesc() + ",数据类型(VARCHAR)长度未设置。");
				return;
			}

			int len = dbType.getInt("length");

			bpmFormField.setCharLen(Integer.valueOf(len));

			setDateFormat(dbType, bpmFormField);
		} else if ("number".equals(type)) {
			if (!dbType.containsKey("intLen")) {
				result.addError("字段:" + bpmFormField.getFieldName() + "," + bpmFormField.getFieldDesc() + ",数据类型(number)数据长度未设置。");
				return;
			}
			int intLen = dbType.getInt("intLen");
			int decimalLen = 0;

			if (dbType.containsKey("decimalLen")) {
				decimalLen = dbType.getInt("decimalLen");
			}

			bpmFormField.setIntLen(Integer.valueOf(intLen));
			bpmFormField.setDecimalLen(Integer.valueOf(decimalLen));
		} else if ("date".equals(type)) {
			setDateFormat(dbType, bpmFormField);
		}
	}

	private static boolean isValidType(String type) {
		if ((type.equals("varchar")) || (type.equals("number")) || (type.equals("date")) || (type.equals("clob"))) {
			return true;
		}
		return false;
	}

	private static void setDateFormat(JSONObject dbType, BpmFormField bpmFormField) {
		String ctlProperty = bpmFormField.getCtlProperty();
		JSONObject jsonObj = null;
		if (StringUtil.isNotEmpty(ctlProperty)) {
			jsonObj = JSONObject.fromObject(ctlProperty);
		} else {
			jsonObj = JSONObject.fromObject("{}");
		}

		String format = (String) dbType.get("format");
		String dateStrFormat = (String) dbType.get("dateStrFormat");
		if ((format == null) && (dateStrFormat == null)) {
			jsonObj.element("format", "yyyy-MM-dd");
		} else if (format != null)
			jsonObj.element("format", format);
		else if (dateStrFormat != null) {
			jsonObj.element("format", dateStrFormat);
		}

		Object displayDate = dbType.get("displayDate");
		if (displayDate == null) {
			jsonObj.element("displayDate", 0);
		} else {
			jsonObj.element("displayDate", Integer.parseInt(displayDate.toString()));
		}
		bpmFormField.setCtlProperty(jsonObj.toString());
	}

	public static Map<String, String> parseOpinion(String html) {
		Map map = new HashMap();
		Document doc = Jsoup.parseBodyFragment(html);
		Elements list = doc.select("[name^=opinion:]");
		for (Iterator it = list.iterator(); it.hasNext();) {
			Element el = (Element) it.next();
			String name = el.attr("name");
			String memo = el.attr("opinionname");
			String opinionName = name.replaceFirst("^opinion:", "");
			map.put(opinionName, memo);
		}
		return map;
	}
}