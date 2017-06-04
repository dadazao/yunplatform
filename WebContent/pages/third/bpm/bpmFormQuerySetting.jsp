<%--
	time:2012-06-25 11:05:09
	desc:edit the 通用表单对话框
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 通用表单查询</title>
	<%@include file="/commons/include/get.jsp" %>		
	<script type="text/javascript"src="${ctx}/js/hotent/platform/system/ScriptDialog.js"></script>
	<script type="text/javascript" src="${ctx}/js/javacode/codemirror.js"></script>
	<script type="text/javascript" src="${ctx}/js/javacode/InitMirror.js"></script>
	<script type="text/javascript">
		$(function() {
				$("#defLayout").ligerLayout({ leftWidth: 270,height: '100%',
				bottomHeight:50,allowLeftCollapse:false,rightWidth:465,allowRightResize:false,centerWidth:20,
			 	allowBottomResize:false,allowRightCollapse:false});
				//默认显示第一个Tab标签
				javaFieldSelected(false) ;
				init();	
		});
		
		function init(){
			$("input.treeField").focus(function(){
				curField=$(this);
			});
			var fields = window.dialogArguments;	
			if(fields.displayField)initDisplayField(fields.displayField);
			if(fields.conditionField)initConditionField(fields.conditionField);
			if(fields.resultField)initResultField(fields.resultField);
			if(fields.sortField)initSortField(fields.sortField);
		}
		//未保存的自定义对话框，在已经设置列以后再次打开设置列的窗口时 初始化已设置的列
		function initDisplayField(displayField){
			var fieldObj=eval("("+displayField+")"),
				objContainer=$("#trDisplayField");
			objContainer.empty();
			for(var i=0,c;c=fieldObj[i++];){
				objContainer.append(getDispalyField(c.field,c.comment));
			}
		};
		
		//初始化条件字段
		function initConditionField(conditionField){
			var fieldObj=eval("("+conditionField+")"),
				objContainer=$("#trConditionField");
			objContainer.empty();
			if(fieldObj.length>0 && fieldObj[0].defaultType == 5){
				for(var i=0;i<fieldObj.length;i++){
					var c = fieldObj[i] ;
					if(c.field=='') continue ;
					var btn = $('div.fieldBtnDiv a:last').clone(true).css('display','') ;
					var delBtn = $('div.fieldBtnDiv .divX:last').clone(true) ;
					btn.attr('name',c.field) ;
					btn.children('span:first').text(c.comment) ;
					$('div.fieldBtnDiv').append(btn).append(delBtn) ;
					
				}
				$("#script").text(fieldObj[0].defaultValue) ;
				javaFieldSelected(true) ;
				return ;
			}
			for(var i=0,c;c=fieldObj[i++];){
				objContainer.append(getConditionField(c.field,c.comment,c.dbType));
				var curTr = $("#condition"+c.field),
					curValTr = $("#conditionVal"+c.field);
				$("select.condition",curTr).val(c.condition);
				$("select[name='changeDefalut']",curValTr).val(c.defaultType);
				changeDefault($("select[name='changeDefalut']",curValTr));
				$("textarea[name='defaultValue']",curValTr).val(c.defaultValue);
			}
		};
		
		//初始化返回字段
		function initResultField(resultField){
			var fieldObj=eval("("+resultField+")"),
				objContainer=$("#trRtnField");
			objContainer.empty();
			if(!fieldObj)return;
			for(var i=0,c;c=fieldObj[i++];){
				objContainer.append(getRtnField(c.field,c.comment));
			}
		};
		
		//初始化排序字段
		function initSortField(sortField){
			if(!sortField.trim()) return ;
			var fieldObj=eval("("+sortField+")"),
				objContainer=$("#trSortField");
			objContainer.empty();
			if(!fieldObj)return;
			for(var i=0,c;c=fieldObj[i++];){
				var tr=$('tr#sortTrTemplate').clone(true);
				tr.attr('id','sort'+c.field);
				tr.attr('name',c.field);
				tr.css('display','');
				if(c.comment=='ASC'){
					$('td#sort',tr).find(':checkbox').attr('checked',true);
				}else{
					$('td#sort',tr).find(':checkbox').attr('checked',false);
				}
				$('td#fieldName',tr).text(c.field);
				objContainer.append(tr);
			}
		};
		
		var curField;
		/*
		 *左边字段选择
		 */
		function selectTreeField(){   
			var obj=$("input:checkbox[name='fieldName']:checked");
			if(curField==null || curField.length==0){
				$.ligerDialog.error("请选择右边的输入框!",'提示信息');
				return;
			}
			if(obj.length==0){
				$.ligerDialog.error("请选择左边的字段!",'提示信息');
				return;
			}
			if(obj.length>1){
				$.ligerDialog.error("只能选择一个左边的字段!",'提示信息');
				return;
			}
			curField.val(obj.val());

		}		
		
		function delRow(obj){
			$(obj).closest("tr").remove();
			
		}
		
		function delConditionTr(obj){
			var tr=$(obj).closest("tr");
			tr.next().remove().end().remove();
		}		
		
		function getConditionField(fieldName,comment,dbType){
			var db=getConditionSelect(dbType,fieldName,comment);
			var sb=new StringBuffer();
			sb.append("<tr class='trCondition' id='condition"+ fieldName +"'>");
			sb.append("<td >"+fieldName+"</td>");
			sb.append("<td >"+db+"</td>");
			sb.append("<td >"+comment+"</td>");
			sb.append("<td><a alt='上移' href='#' class='link moveup' onclick='sortConditionTr(this,true)'></a>");
			sb.append("<a alt='下移' href='#' class='link movedown' onclick='sortConditionTr(this,false)'></a>");
			sb.append("<a href='#' class='link del'  onclick='delConditionTr(this)' >删除</a></td>");
			sb.append("</tr>");
			sb.append("<tr id='conditionVal"+ fieldName +"'>");
			sb.append("<td>默认值</td>");
			sb.append("<td><select name='changeDefalut' onchange='changeDefault(this)'>");
			sb.append("<option value='1'>参数传入</option>");
			sb.append("<option value='2'>固定值</option><option value='3'>脚本</option></select></td>");
			sb.append("<td colspan='2'><a style='display:none;' href='#' name='btnScript' class='link var' title='常用脚本' onclick='selectScript(this)'>常用脚本</a>");
			sb.append("<textarea name='defaultValue' cols='40' rows='3' ");
			sb.append("		style='display:none;' ");
			sb.append("></textarea></td>");
			sb.append("</tr>");
			return sb.toString();
		}
		
		function selectScript(obj) {
			var txtObj=$(obj).siblings("textarea")[0];
			ScriptDialog({
				callback : function(script) {
					$.insertText(txtObj,script);
				}
			});
		};
		
		function changeDefault(obj){
			var val=$(obj).val();
			var objTr=$(obj).parents("tr");
			var txtObj=$("textarea[name='defaultValue']",objTr);
			var linkObj=$("a[name='btnScript']",objTr);
			switch(val){
				case "1":
					txtObj.hide();
					linkObj.hide();
					break;
				case "2":
					txtObj.show();
					linkObj.hide();
					break;
				case "3":
					txtObj.show();
					linkObj.show();
					break;
			}
		}
			
		function getRtnField(fieldName,comment){
			var sb=new StringBuffer();
			sb.append("<tr id='rtn"+ fieldName +"' name='"+fieldName+"' comment='"+comment+"' >");
			sb.append("<td >"+fieldName+"</td>");
			sb.append("<td >"+comment+"</td>");
			sb.append("<td><a alt='上移' href='#' class='link moveup' onclick='sortTr(this,true)'>&nbsp;&nbsp;</a>");
			sb.append("<a alt='下移' href='#' class='link movedown' onclick='sortTr(this,false)'>&nbsp;&nbsp;</a>");
			sb.append("<a href='#' class='link del' onclick='delRow(this)' >删除</a></td>");
			sb.append("</tr>");
			return sb.toString();
		}
		
		function getConditionSelect(dbType,name,comment){
			var sb=new StringBuffer();
			sb.append("<select class='condition' name='"+name+"' dbType='"+dbType+"' comment='"+comment+"'>");
			switch(dbType){
				case "varchar":
					sb.append("<option value='='>等于</option>");
					sb.append("<option value='like'>LIKE</option>");
					sb.append("<option value='likeEnd'>LIKEEND</option>");
					break;
				case "number":
					sb.append("<option value='='>等于</option>");
					sb.append("<option value='>='>大于等于</option>");
					sb.append("<option value='>'>大于</option>");
					sb.append("<option value='<'>小于</option>");
					sb.append("<option value='<='>小于等于</option>");
					break;
				case "date":
					sb.append("<option value='='>等于</option>");
					sb.append("<option value='between'>Between</option>");
					sb.append("<option value='>='>大于等于</option>");
					sb.append("<option value='<='>小于等于</option>");
					
					break;
				
			}
			
			sb.append("</select>");
			return sb;
		}

		function setField(){
			var divContents = $('div.content') ;
			var visibleDivContent = undefined;
			if(divContents==undefined) return ;
			for(var i=0;i<divContents.length;i++){
				if($(divContents[i]).css('display')!='none'){
					visibleDivContent = divContents[i] ;
					break ;
				}
			}
			if(visibleDivContent == undefined){
				$.ligerDialog.error('请先展开右边目标区域','提示信息');
				return ;
			}
			var divParent = $(visibleDivContent).closest('.panel-detail') ;
			
			if(divParent.attr('class').indexOf('condition')>=0){
				if($('.javaField').css('display')!='none'){
				//java脚本区域
					
					$("input[name='fieldName']:checked").each(function(){
						var trObj=$(this).closest("tr");	
						var fieldName=$(this).val();  //id
						var comment=$("input[name='comment']",trObj).val();
						var dbType=$(this).attr("dbType");
						var fnBtns = $('div.fieldBtnDiv').children('[name="'+fieldName+'"]') ;
						if(fnBtns==undefined || fnBtns.length<1){
	  						var btn = $('div.fieldBtnDiv').children('.button:last').clone(true).css('display','') ;
	  						var delBtn = $('div.fieldBtnDiv').children('.divX:last').clone(true) ;
	  						btn.attr('name',fieldName) ;
	  						btn.attr('dbType',dbType) ;
	  						btn.children('span:first').text(comment) ;
	  						$('div .fieldBtnDiv').append(btn).append(delBtn) ;
	  						javaFieldSelected(true);
						}
					});
				}else if($('.trConditionField').css('display')!='none'){
					if($('.btnTree')!=undefined && $('.btnTree').length>0){
						setConditionField(false) ;
					}else
						setConditionField(true) ;
				}
				return ;
			}
			if(divParent.attr('class').indexOf('return')>=0){
				setReturnField() ;
				return ;
			}
			if(divParent.attr('class').indexOf('sort')>=0){
				setSortField() ;
				return ;
			}
		}
		
		function setSortField(){
			var objContainer=$("#trSortField");
			$("input:checkbox[name='fieldName']:checked").each(function(){
				var trObj=$(this).closest("tr");	
				var fieldName=$(this).val();  //id
				var obj=$("#sort" + fieldName);
				if(obj.length==0){
					var tr=$('tr#sortTrTemplate').clone(true);
					tr.attr('id','sort'+fieldName);
					tr.attr('name',fieldName);
					tr.css('display','');
					$('td#fieldName',tr).text(fieldName);
					objContainer.append(tr);
				}
			});
		}
		
		function setConditionField(isList){
			var objContainer=$("#trConditionField");
			$("input[name='fieldName']:checked").each(function(){
				var trObj=$(this).closest("tr");	
				var fieldName=$(this).val();  //id
				var comment=$("input[name='comment']",trObj).val();
				var dbType=$(this).attr("dbType");
				var tr=getConditionField(fieldName,comment,dbType,isList);
				objContainer.append(tr);
				
			});
		}
		
		function setReturnField(){
			var objContainer=$("#trRtnField");
			$("input:checkbox[name='fieldName']:checked").each(function(){
				var trObj=$(this).closest("tr");	
				var fieldName=$(this).val();  //id
				var comment=$("input[name='comment']",trObj).val();
				var obj=$("#rtn" + fieldName);
				if(obj.length==0){
					var tr=getRtnField(fieldName,comment);
					objContainer.append(tr);
				}
			});
		}
		
		var displayStr="";
		var conditionStr="";
		var rtnStr="";		
		var sortStr="";
		
		function buildTreeJson(){
			var aryRtnField=[];
			var  rtn="";
			var treeId=$("#treeId").val();
			var parentId=$("#parentId").val();
			var displayName=$("#displayName").val();
			
			if(treeId=="" || parentId=="" || displayName==""){
				rtn+="请填写映射树的字段\r\n";
			}
			
			displayStr="{id:'"+treeId+"',pid:'"+parentId+"',displayName:'"+displayName+"'}";
			var result=getCondition();
			if(!result){
				rtn+="请填写条件字段的值\r\n";
			}
			//条件字段
			$("#trRtnField").children().each(function(){
				var fieldName=$(this).attr("name");
				var comment=$(this).attr("comment");
				var obj={};
				obj.field=fieldName;
				obj.comment=comment;
				aryRtnField.push(obj);
			});
			
			if(aryRtnField.length==0){
				rtn+="请选择返回字段";
			}
			rtnStr=JSON2.stringify(aryRtnField);
			
			return rtn;
		}
		
		//获取选择的条件。
		function getCondition(){
			var aryContion=[];
			var rtn=true;
			if(curConditionField=="javaField"){
				$('.fieldBtnDiv a.button').each(function(i){
					//跳过第一个隐藏的按钮
					if(i==0) return true ;
					var obj={};
					var btn = $(this) ;
					obj.field=btn.attr('name');
					obj.comment=btn.children('span:first').text();
					obj.condition="";
					obj.dbType="";
					obj.defaultType="5";
					obj.defaultValue="";
					if(InitMirror.editor!=null){
						obj.defaultValue=InitMirror.editor.getCode();
					}
					aryContion.push(obj);
				});
				if(aryContion.length == 0){
					obj.field="";
					obj.comment="";
					obj.condition="";
					obj.dbType="";
					obj.defaultType="5";
					obj.defaultValue=InitMirror.editor.getCode();
					aryContion.push(obj);
				}
				conditionStr=JSON2.stringify(aryContion);
				return rtn ;
			}

			$("#trConditionField").children("tr.trCondition").each(function(){
				var trObj=$(this);
				var trDefault=trObj.next();
				var selObj=$('select.condition',trObj);
				var fieldName=selObj.attr("name");
				var comment=selObj.attr("comment");
				var condition=selObj.attr("value");
				var dbType=selObj.attr("dbType");
				var obj={};
				obj.field=fieldName;
				obj.comment=comment;
				obj.condition=condition;
				obj.dbType=dbType;
				
				var selDefault=$("select[name='changeDefalut']",trDefault).val();
				var txtDefault=$("textarea[name='defaultValue']",trDefault).val();
				if(selDefault!="1" && txtDefault.trim()==""){
					rtn=false;
				}
				obj.defaultType=selDefault;
				if(selDefault=="1"){
					obj.defaultValue="";
				}
				else{
					obj.defaultValue=txtDefault;
				}
				aryContion.push(obj);
			});
			conditionStr=JSON2.stringify(aryContion);
			return rtn;
		}
		
		function buildListJson(){
			var rtn="";
			var aryDisplay=[];
			
			var aryRtnField=[];
			
			var arySortField=[];
			
			var result=getCondition();
			if(!result){
				rtn+="请填写条件字段的值\r\n";
			}
			
			$("#trRtnField").children().each(function(){
				var fieldName=$(this).attr("name");
				var comment=$(this).attr("comment");
				var obj={};
				obj.field=fieldName;
				obj.comment=comment;
				aryRtnField.push(obj);
			});
			
			if(aryRtnField.length==0){
				rtn+="请选择返回字段";
			}
			
			$("#trSortField").children().each(function(){
				var fieldName=$(this).attr("name");
				var comment='ASC' ;
				if($('#sort :checked',this).length==0){
					comment='DESC' ;
				}
				var obj={};
				obj.field=fieldName;
				obj.comment=comment;
				arySortField.push(obj);
			});
			
			displayStr=JSON2.stringify(aryDisplay);
			rtnStr=JSON2.stringify(aryRtnField);
			sortStr=JSON2.stringify(arySortField);
			return rtn;
			
		}
		
	    function selectForm(){
	    	var rtn="";
			//如果是树状的只取不大于3个的返回值				
			if($("input.treeField").length>0) {
				rtn=buildTreeJson();
			}
			else{
				rtn=buildListJson();
			}
			if(rtn!=""){
				$.ligerDialog.warn(rtn,'提示信息');
				return;
			}
			var rerurnlist= new Array(displayStr,conditionStr,rtnStr,sortStr); 
			window.returnValue=rerurnlist;
	    	window.close();
			
	    }
	    
		function sortTr(obj,isUp) {
			var thisTr = $(obj).parents("tr");
			if(isUp){
				var prevTr = $(thisTr).prev();
				if(prevTr){
					thisTr.insertBefore(prevTr);
				}
			}
			else{
				var nextTr = $(thisTr).next();
				if(nextTr){
					thisTr.insertAfter(nextTr);
				}
			}
		};
	    
		function sortConditionTr(obj,isUp) {
			var thisTr = $(obj).closest("tr");
			var nextTr=thisTr.next();
			
			//向上
			if(isUp){
				var prevTr = thisTr.prev();
				if(prevTr.length==0) return;
				var targeTr=prevTr.prev();
				thisTr.insertBefore(targeTr)
				nextTr.insertBefore(targeTr);
			}
			else{
				var tmpTr =nextTr.next();
				if(tmpTr.length==0) return;
				var targeTr=tmpTr.next();
				nextTr.insertAfter(targeTr);
				thisTr.insertAfter(targeTr);
			}
		};
		$(function(){
			
			var headHei = $('.header').height() ;
			var siblingNum = $('.header').siblings().length ;
			var conditionTable = $('.conditionContainer').find('table') ;
			var settingContainerHei = $('.l-layout-right').height()-headHei ;
			
			$('.header').next('.content').hide() ;
			$(".header").hover(
				  function () 
				  {
					 $(this).css("color","#ffffff");
				     $(this).css("cursor","pointer");
				  },
				  function () 
				  {
				     $(this).css("color","#222222");
					 $(this).css("cursor","pointer");
				  }
		   );
		   //加载或关闭二级菜单
		   $(".header").bind('click',
				function () 
				{
					settingContainerHei = $('.l-layout-right').height()-headHei ;
			        var divContentObj = $(this).next('.content');
			        var isHidden = $(divContentObj).css('display')=='none';
		           	$("div.content").hide();
		           
			        if(isHidden){
			           	divContentObj.show() ;
			           	divContentObj.css("height",settingContainerHei-(siblingNum+1)*headHei);
		            }
			        
				}
		   );
		   $("td[nowrap='nowrap']").dblclick(function(){
			  var field = $(this).text().trim() ;
			  if($('.javaField').css('display')!='none'){
				  InitMirror.editor.insertCode(field) ;
			  }
		   });
	      
	      $("div.tab-content div.tab-layout").attr("id", function(){return "No"+ $("div.tab-content div.tab-layout").index(this)});
	      $("ul.tab-menu li").click(function(){
	          var c = $("ul.tab-menu li");
	          var index = c.index(this);
	          $('#No'+index).siblings().hide();
	          $('#No'+index).show() ;
	          c.eq(index).addClass("current").siblings('li').removeClass("current");
			   if($('#No'+index).attr('class').indexOf('javaField')>=0){
				   if($('div.fieldBtnDiv').children('a.button').length>1){
						$('div.fieldBtnDiv').show() ;
				   }
				   curConditionField = "javaField" ;
			   }else{
				   $('.fieldBtnDiv').hide() ;
				   if($('#No'+index).attr('class').indexOf('helpField')<0)
				   		curConditionField = "" ;
			   }
	   	  });
	   
	   	  $('.fieldBtnDiv .button').live('click',function(){
				InitMirror.editor.insertCode($(this).attr('name')) ;
	   	  });
	   	  
			$('.fieldBtnDiv .button').mouseover(function(){
				var targetX = $(this).next('div.divX') ;
				if(targetX==null) return ;
		   		targetX.css({"left":$(this).width()+targetX.width()+$(this).position().left}) ;
		   		targetX.show() ;
			})
			$('.fieldBtnDiv .button').mouseleave(function(){
				if($(this).next('div.divX')==null) return ;
				$(this).next('div.divX').hide();
			})
			$('.divX').mouseenter(function(){
				$(this).show() ;
			})
			$('.divX').mouseleave(function(){
				$(this).hide();
			})
			$('.divX').click(function(){
				$(this).prev('a.button').remove() ;
				$(this).remove();
			})
			
			$('.l-layout-center .btnContainer').css('margin-top',$('.l-layout-content').height()/2+"px") ;
		
		})
		var curConditionField ;
		function javaFieldSelected(bool){
			$("ul.tab-menu li").removeClass("current");
			$("div.tab-content .tab-layout").hide();
			if(bool==true){
				$("ul.tab-menu li:nth-child(2)").addClass("current");
				$(".tab-content .javaField").show() ;
				if($('div.fieldBtnDiv').children('a.button').length>1){
					$('div.fieldBtnDiv').show() ;
				}
				curConditionField = "javaField" ;
			}else{
				$("ul.tab-menu li:first-child").addClass("current");
				$("div.tab-content .tab-layout:first-child").show() ;
				curConditionField = "" ;
			}
		}
	    
	</script>
	<style type="text/css">
		body{ padding:2px; margin:0 0 0 0;overflow: hidden; }
		div.content{height:110px;overflow: auto;}
		ul.btnContainer{text-align: center;}
		.l-layout-content{width:100% !important;}
		.l-layout-right{left:319px;height:480px;}
		.l-layout-right .l-layout-header-inner {padding-left : 0px;}
		.l-layout-center{height:480px;}
		/* tab样式 */
		.tab-box {width:99%; height:99%; margin:1px;}
		.tagMenu {height:20px; line-height:20px; background:#efefef; position:relative; border-bottom:1px solid #999}
		.tab-content .table-grid{margin-top: 0px;}
		.tagMenu ul {position:absolute; left:5px; bottom:-1px; height:26px;}
		ul.tab-menu li {float:left; margin-bottom:1px; line-height:16px; height:14px; margin:5px 0 0 -1px; text-align:center; padding:0 12px; cursor:pointer}
		ul.tab-menu li.current {border:1px solid #999; border-bottom:none; background:#fff; height:25px; line-height:26px; margin:0}
		.javaField, .helpField {border: 3px solid #DDDDDD;height:200px;float:left;width:98%;}
		.helpField {padding:0 5px 0 10px;width:95%;display:none;}
		.fieldBtnDiv {float:left;display:none;}
		.fieldBtnDiv .button{margin:0 2px 2px 2px;}
		
		.divX {line-height:7px;width:7px;font-weight:bold;cursor:pointer;color:red;display: none;position:relative;float:left;}
		.table-detail .inputText {width:140px;}
	</style>
	
</head>
<body>
		<div id="defLayout" >
	            <div position="left" title="获取字段列表" style="overflow: scroll;width: 270px;height:450px;">
					<table cellpadding="1" class="table-grid table-list" cellspacing="1">
						<tr>
							<th></th>
							<th>字段</th>
							<th>注释</th>
						</tr>
						<c:forEach var="col" items="${tableModel.columnList }" varStatus="status">
						<c:set var="clsName"  ><c:choose><c:when test="${status.index%2==0}">odd</c:when><c:otherwise>even</c:otherwise></c:choose> </c:set>
						<tr class="${clsName}">
							<td>
								<c:choose>
	            					<c:when test="${style==0 }">
										<input type="checkbox" name="fieldName"  class="pk"  value="${col.name }"  dbType="${col.columnType }">
									</c:when>
									<c:otherwise>
										<input type="checkbox" name="fieldName" class="pk"  value="${col.name }" id="${col.name }" dbType="${col.columnType }">
									</c:otherwise>
								</c:choose>
							</td>
							<td nowrap="nowrap">
								${col.name }
							</td>
							<td>
								<input type="text" name="comment" class="inputText" value="${col.comment }">
							</td>
						</tr>
						</c:forEach>
					</table>
	            </div>
	            <div position="center" >
           			<ul class="btnContainer">
	          			<li class="btn">
	          				 <a href='#' class='button'  onclick="setField()" ><span >=></span></a>
	          			</li>
	          		</ul>
	            </div>  
	            <div id="fieldSetting" position="right"  title="字段设置">
					<div class="panel-detail conditionContainer">
	          			<div class="header" style="margin-top:5px;">
	          				条件字段
	          			</div>
	          			<div class="content" style="height:210px;">
	          			<div class="tab-box">
						    <div class="tagMenu">
						        <ul class="tab-menu">
						            <li>条件选择</li>
						            <li>Java脚本</li>
						            <li>帮助</li>
						        </ul>
						    </div>
						    <div class="tab-content">
							    <div class="tab-layout trConditionField">
			          			<table cellpadding="1" class="table-grid table-list" cellspacing="1">
			          				<tr>
			          					<th>
			          						字段名
			          					</th>
			          					<th>
			          						条件
			          					</th>
			          					
			          					<th>
			          						显示名
			          					</th>
			          					<th>
			          						管理
			          					</th>
			          				</tr>
			          				<tbody id="trConditionField">
			          					<c:forEach items="${ bpmFormDialog.conditionList}" var="field">
			          						<tr class='trCondition' id='condition${field.fieldName}' name='${field.fieldName}' comment='${field.comment}'>
			          							<td>${field.fieldName}</td>
			          							<td>
			          								<select class='condition' name='${field.fieldName}' dbType='${field.fieldType}' comment='${field.comment}' >
			          								<c:choose>
			          									<c:when test="${field.fieldType=='varchar'}">
				          									<option value='=' <c:if test="${field.condition=='='}">selected</c:if> >等于</option>
															<option value='like' <c:if test="${field.condition=='like'}">selected</c:if>>LIKE</option>
															<option value='likeEnd' <c:if test="${field.condition=='likeEnd'}">selected</c:if>>LIKEEND</option>
			          									</c:when>
			          									<c:when test="${field.fieldType=='number'}">
			          										<option value='=' <c:if test="${field.condition=='='}">selected</c:if>>等于</option>
															<option value='>=' <c:if test="${field.condition=='>='}">selected</c:if> >大于等于</option>
															<option value='>' <c:if test="${field.condition=='>'}">selected</c:if> >大于</option>
															<option value='<' <c:if test="${field.condition=='<'}">selected</c:if> >小于</option>
															<option value='<=' <c:if test="${field.condition=='<='}">selected</c:if>>小于等于</option>
			          									</c:when>
			          									<c:otherwise>
			          										<option value='=' <c:if test="${field.condition=='='}">selected</c:if>>等于</option>
															<option value='between' <c:if test="${field.condition=='between'}">selected</c:if> >between</option>
															<option value='>=' <c:if test="${field.condition=='>='}">selected</c:if> >大于等于</option>
															<option value='<=' <c:if test="${field.condition=='<='}">selected</c:if> >小于等于</option>
			          									</c:otherwise>
			          								</c:choose>
			          								</select>
												</td>
												<td>${field.comment}</td>
			          							
			          							<td>
			          							<a alt='上移' href='#' class='link moveup' onclick='sortConditionTr(this,true)'></a>
			          							<a alt='下移' href='#' class='link movedown' onclick='sortConditionTr(this,false)'></a>
			          							<a href='#' class='link del'  onclick='delConditionTr(this)' >删除</a>
			          							</td>
			          						</tr>
			          					
		
			          						<tr id='conditionVal${field.fieldName}'>
			          							<td>默认值</td>
			          							<td>
			          								<select name='changeDefalut' onchange='changeDefault(this)'>
			          									<option value="2" <c:if test="${field.defaultType=='2'}">selected</c:if>>固定值</option>
			          									<option value="3" <c:if test="${field.defaultType=='3'}">selected</c:if>>脚本</option>
			          								</select>
			          							</td>
			          							<td colspan="2">
			          							<a <c:if test="${ field.defaultType=='2'}">style='display:none;'</c:if>  href='#' name='btnScript' 
			          							class='link var' title='常用脚本' onclick='selectScript(this)'>常用脚本</a><br>
			          							<textarea name='defaultValue' cols='40' rows='3' >${field.defaultValue}</textarea></td>
			          						</tr>
			          					</c:forEach>
			          				</tbody>
			          			</table>
	         					</div>
			          			<div class="fieldBtnDiv" name="fieldBtnDiv">
								 	<a href="#" class="button" onclick="" style="display:none;">
								 		<span></span>
								 	</a>
								 	<div class="divX" id="DIV" title="删除">×</div>
								 </div>
						        <div class="tab-layout javaField">
						 			<textarea id="script" name="defaultValue" codemirror="true" mirrorheight="200px" name="script" rows="26" cols="70"></textarea>
						 		</div>
						        <div class="tab-layout helpField">
						 			<span>
										例：if(map.get("ACTDEFID")!=null)<br>&nbsp;&nbsp;return " and ACTDEFID like '%"+map.get("ACTDEFID")+"%'" ;<br>
										其中的map为系统所封装的一个参数<br>
										在脚本中使用map.get("ACTDEFID")可以获取表单提交时所携带的ACTDEFID参数值，脚本应拼接并返回任意的可执行的sql语句
									</span>
					 			</div>
							</div>
	          			</div>
	          			</div>
	          		</div>
	          	
		          		<div class="panel-detail returnContainer">
		          			<div class="header">
		          				返回字段
		          			</div>
		          			<div class="content">
		          			<div style="overflow: auto;">
			          			<table cellpadding="1" class="table-grid table-list" cellspacing="1">
			          				<tr>
			          					<th>
			          						字段名
			          					</th>
			          					<th>
			          						显示名
			          					</th>
			          					<th>
			          						管理
			          					</th>
			          				</tr>
			          				<tbody id="trRtnField">
			          					<c:forEach items="${ bpmFormDialog.returnList}" var="field">
			          						<tr id='rtn${field.fieldName}' name='${field.fieldName}' comment='${field.comment}'>
			          							<td>${field.fieldName}</td>
			          							<td>${field.comment}</td>
			          							<td>
			          							<a alt='上移' href='#' class='link moveup' onclick='sortTr(this,true)'>&nbsp;</a>
			          							<a alt='下移' href='#' class='link movedown' onclick='sortTr(this,true)'>&nbsp;</a>
			          							<a href='#' class='link del'  onclick='delRow(this)' >删除</a>
			          							</td>
			          						</tr>
			          					</c:forEach>
			          				</tbody>
			          			</table>
			          		</div>
		          			</div>
		          		</div>
		          		<div class="panel-detail sortContainer">
		          			<div class="header">
		          				排序字段
		          			</div>
		          			<div class="content">
		          			<div style="overflow: auto;">
			          			<table cellpadding="1" class="table-grid table-list" cellspacing="1">
			          				<tr>
			          					<th>
			          						字段名
			          					</th>
			          					<th>
			          						排序
			          					</th>
			          					<th>
			          						管理
			          					</th>
			          				</tr>
			          				<tbody id="trSortField">
			          					<c:forEach items="${ bpmFormDialog.sortList}" var="field">
			          						<tr id="sort${field.fieldName}" name="${field.fieldName}">
			          							<td id="fieldName">${field.fieldName}</td>
			          							<td id="sort">
			          								<input type="checkbox" name="sort" id="ASC" <c:if test="${field.comment == 'ASC'}">checked="checked"</c:if> />
				          							&nbsp;升序（不勾选则为降序）
			          							</td>
			          							<td>
			          							<a alt='上移' href='#' class='link moveup' onclick='sortTr(this,true)'>&nbsp;</a>
			          							<a alt='下移' href='#' class='link movedown' onclick='sortTr(this,false)'>&nbsp;</a>
			          							<a href='#' class='link del'  onclick='delRow(this)' >删除</a>
			          							</td>
			          						</tr>
			          					</c:forEach>
			          				</tbody>
			          				<tr id="sortTrTemplate" style="display:none;">
			   							<td id="fieldName"></td>
			   							<td id="sort">
				   							<input type="checkbox" name="sort" id="ASC" checked="checked" />&nbsp;升序（不勾选则为降序）
			   							</td>
			   							<td>
			   							<a alt='上移' href='#' class='link moveup' onclick='sortTr(this,true)'>&nbsp;</a>
			   							<a alt='下移' href='#' class='link movedown' onclick='sortTr(this,false)'>&nbsp;</a>
			   							<a href='#' class='link del'  onclick='delRow(this)' >删除</a>
			   							</td>
			   						</tr>
			          			</table>
			          		</div>
		          			</div>
		          		</div>
		            </div>
		            <div position="bottom" class="bottom" style="padding-top: 15px;">
						  <a href='#' class='button'  onclick="selectForm()" ><span class="icon ok"></span><span >确定</span></a>
				  		<a href='#' class='button' style='margin-left:10px;' onclick="window.close()"><span class="icon cancel"></span><span >取消</span></a>
					</div>
					
					<input type="hidden" name="resultfield" id="resultfield" value="${bpmFormDialog.resultfield}"/>
					<input type="hidden" name="id" id="id" value="${bpmFormDialog.id}"/>
	       	  </div>
       	 
</body>
</html>
