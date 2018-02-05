<%
	//我的流程定义外面窗口
%>
<%@page import="com.hotent.platform.model.system.GlobalType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.hotent.platform.model.bpm.BpmDefRights"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
    <head>
        <title>流程定义选择器</title>
		<%@include file="/commons/include/form.jsp" %>
		<link rel="stylesheet" href="${ctx}/js/tree/zTreeStyle.css" type="text/css" /><!--  -->
	    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerMenu.js"></script>
		<script type="text/javascript" src="${ctx}/js/tree/jquery.ztree.js"></script>
		<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerLayout.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/form/GlobalType.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/system/GlobalMenu.js"></script>
		<style type="text/css">
			
			body{overflow: hidden;}
		</style>
        <script type="text/javascript">
        		//单选
        		var isSingle=${isSingle};
        		//取有效的流程
        		var validStatus=${validStatus};
        		//显示全部
        		var showAll=${showAll};
        		
        		var catKey="<%=GlobalType.CAT_FLOW%>";
        		var globalType=new GlobalType(catKey,"glTypeTree",
       				{
        			onClick:onClick,
        			url:'${ctx}/platform/system/globalType/getByCatKeyForBpm.ht'
        			,expandByDepth:1
        			});
        		
                $(function (){
                	//布局
                    $("#defLayout").ligerLayout({ leftWidth:210,height: '90%',allowLeftResize:false});
                	//加载菜单
                    globalType.loadGlobalTree();
                	
                	var url="${ctx}/platform/bpm/bpmDefinition/selector.ht?isSingle={0}&validStatus={1}&showAll={2}";
                	url=String.format(url,isSingle,validStatus,showAll);
                	$("#bpmDefinitionListFrame").attr("src",url);
          
                });
              	//左击
            	function onClick(treeNode){
            		var typeId=treeNode.typeId;
            		var url="${ctx}/platform/bpm/bpmDefinition/selector.ht?isSingle={0}&validStatus={1}&showAll={2}&typeId={3}";
           			url=String.format(url,isSingle,validStatus,showAll,typeId);
           			
            		$("#bpmDefinitionListFrame").attr("src",url);
            	};
            	//展开收起
            	function treeExpandAll(type){
            		globalType.treeExpandAll(type);
            	};
            	
            	//返回选择的流程定义
            	function selectFlow(){
            		var chIds
            		if(isSingle==true){
    					chIds = $('#bpmDefinitionListFrame').contents().find(":input[name='defId'][checked]");
    				}else{
    					chIds = $("#processList").find(":input[name='defId']");
    				}
            		var defIdArr=new Array();
            		var subjectArr=new Array();
            		var defKeyArr=new Array();
            		$.each(chIds,function(i,ch){
            			var aryTmp=$(ch).val().split("#");
            			defIdArr.push(aryTmp[0]);
            			subjectArr.push(aryTmp[1]);
            			defKeyArr.push(aryTmp[2]);
            		});
            		defIds=defIdArr.join(',');
            		subjects=subjectArr.join(',');
            		defKeys=defKeyArr.join(',');
            		var obj={defIds:defIds,subjects:subjects,defKeys:defKeys};
            		window.returnValue=obj;
            		window.close();
            	};
            	
            	function selectMulti(obj){
            		if ($(obj).attr("checked") == "checked"){
    					var subject = $(obj).siblings(":hidden[name='subject']").val();
    					var defKey = $(obj).siblings(":hidden[name='defKey']").val();
    					var data = $(obj).val()+'#'+subject+'#'+defKey;
    					add(data);
    				}
            	};

    			function selectAll(obj) {
    				var state = $(obj).attr("checked");
    				var rtn=state == undefined?false:true;
    				checkAll(rtn);
    			};
    			
            	function checkAll(checked) {
    				$("#bpmDefinitionListFrame").contents().find("input[type='checkbox'][class='pk']").each(function() {
    					$(this).attr("checked", checked);
    					if (checked) {
    						var subject = $(this).siblings(":hidden[name='subject']").val();
        					var defKey = $(this).siblings(":hidden[name='defKey']").val();
        					var data = $(this).val()+'#'+subject+'#'+defKey;
    						add(data);
    					}
    				});
    			};
    			function dellAll() {
    				$("#processList").html("<tr class='hidden'></tr>");
    			};
    			function del(obj) {
    				var tr = $(obj).parents("tr");
    				$(tr).remove();
    			};
            	function add(data) {
    				
    				var aryTmp=data.split("#");
    				var defId=aryTmp[0];
    				var len= $("#process_" + defId).length;
    				if(len>0) return;
    				
    				var aryData=['<tr id="process_'+defId+'">',
    					'<td>',
    					'<input type="hidden" class="pk" name="defId" value="'+data +'"><span> ',
    					aryTmp[1],
    					'</span></td>',
    					'<td><a onclick="javascript:del(this);" class="link del" ></a> </td>',
    					'</tr>'];
    				$("#processList").append(aryData.join(""));
    			};
            	
            	$(function(){
            		//快速查找
            		var findStr = '';
        			$("input.quick-find").bind('keyup',function(){
        				var str = $(this).val();
        				if(!str)return;
        				if(str==findStr)return;
        				findStr = str;
        				var  tbody = $("#processList"), 	
        					 firstTr = $('tr.hidden',tbody);
        				$("tr",tbody).each(function(){
        					var me = $(this),
        						span = $('span',me),
        						spanStr = span.html();
        					if(!spanStr)return true;						
        					if(spanStr.indexOf(findStr)>-1){
        						$(this).insertAfter(firstTr);
        					}
        				});
        			});
            	});
            	
         </script> 
    </head>
    <body>
      	<div id="defLayout" >            
            <div position="left" title="流程分类" style="overflow: auto;float:left;width:100%">
            	<div class="tree-toolbar">
					<span class="toolBar">
						<div class="group"><a class="link reload" id="treeFresh" href="javascript:globalType.loadGlobalTree();;"><span></span>刷新</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link expand" id="treeExpandAll" href="javascript:treeExpandAll(true)"><span></span>展开</a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link collapse" id="treeCollapseAll" href="javascript:treeExpandAll(false)"><span></span>收起</a></div>
					</span>
				</div>
				<ul id="glTypeTree" class="ztree"></ul>
            </div>
            <div position="center" title="流程定义">
          		<iframe id="bpmDefinitionListFrame" name="bpmDefinitionListFrame" height="100%" width="100%" frameborder="0" ></iframe>
            </div>
            <c:if test="${param.isSingle==false}">
				<div   position="right" title="   <a onclick='javascript:dellAll();' class='link del'>清空 </a><input type='text' class='quick-find' title='查找'/> " style="overflow-y: auto;">
					<table width="145" id="processList" class="table-grid table-list" id="0" cellpadding="1" cellspacing="1">
					<tr class="hidden"></tr>
					</table>
				</div>
			</c:if>
		</div>
		<div position="bottom" class="bottom" style='margin-top:10px;'>
		  	<a href='#' class='button'  onclick="selectFlow()" ><span class="icon ok"></span><span >选择</span></a>
		    <a href='#' class='button' style='margin-left:10px;' onclick="window.close()"><span class="icon cancel"></span><span >取消</span></a>
		</div>
</body>
       
</html>
