<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+ "://"+request.getServerName() + ":"+request.getServerPort()+path;
%>

<script type="text/javascript">
<!--		
	$(function(){
		$.ajaxSetup({async: false});
		compexDataJson('<%=basePath %>/pages/resource/compexdataJson.action?formId=${formId}&params=${params}&model=${model}&subDomainId=${subDomainId}&partitionId=${partitionId}');
		$("#XG").attr("disabled","disabled");
		$("#XG").attr("class","listbuttonDisable");
		var params=$("#paramsId").val();
		$("#domainId").val(params.split(";")[0].split(":")[1]);
		
		listpage();
		$("#pathSelectId").combobox();
		
		$('#yunDialog').attr('style', 'height: 100%;overflow-x:hidden;OVERFLOW-Y:auto;');
		
		var op = '${op}';
		if(op=='new') {
			//loadDefaultFont();
		}
		$.ajaxSetup({async: true});
		formbzUrl = "<%=basePath %>/pages/resource/compexshowFormHelp.action?formId=${formId}";
		ns.common.mouseForButton();
	});


	//获得缺省的字体
	function loadDefaultFont() {
		$.ajax({
			type:'POST',
			url:'<%=basePath %>/pages/deployment/fontload.action',
		    dataType:'json',
			success:function(data){
				$("#sys_catalog-tbl_zihao").val((data.fontSize));
				$("#sys_catalog-tbl_ziti").val(data.fontFamily);
				$("#sys_catalog-tbl_yanse").val(data.color);
			}
		});
	}

	function listpage(){
		var urlString = "<%=basePath %>/pages/resource/tabulation/getTabulations.action";
		$.ajax({
			type:'post',
			url: urlString,
			dataType: 'json',
			async: false,
			success: function(data){
				buildCustomPath(data);
			}
		});
	}
	
	function formpage(){
		$("#sys_catalog-tbl_pathCustom").html("");
		$("#pathId").hide();
		$("#pathId").val("");
	}
	
	function imagepage(){
		var urlString = "<%=basePath %>/pages/system/imagePage/getImagePages.action";
		$.ajax({
			type:'post',
			url: urlString,
			dataType: 'json',
			async: false,
			success: function(data){
				buildCustomPathByImg(data);
			}
		});
	}

	function buildCustomPath(tabulations){
		var pathstr="<select id='pathSelectId' name='sys_catalog-tbl_listid' style='width:135px' onchange='selectPath(\"list\");'>";
		pathstr+="<option value='-1' id=''>无</option>";
		for(var i=0;i<tabulations.length;i++){
			var tabulation=tabulations[i];
			var selected = "";
			if($("#sys_catalog-tbl_listidCustomInput").val()==tabulation.id){
				selected = "selected='selected'";
			}
			pathstr+="<option "+selected+" value='"+tabulation.id+"' id='"+tabulation.tabulationPath+"'>"+tabulation.tabulationName+"</option>";
		}
		pathstr+="</select>";
		$("#sys_catalog-tbl_listidCustom").html(pathstr);
	}

	function buildCustomPathByImg(imagepages){
		var pathstr="<select id='pathSelectId' name='sys_catalog-tbl_listid' style='width:135px' onchange='selectPath(\"img\");'>";
		pathstr+="<option value='-1'>无</option>";
		for(var i=0;i<imagepages.length;i++){
			var imagepage=imagepages[i];
			var selected = "";
			if($("#sys_catalog-tbl_listidCustomInput").val()==imagepage.id){
				selected = "selected='selected'";
			}
			pathstr+="<option "+selected+" value='"+imagepage.id+"' id='"+imagepage.imagePagePath+"'>"+imagepage.name+"</option>";
		}
		pathstr+="</select>";
		$("#sys_catalog-tbl_listidCustom").html(pathstr);
	}

	function selectPath(pagetype) {
		if(pagetype == 'list'){
			var _listpath = $("#pathSelectId option:selected").attr("id");
			$("#sys_catalog-tbl_path").val(_listpath);
		}else if(pagetype == 'img'){
			var _imgpath = $("#pathSelectId option:selected").attr("id");
			$("#sys_catalog-tbl_path").val(_imgpath);
		}
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