<%@page import="com.hotent.platform.model.system.GlobalType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.hotent.platform.model.bpm.BpmDefRights"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
    <head>
        <title>流程定义管理</title>
		<%@include file="/commons/include/form.jsp" %>
		<f:link href="tree/zTreeStyle.css" ></f:link>
	    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerMenu.js"></script>
		<script type="text/javascript" src="${ctx}/js/tree/jquery.ztree.js"></script>
		<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerLayout.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/form/GlobalType.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/system/GlobalMenu.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowRightDialog.js"></script>
		<style type="text/css">
			.tree-title{overflow:hidden;width:100%;}
			body{overflow: hidden;}
		</style>	
        <script type="text/javascript">
        		var catKey="<%=GlobalType.CAT_FLOW%>";
        		var flowTypeMenu=new FlowTypeMenu();
        		var curMenu=null;
        		var globalType=new GlobalType(catKey,"glTypeTree",
        				{
        					onClick:onClick,
        					onRightClick:zTreeOnRightClick,
        					url:'${ctx}/platform/system/globalType/getByCatKey.ht',
        					expandByDepth:1
        				});
        		
                $(function (){
                	//布局
                    $("#defLayout").ligerLayout({ leftWidth:210,height: '100%',allowLeftResize:false});
                	//加载菜单 
                    globalType.loadGlobalTree();
                	$(document).click(hiddenMenu);
                });
                
                function hiddenMenu(){
        			if(curMenu){
        				curMenu.hide();
        			}
        		}
                
                function handler(item){
                	hiddenMenu();
                	var txt=item.text;
                	switch(txt){
                		case "增加分类":
                			globalType.openGlobalTypeDlg(true);
                			break;
                		case "编辑分类":
                			globalType.openGlobalTypeDlg(false);
                			break;
                		case "删除分类":
                			globalType.delNode();
                			break;

                	}
                }

            	/**
            	 * 树右击事件
            	 */
            	function zTreeOnRightClick(event, treeId, treeNode) {
            		hiddenMenu();
            		if (treeNode) {
            			globalType.currentNode=treeNode;
            			globalType.glTypeTree.selectNode(treeNode);
            			curMenu=flowTypeMenu.getMenu(treeNode, handler);
            			curMenu.show({ top: event.pageY, left: event.pageX });
            		}
            	};
            	
            	//左击
            	function onClick( treeNode){
            		var typeId=treeNode.typeId;
            		var url="${ctx}/platform/bpm/bpmDefinition/list.ht?typeId="+typeId;
            		$("#defFrame").attr("src",url);
            	};
            	//展开收起
            	function treeExpandAll(type){
            		globalType.treeExpandAll(type);
            	};
            	
            /* 	//对流程分类分配权限。
            	function assignRights(){
            		var node=globalType.currentNode;
            		if(node!=null && node!=undefined){
            			if(node.isRoot==undefined){
            				var typeId=node.typeId;
            				FlowRightDialog(typeId,1,'',node.children?1:0);
            			}
            		}
            	} */
            
         </script> 
    </head>
    <body>
      	<div id="defLayout" class="panel-top">
            <div position="left" title="流程分类管理" style="overflow: auto;float:left;width:100%">
            	<div class="tree-toolbar">
					<span class="toolBar">
						<div class="group"><a class="link reload" id="treeFresh" href="javascript:globalType.loadGlobalTree();" ><span></span></a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link expand" id="treeExpandAll" href="javascript:treeExpandAll(true)" ><span></span></a></div>
						<div class="l-bar-separator"></div>
						<div class="group"><a class="link collapse" id="treeCollapseAll" href="javascript:treeExpandAll(false)" ><span></span></a></div>
					</span>
				</div><ul id="glTypeTree" class="ztree"></ul>
            </div>
            <div position="center" >
          		<iframe id="defFrame" height="100%" width="100%" frameborder="0" src="${ctx}/platform/bpm/bpmDefinition/list.ht"></iframe>
            </div>
        </div>
	
		
    </body>
    </html>
