<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<%@include file="/pages/resource/compexDomainEdit.jsp"%>

<script type="text/javascript">
<!--
	$(function(){
		//修改默认的保存方法
		$('#BC').attr('onclick', 'eventCombTemplateBC()');
		//添加表单模版的所属类型,模版的行数\列数
		$('#tabDivId form').append('<input type="hidden" value="1" name="sys_template-tbl_type"/>');
		$('#tabDivId form').append('<input type="hidden" value="0" name="sys_template-tbl_templatechname"/>');
		$('#tabDivId form').append('<input type="hidden" value="0" id="sys_template-tbl_tds" name="sys_template-tbl_tds"/>');
		$('#tabDivId form').append('<input type="hidden" value="0" id="sys_template-tbl_trs" name="sys_template-tbl_trs"/>');
		//模版来源默认为"自定义"
		//$("input[name='sys_template-tbl_designType']").each(function() {
		//	if (this.value == '1') { 
		//		this.checked = true;
		//	}
		//});
		//将自定义代码放入指定区域
		$('#tabDivId table tbody tr').eq(0).after($('#tbl_designType-1 tbody').html());
		//删除自定义代码
		$('#tbl_designType-1').html('');
		//初始化模版设计视图
		templateInit();
		viewContent();
		addPartitionPage();
		formbzUrl = "<%=basePath %>/pages/resource/compexshowFormHelp.action?formId=${formId}";
		ns.common.mouseForButton();
	});
	
//-->
</script>

<table id="tbl_designType-1" style="display: none">
	<tbody>
		<tr id="templateDesignTr">
			<td class="Input_Table_Label" align="left" width="10%">
				布局设计
			</td>
			<td colspan="3" align="left" width="90%">
				<table width="650px" cellspacing="0" cellpadding="2" border="0"
					class="Input_Table">
					<tr>
						<td>
							表格
						</td>
						<td>
							<input type="text" id="rowSize" name="templateAtom_tds"
								style="width: 100px; height: 15px;" maxlength="100" />
							行
							<input type="text" id="columnSize" name="templateAtom_tds"
								style="width: 100px; height: 15px;" maxlength="100" />
							列
						</td>
						<td>
							<input type="button" id="" name="" class="listbutton" value="确定"
								onclick="setTable()" />
						</td>
					</tr>
					<tr>
						<td>
							合并
						</td>
						<td>
							<input type="text" id="rowMergeStart"
								style="width: 100px; height: 15px;" maxlength="100" />
							行
							<input type="text" id="colMergeStart"
								style="width: 100px; height: 15px;" maxlength="100" />
							列 ~
							</br>
							</br>
							<input type="text" id="rowMergeEnd"
								style="width: 100px; height: 15px;" maxlength="100" />
							行
							<input type="text" id="colMergeEnd"
								style="width: 100px; height: 15px;" maxlength="100" />
							列
						</td>
						<td>
							<input type="button" id="" name="" class="listbutton" value="确定"
								onclick="setMerge()" />
						</td>
					</tr>
					<tr>
						<td>
							高度
						</td>
						<td title="如不设置行数,默认为设置所有行的高度">
							<input type="text" id="rowHeight"
								style="width: 100px; height: 15px;" maxlength="100" />
							行
							<input type="text" id="numHeight"
								style="width: 100px; height: 15px;" maxlength="100" />
							<select id="heightUnit" name="heightUnit">
								<option value="px">
									像素
								</option>
								<option value="%">
									百分比
								</option>
							</select>
						</td>
						<td>
							<input type="button" id="" name="" class="listbutton" value="确定"
								onclick="setHeight()" //>
						</td>
					</tr>
					<tr>
						<td>
							宽度
						</td>
						<td
							title="如不设置行列数,默认为设置所有列的宽度;如果不设置行数,默认为设置此列所有行宽度;如不设置列数,默认为设置行总宽度;">
							<input type="text" id="rowWidth"
								style="width: 100px; height: 15px;" maxlength="100" />
							行
							<input type="text" id="columnWidth"
								style="width: 100px; height: 15px;" maxlength="100" />
							列
							<input type="text" id="numWidth"
								style="width: 100px; height: 15px;" maxlength="100" />
							<select id="widthUnit" name="widthUnit">
								<option value="px">
									像素
								</option>
								<option value="%">
									百分比
								</option>
							</select>
						</td>
						<td>
							<input type="button" id="" name="" class="listbutton" value="确定"
								onclick="setWidth()" //>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr id="templateDesign1Tr">
			<td class="Input_Table_Label" align="left" width="10%">
				模板设计
			</td>
			<td colspan="3" align="left">
				<table id="templateView1" cellspacing="0" cellpadding="0" border="0"
					class="Input_Table" align="left" width="650px">
				</table>
			</td>
		</tr>
		<tr id="templateViewTr">
			<td class="Input_Table_Label" align="left" width="10%">
				模板预览
			</td>
			<td colspan="3" align="left">
				<table id="templateView2" cellspacing="0" cellpadding="0" border="0"
					class="Input_Table" align="left" width="100%">
				</table>
			</td>
		</tr>
	</tbody>
</table>