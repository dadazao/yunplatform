<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>

<script type="text/javascript">
<!--
	$(function(){
		var urlString = "<%=basePath%>/pages/resource/buttonActiondataJson.action?params=${params}&formId=${formId}";
		//生成查看/修改页面
		dataJson(urlString);
		//删除ACTION URL
		scUrl = "<%=basePath%>/pages/resource/buttonActionsingleDelete.action?params=${params}";
		//保存ACTION URL
		bcUrl = "<%=basePath%>/pages/resource/buttonActionsave.action";
		//将修改按钮置灰
		$("#XG").attr("disabled","disabled");
		$("#XG").attr("class","listbuttonDisable");
		
		//新建按钮，设置默认值
		var bName = $("#buttonNameId").val();
		if(bName == "") {
			document.getElementById("buttonNameFontSizeId").value = "12";
			document.getElementById("buttonBorderSizeId").value = "1";
			document.getElementById("buttonWidthId").value = "60";
			document.getElementById("buttonHeightId").value = "21";
			document.getElementById("buttonNameFontColorId").value = "#000000";
			document.getElementById("buttonBackGroundColorId").value = "#FFFFFF";
			document.getElementById("buttonBorderColorId").value = "#000000";
		}
		
		initButtonCssStyle();
		
		$("#buttonNameId").blur(function(){
			onButtonNameValue();
		});
		$("#buttonNameFontStyleId").change(function(){
			onChangeButtonStyle("buttonNameFontStyleId","fontFamily");
		});
		$("#buttonNameFontSizeId").blur(function(){
			onChangeButtonStyle("buttonNameFontSizeId","fontSize");
		});
		$("#buttonNameFontColorId").change(function(){
			onChangeButtonStyle("buttonNameFontColorId","color");
		});
		$("#buttonWidthId").blur(function(){
			onChangeButtonStyle("buttonWidthId","width");
		});
		$("#buttonHeightId").blur(function(){
			onChangeButtonStyle("buttonHeightId","height");
		});
		$("#buttonBorderSizeId").blur(function(){
			onChageButtonBorderSize();
		});
		$("#buttonBorderColorId").change(function(){
			onChageButtonBorderColor();
		});
		$("#buttonBackGroundColorId").change(function(){
			onChageButtonBackGroundColor();
		});
	});

	function initButtonCssStyle(){
		$('#btn').attr("value",$("#buttonNameId").val());
		$('#btn').css("fontFamily",$("#buttonNameFontStyleId").val());
		$('#btn').css("fontSize",$("#buttonNameFontSizeId").val());
		$('#btn').css("color",$("#buttonNameFontColorId").val());
		$('#btn').css("width",$("#buttonWidthId").val());
		$('#btn').css("height",$("#buttonHeightId").val());
		
		$("#btn").css("border-top-width",$("#buttonBorderSizeId").val());
		$("#btn").css("border-bottom-width",$("#buttonBorderSizeId").val());
		$("#btn").css("border-left-width",$("#buttonBorderSizeId").val());
		$("#btn").css("border-right-width",$("#buttonBorderSizeId").val());
		
		$("#btn").css("border-top",$("#buttonBorderColorId").val()+" "+$("#buttonBorderSizeId").val()+"px solid");
		$("#btn").css("border-bottom",$("#buttonBorderColorId").val()+" "+$("#buttonBorderSizeId").val()+"px solid");
		$("#btn").css("border-left",$("#buttonBorderColorId").val()+" "+$("#buttonBorderSizeId").val()+"px solid");
		$("#btn").css("border-right",$("#buttonBorderColorId").val()+" "+$("#buttonBorderSizeId").val()+"px solid");
		
		$("#btn").css("background-color",$("#buttonBackGroundColorId").val());
	}
	function onChangeButtonStyle(buttonID,buttonCssKey)
	{
	   if($('#'+buttonID).val() != ""){
		  $('#btn').css(buttonCssKey,$('#'+buttonID).val());
	   }
	}
	function onButtonNameValue(){
		$('#btn').attr("value",$("#buttonNameId").val());
	}
	function onChageButtonBorderSize(){
		$("#btn").css("border-top-width",$("#buttonBorderSizeId").val());
		$("#btn").css("border-bottom-width",$("#buttonBorderSizeId").val());
		$("#btn").css("border-left-width",$("#buttonBorderSizeId").val());
		$("#btn").css("border-right-width",$("#buttonBorderSizeId").val());
	}
	function onChageButtonBorderColor(){
		$("#btn").css("border-top",$("#buttonBorderColorId").val()+" "+$("#buttonBorderSizeId").val()+"px solid");
		$("#btn").css("border-bottom",$("#buttonBorderColorId").val()+" "+$("#buttonBorderSizeId").val()+"px solid");
		$("#btn").css("border-left",$("#buttonBorderColorId").val()+" "+$("#buttonBorderSizeId").val()+"px solid");
		$("#btn").css("border-right",$("#buttonBorderColorId").val()+" "+$("#buttonBorderSizeId").val()+"px solid");
	}
	function onChageButtonBackGroundColor(){
		$("#btn").css("background-color",$("#buttonBackGroundColorId").val());
	}
//-->
</script>
<div>
	<div style="display: none;">
		<input id="domainSubmit" type="submit">
		<input id="formId" name="formId" value="${formId}" />
		<input id="paramsId" name="params" value="${params}">
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<table>
				<tr>
					<td>
						<c:forEach items="${formButtons}" var="formButton"
							varStatus="status">
							<c:if test="${formButton.buttonType == '1'}">
								<c:forEach items="${formButton.buttonGroup.buttonAndGroups}"
									var="buttonAndGroup" varStatus="stat">
									<button type="button" id="${buttonAndGroup.button.buttonBM}"
										name="b${status.count*stat.count}" class="listbutton"
										onclick="event${buttonAndGroup.button.buttonBM}();">
										${buttonAndGroup.button.buttonName}
									</button>
								</c:forEach>
							</c:if>
							<c:if test="${formButton.buttonType == '0'}">
								<button type="button" name="b${status.count}" class="listbutton"
									onclick="event${formButton.button.buttonBM}();">
									${formButton.button.buttonName}
								</button>
							</c:if>
						</c:forEach>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
<div class="tabs">
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
			<ul id="tabLiId">
			</ul>
		</div>
	</div>
	<div class="tabsContent" id="tabDivId">
	</div>
	<!-- Tab结束层 -->
	<div class="tabsFooter">
		<div class="tabsFooterContent"></div>
	</div>
</div>
<br>
<div align="center">
	<table class="Input_Table" border="0" bgcolor="#FFFFFF" style="width: 99%; height: 150px">
		<tr>
			<td align="center" valign="middle">
				<input type="button" id="btn" name="btn" value="" style="width: 60px;height:21px;">
			</td>
		</tr>
	</table>
</div>
