<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<tr id="templateDesignTr">
	<td class="Input_Table_Label" align="left">
		布局设计
	</td>
	<td colspan="3" align="left" width="90%">
		<table width="650px" cellspacing="0" cellpadding="2" border="0"
			class="Input_Table">
			<tr>
				<td width="15%" rowspan="3">
					表格设计
				</td>
				<td width="80%">
					插入行列：
					<input type="text" id="rowSize" value="0"
						style="width: 70px; height: 15px;" maxlength="100" />
					行
					<input type="text" id="columnSize" value="4"
						style="width: 70px; height: 15px;" maxlength="100" />
					列 &nbsp;&nbsp;在
					<input id="tr_num" size='2' value="0" />
					行后插入
					<font color="red">*</font>
					<br />
				</td>
				<td width="5%">
					<input type="button" id="" name="" class="listbutton" value="确定"
						onclick="setTable()" />
				</td>
			</tr>
			<tr>
				<td>
					表格宽度：
					<input id="tableWidth" style="width: 70px; height: 15px;"
						maxlength="100" value="100" />
					%
					<br />
				</td>
				<td width="5%">
					<input type="button" id="" name="" class="listbutton" value="确定"
						onclick="setTableWidth()" />
				</td>
			</tr>
			<tr>
				<td>
					上下边距：
					<input id="tableHeight" style="width: 70px; height: 15px;"
						maxlength="100" value="0" />
					px
					<br />
				</td>
				<td width="5%">
					<input type="button" id="" name="" class="listbutton" value="确定"
						onclick="setTableHeight()" />
				</td>
			</tr>
			<tr>
				<td>
					表格合并
				</td>
				<td>
					<input type="text" id="rowMergeStart"
						style="width: 70px; height: 15px;" maxlength="100" />
					行
					<input type="text" id="colMergeStart"
						style="width: 70px; height: 15px;" maxlength="100" />
					列 ~
					<input type="text" id="rowMergeEnd"
						style="width: 70px; height: 15px;" maxlength="100" />
					行
					<input type="text" id="colMergeEnd"
						style="width: 70px; height: 15px;" maxlength="100" />
					列
				</td>
				<td>
					<input type="button" id="" name="" class="listbutton" value="确定"
						onclick="setMerge()" />
				</td>
			</tr>
			<tr>
				<td>
					单元格高度
				</td>
				<td>
					<input type="text" id="numHeight"
						style="width: 70px; height: 15px;" maxlength="100" />
					px
				</td>
				<td>
					<input type="button" id="" name="" class="listbutton" value="确定"
						onclick="setHeight()" //>
				</td>
			</tr>
			<tr>
				<td>
					单元格宽度
				</td>
				<td>
					<input type="text" id="numWidth" style="width: 70px; height: 15px;"
						maxlength="100" />
					<select id="widthUnit" name="widthUnit">
						<option value="%">
							%
						</option>
						<option value="px">
							px
						</option>
					</select>
				</td>
				<td>
					<input type="button" id="" name="" class="listbutton" value="确定"
						onclick="setWidth()" //>
				</td>
			</tr>
			<tr>
				<td>
					值设置
				</td>
				<td>
					<select id="labelSet" onchange="labelChange()">
						<option value="数据字段" selected="selected">
							数据字段
						</option>
						<option value="数据值">
							数据值
						</option>
						<option value="自定义文本">
							自定义文本
						</option>
						<option value="自定义样式">
							自定义样式
						</option>
					</select>
					<input type="text" name="cusText" id="cusText" />
					<select id="tdAlign">
						<option value="left" selected="selected">
							左对齐
						</option>
						<option value="center">
							居中
						</option>
						<option value="right">
							右对齐
						</option>
					</select>
				</td>
				<td>
					<input type="button" id="" name="" class="listbutton" value="确定"
						onclick="setTdValue()" //>
				</td>
			</tr>
		</table>
	</td>
</tr>

<tr>
	<td class="Input_Table_Label" align="left">
		模板设计
	</td>
	<td colspan="3" align="left">
		<div style="width: 650px;" id="templateDesignDiv">
			<table id="templateView" cellspacing="0" cellpadding="0" border="0"
				class="Input_Table" align="center" width="100%"
				style="margin: 0px auto;">
			</table>
		</div>

	</td>
</tr>
<tr><td id="templateViewTd" colspan="4"></td></tr>