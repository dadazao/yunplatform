<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>
<script type="text/javascript">
<!--		
	$(function(){
		compexDataJson('<%=basePath %>/pages/resource/buttondataJson.action?formId=${formId}&params=${params}&model=${model}&subDomainId=${subDomainId}&partitionId=${partitionId}');
		$("#XG").attr("disabled","disabled");
		$("#XG").attr("class","listbuttonDisable");
		var params=$("#paramsId").val();
		$("#domainId").val(params.split(";")[0].split(":")[1]);
		
		//新建按钮，设置默认值
		var bName = $("#button_buttonName").val();
		if(bName == "") {
			document.getElementById("button_buttonNameFontSize").value = "12";
			document.getElementById("button_buttonBorderSize").value = "1";
			document.getElementById("button_buttonWidth").value = "60";
			document.getElementById("button_buttonHeight").value = "24";
			document.getElementById("button_buttonNameFontColor").value = "#000000";
			document.getElementById("button_buttonBackGroundColor").value = "#FFFFFF";
			document.getElementById("button_buttonBorderColor").value = "#000000";
		}
		
		initButtonCssStyle();
		
		$("#button_buttonName").blur(function(){
			onButtonNameValue();
		});
		$("#button_buttonNameFontStyle").change(function(){
			onChangeButtonStyle("button_buttonNameFontStyle","fontFamily");
		});
		$("#button_buttonNameFontSize").blur(function(){
			onChangeButtonStyle("button_buttonNameFontSize","fontSize");
		});
		$("#button_buttonNameFontColor").change(function(){
			onChangeButtonStyle("button_buttonNameFontColor","color");
		});
		$("#button_buttonWidth").blur(function(){
			onChangeButtonStyle("button_buttonWidth","width");
		});
		$("#button_buttonHeight").blur(function(){
			onChangeButtonStyle("button_buttonHeight","height");
		});
		$("#button_buttonBorderSize").blur(function(){
			onChageButtonBorderSize();
		});
		$("#button_buttonBorderColor").change(function(){
			onChageButtonBorderColor();
		});
		$("#button_buttonBackGroundColor").change(function(){
			onChageButtonBackGroundColor();
		});
		$('#yunDialog').attr('style', 'height: 100%;overflow-x:hidden;OVERFLOW-Y:auto;');
		formbzUrl = "<%=basePath %>/pages/resource/compexshowFormHelp.action?formId=${formId}";
		
		var edit = "${op}";
		if(edit == 'new') {
			loadDefaultFont();
		}
		ns.common.mouseForButton();

	});

	//获得缺省的字体
	function loadDefaultFont() {
		$.ajax({
			type:'POST',
			url:'<%=basePath %>/pages/deployment/fontload.action',
		    dataType:'json',
			success:function(data){
				$("#sys_button-tbl_buttonNameFontSize").val((data.fontSize));
				$("#sys_button-tbl_buttonNameFontStyle").val(data.fontFamily);
				$("#sys_button-tbl_buttonNameFontColor").val(data.color);
			}
		});
	}
	
	function initButtonCssStyle(){
		$('#btn').attr("value",$("#button_buttonName").val());
		$('#btn').css("fontFamily",$("#button_buttonNameFontStyle").val());
		$('#btn').css("fontSize",$("#button_buttonNameFontSize").val());
		$('#btn').css("color",$("#button_buttonNameFontColor").val());
		$('#btn').css("width",$("#button_buttonWidth").val());
		$('#btn').css("height",$("#button_buttonHeight").val());
		
		$("#btn").css("border-top-width",$("#button_buttonBorderSize").val());
		$("#btn").css("border-bottom-width",$("#button_buttonBorderSize").val());
		$("#btn").css("border-left-width",$("#button_buttonBorderSize").val());
		$("#btn").css("border-right-width",$("#button_buttonBorderSize").val());
		
		$("#btn").css("border-top",$("#button_buttonBorderColor").val()+" "+$("#button_buttonBorderSize").val()+"px solid");
		$("#btn").css("border-bottom",$("#button_buttonBorderColor").val()+" "+$("#button_buttonBorderSize").val()+"px solid");
		$("#btn").css("border-left",$("#button_buttonBorderColor").val()+" "+$("#button_buttonBorderSize").val()+"px solid");
		$("#btn").css("border-right",$("#button_buttonBorderColor").val()+" "+$("#button_buttonBorderSize").val()+"px solid");
		
		$("#btn").css("background-color",$("#button_buttonBackGroundColor").val());
	}
	function onChangeButtonStyle(buttonID,buttonCssKey)
	{
	   if($('#'+buttonID).val() != ""){
		  $('#btn').css(buttonCssKey,$('#'+buttonID).val());
	   }
	}
	function onButtonNameValue(){
		$('#btn').attr("value",$("#button_buttonName").val());
	}
	function onChageButtonBorderSize(){
		$("#btn").css("border-top-width",$("#button_buttonBorderSize").val());
		$("#btn").css("border-bottom-width",$("#button_buttonBorderSize").val());
		$("#btn").css("border-left-width",$("#button_buttonBorderSize").val());
		$("#btn").css("border-right-width",$("#button_buttonBorderSize").val());
	}
	function onChageButtonBorderColor(){
		$("#btn").css("border-top",$("#button_buttonBorderColor").val()+" "+$("#button_buttonBorderSize").val()+"px solid");
		$("#btn").css("border-bottom",$("#button_buttonBorderColor").val()+" "+$("#button_buttonBorderSize").val()+"px solid");
		$("#btn").css("border-left",$("#button_buttonBorderColor").val()+" "+$("#button_buttonBorderSize").val()+"px solid");
		$("#btn").css("border-right",$("#button_buttonBorderColor").val()+" "+$("#button_buttonBorderSize").val()+"px solid");
	}
	function onChageButtonBackGroundColor(){
		$("#btn").css("background-color",$("#button_buttonBackGroundColor").val());
	}
	
//-->
</script>
<div id="yunDialog">
	<div  class="buttonPanel">
		<div style="display:none;">
			<input id="domainSubmit" type="submit"/>
			<input type="hidden" id="isFirstTab" value="true">
			<input id="formId" name="formId" value="${formId}"/>
			<input id="domainId" name="domainId" value="${domainId}"/>
			<input id="paramsId" name="params" value="${params}"/>
			<input id="mainTable" name="mainTable" value="${mainTable}"/>
		</div>
		<c:forEach items="${form.formButtons}" var="formButton" varStatus="status">
			<c:if test="${formButton.hasAuth=='0'}">
				<c:if test="${formButton.buttonType == '0'}">
					<button type="button" id="${formButton.funcName}" name="b${status.count}" class="listbutton" style="width:${formButton.button.buttonWidth}px;height:${formButton.button.buttonHeight}px;" onclick="eventCompex${formButton.funcName}();" style="width:${formButton.button.buttonWidth}px;height:${formButton.button.buttonHeight}px;background: ${formButton.button.buttonBackGroundColor};border-color: ${formButton.button.buttonBorderColor};"><font color="${formButton.button.buttonNameFontColor}" style="font-family:${formButton.button.buttonNameFontStyle};font-size: ${formButton.button.buttonNameFontSize}px;">${formButton.showName}</font></button>	
				</c:if>
			</c:if>
			<c:if test="${formButton.hasAuth=='1'}">
				<c:if test="${formButton.buttonType == '0'}">
					<button type="button" id="${formButton.funcName}" name="b${status.count}" class="listbuttonDisable" disabled="disabled" style="width:${formButton.button.buttonWidth}px;height:${formButton.button.buttonHeight}px;background: ${formButton.button.buttonBackGroundColor};border-color: ${formButton.button.buttonBorderColor};"><font color="${formButton.button.buttonNameFontColor}" style="font-family:${formButton.button.buttonNameFontStyle};font-size: ${formButton.button.buttonNameFontSize}px;">${formButton.showName}</font></button>	
				</c:if>
			</c:if>
		</c:forEach>
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
</div>
	<%--<div align="center">
		<table class="Input_Table" border="0" bgcolor="#FFFFFF" style="width: 99%; height: 150px">
			<tr>
				<td align="center" valign="middle">
					<input type="button" id="btn" name="btn" value="" style="width: 60px;height:21px;">
				</td>
			</tr>
		</table>
	</div>
--%>