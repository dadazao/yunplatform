<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <title>流程定义明细</title>
   	<link href="<%=basePath %>/themes/css/jquery.qtip.css" rel="stylesheet" />
   	<link href="<%=basePath %>/themes/css/definition.node.css" rel="stylesheet" />
    <script type="text/javascript" src="<%=basePath %>/js/bpm/SignRuleWindow.js" ></script>
    <script type="text/javascript" src="<%=basePath %>/js/bpm/FlowRuleWindow.js" ></script>
    <script type="text/javascript" src="<%=basePath %>/js/bpm/FlowVarWindow.js" ></script>
    <script type="text/javascript" src="<%=basePath %>/js/bpm/ForkConditionWindow.js" ></script>
    <script type="text/javascript" src="<%=basePath %>/js/bpm/FlowEventWindow.js" ></script>
    <script type="text/javascript" src="<%=basePath %>/js/bpm/FlowForkJoinWindow.js" ></script>
    <script type="text/javascript" src="<%=basePath %>/js/bpm/MobileSetWindow.js" ></script>
    <script type="text/javascript" src="<%=basePath %>/js/bpm/FlowMessageWindow.js" ></script>
    <script type="text/javascript" src="<%=basePath %>/js/bpm/FlowWebServiceWindow.js" ></script>
    <script type="text/javascript" src="<%=basePath %>/js/bpm/FlowReminderWindow.js" ></script>
    <script type="text/javascript" src="<%=basePath %>/js/bpm/BpmNodeUserSetWindow.js" ></script>
    <script type="text/javascript" src="<%=basePath %>/js/bpm/ViewSubFlowWindow.js" ></script>
    <script type="text/javascript" src="<%=basePath %>/js/jquery/jquery.qtip.js" ></script>
    <script type="text/javascript">
    		var defId ="${bpmDefinition.defId}";
    		var actDefId ="${bpmDefinition.actDefId}";
    		var deployId= "${bpmDefinition.actDeployId}";
            var menu;
            //判断参数是否加载
            var isOtherParamFrm=false;
            var currentObj=null;
            $(function (){
            	
            	setContainerHeightWidth();
            	
                 $("div.flowNode").each(function(){                	
                	$(this).bind({mouseenter:function(){
                		currentObj=$(this);
                	}});
                	currentObj=$(this);
                	var type=currentObj.attr("type"),
                	title=currentObj.attr("title"),
                	id=currentObj.attr("id");
                	var items= getItems(id,type);
                	if(!items)return;
                	if(items.length==0)return;
                	var item,html=['<div class="edui-menu-body">'];
                	while(item=items.pop()){
                		if(item.id=='-'){
                			html.push('<div class="edui-menuitem edui-menuseparator"><div class="edui-menuseparator-inner"></div></div>');
                		}
                		else{
                			html.push('<div class="edui-menuitem edui-for-'+item.id+'" onmouseover="$(this).addClass(\'edui-state-hover\')" onmouseout="$(this).removeClass(\'edui-state-hover\')" onclick="clickHandler(this.id)" id="'+item.id+'"><div class="edui-box edui-icon"></div><div class="edui-box edui-label edui-menuitem-label">'+item.text+'</div></div>');
                		}
                	}
                	html.push('</div>');
   			      $(this).qtip({  
   			        content:{
   			        	text:html.join(''),
   			        	title:{
   			        		text:title
   			        		}
   			        	},  
   			        position: {
   			        	at:'center',
   			        	target:'event',
   			        	adjust: {
   			        		x:-15,
   			        		y:-15
   		   				},  
   		   				viewport: $(window)
   			        },
   			        show:{   			        	
	   			     	effect: function(offset) {
	   						$(this).slideDown(200);
	   					}
   			        },   			     	
   			        hide: {
   			        	event:'click mouseleave',
   			        	leave: false,
   			        	fixed:true,
   			        	delay:300
   			        	},  
   			        style: {
   			       	  classes:'ui-tooltip-light ui-tooltip-shadow'
   			        } 			    
   			      });
   			    }); 
              //  var  swidth = ${shapeMeta.width};
              // var width= $(document.body).width();//浏览器当前窗口文档body的高度 
         	  //	if(swidth>width)
     		  //		  $('.panel-container').css('width','${shapeMeta.width}px');
               
            });
            
            function setContainerHeightWidth(){
            	var h=document.documentElement.clientHeight ;
            	var w=$(".panel-container").width();
            	$("#divContainer").height(h-120).width(w);
            	
            	
            }
            
            function getItems(id,type) {
            	var items=[];
            	switch(type){
            		case "startEvent":
            			items= [{id:'flowEvent', text: '事件设置'}];
            			break;
            		/*
            		case "subStartEvent":
            			items= [ {id:'flowEvent', text: '事件设置'}];
            			break;
            		*/
            		case "endEvent":
            			items= [{id:'flowEvent', text: '事件设置' }];
            			break;
            		case "parallelGateway":
            			items= [];
            			break;
            		case "inclusiveGateway":
            		case "exclusiveGateway":
            			items= [{id:'flowCodition', text: '设置分支条件' }];
            			break;
            		case "multiUserTask":
            			items= [{id:'-'},
            			        {id:'flowRule', text: '跳转规则设置' },
                		        {id:'flowVote', text: '会签投票规则设置' },
                		        {id:'-'},
                		        {id:'flowEvent', text: '事件设置' },
                		        {id:'flowDue', text: '任务催办设置' },
                		        {id:'-'},
                		        {id:'flowApproval', text: '常用语设置' },
                		        {id:'informType',text:'通知方式'}];
            			break;
            		case "userTask":
            			if(isFirstNode(id)){
            				items= [{id:'-'},
                    		        {id:'flowRule', text: '跳转规则设置' },
                    		        {id:'flowEvent', text: '事件设置' },
                    		        {id:'-'},
                    		        {id:'flowDue', text: '任务催办设置' },
                    		        {id:'-'},
                    		        {id:'flowApproval', text: '常用语设置' },
                    		        {id:'informType',text:'通知方式'}];
            			}else{
            				items= [{id:'-'},
                    		        {id:'flowRule', text: '跳转规则设置' },
                    		        {id:'flowEvent', text: '事件设置' },
                    		        {id:'-'},
                    		        {id:'flowDue', text: '任务催办设置' },
                    		        {id:'flowForkJoin', text: '流程分发汇总' },
                    		        {id:'-'},
                    		        {id:'flowApproval', text: '常用语设置' },
                    		        {id:'informType',text:'通知方式'}];
            			}
            			break;
            		case "webService":
            			items= [{id:'webServiceSet',text:'WebService设置'}];
            			break;
            		case "email":
            			items= [{id:'flowMessage', text: '消息参数' }];
            			break;
            		case "script":
            			items= [{id:'flowEvent', text: '设置脚本' }];
            			break;
            		case "callActivity":
            			items= [
            			        {id: 'viewSubFlow',text: '查看调用子流程示意图'},
            			        {id: 'flowSet', text: '设置子流程'}];
            			break;
            		case "subProcess":
            			items= [{id: 'flowEvent',text: '事件设置'}];
            			break;
            	}
            	
            
            	return items;
            }
            
            function isFirstNode(id){
            	var isFirst = false;
            	$("input.firstNode").each(function(){
            		var nodeId = $(this).val();
            		if(id==nodeId){
            			isFirst = true;
            		}
            	});
            	return isFirst;
            }

            var signRule;
            var flowRule;
            var forkCondition;

            function clickHandler(itemId){
            	//节点类型
            	var type=currentObj.attr("type");            	
            	//任务id
            	var activitiId=currentObj.attr("id");
            	var activityName=currentObj.attr("title");
            	
            	if(itemId=="flowVote" && type=="multiUserTask"){
            		SignRuleWindow({actDefId:actDefId,activitiId:activitiId,defId:defId});
            	}
            	else if(itemId=="flowRule"){
            		FlowRuleWindow({deployId:deployId,actDefId:actDefId,nodeId:activitiId,nodeName:activityName});
            	}
            	else if(itemId=="webServiceSet"){
            		//alert("webservice设置");
                	FlowWebServiceWindow({actDefId:actDefId,nodeId:activitiId,defId:defId});
            	}
            	else if(itemId=="flowCodition"){
            		ForkConditionWindow({defId:defId,deployId:deployId,nodeId:activitiId});
           		}
            	else if(itemId=="flowMessage"){
            		FlowMessageWindow({actDefId:actDefId,nodeId:activitiId});
            	}
            	else if(itemId=="flowEvent"){
            		//开始事件 后置脚本 type startEvent
            		//结束事件 前置脚本 endEvent
            		//用户任务 多实例任务 前后 userTask  multiUserTask
            		//脚本节点 脚本节点 script
            		FlowEventWindow({type:type,actDefId:actDefId,activitiId:activitiId,defId:defId});
            	}
            	else if(itemId=="flowDue"){
            		FlowReminderWindow({actDefId:actDefId,nodeId:activitiId});
            	}else if(itemId=="flowForkJoin"){
            		FlowForkJoinWindow({actDefId:actDefId,nodeId:activitiId,activityName:activityName});
            	}else if(itemId=="flowApproval"){ // 常用语设置
            		FlowApprovalItemWindow({activitiId:activitiId,defId:defId,actDefId:actDefId});
            	}else if(itemId=="viewSubFlow"){
            		ViewSubFlowWindow({nodeId:activitiId,defId:defId,actDefId:actDefId});
            	}else if(itemId=="informType"){
            		InformTypeWindow({nodeId:activitiId,defId:defId,actDefId:actDefId});
            	}else if(itemId=="flowSet"){
            		FlowSetWindow({nodeId:activitiId,defId:defId,actDefId:actDefId});
            	}
            	
            }
            
            InformTypeWindow = function(d) {
            	var dialogWidth=500;
            	var dialogHeight=300;
            	var options=$.extend({},{dialogWidth:dialogWidth,dialogHeight:dialogHeight,mask:true,resizable:true});
            	if (!d) {
            		d = {};
            	}
            	var url = __basePath + "/pages/third/bpm/bpmDefinition/informType.action?actDefId=" + d.actDefId + "&nodeId=" + d.nodeId;
            	$.pdialog.open(url,"selectInfoDialog","选择表单",{width:options.dialogWidth,height:options.dialogHeight,mask:options.mask,resizable:options.resizable});
            };
            
            function FlowApprovalItemWindow(conf){
            	var dialogWidth=500;
            	var dialogHeight=300;
            	var options=$.extend({},{dialogWidth:dialogWidth,dialogHeight:dialogHeight,mask:true,resizable:true});
            	if(!conf) conf={};
            	var url=__basePath + "/pages/third/bpm/taskApprovalItems/edit.action?nodeId=" + conf.activitiId +"&defId=" + conf.defId	+"&actDefId="+conf.actDefId;
            	$.pdialog.open(url,"selectFlowDialog","选择表单",{width:options.dialogWidth,height:options.dialogHeight,mask:options.mask,resizable:options.resizable});
            }
     </script> 
	<style type="text/css"> 
	     
	    div.flowNode{cursor:pointer;}
	</style>
</head>
<body>
		<div id="divContainer" style="overflow: auto;" >
			<div style=" margin:20px auto 0;position: relative;background:url('<%=basePath%>/bpmImage?deployId=${bpmDefinition.actDeployId}')  no-repeat;width:${shapeMeta.width}px;height:${shapeMeta.height+100}px;">
					${shapeMeta.xml} 
			</div>
		</div>
		<!-- 第一个节点 -->
		<div style="display:none;">
			<c:forEach items="${flowNodeList}" var="flowNode">
				<input type="text" class="firstNode" value="${flowNode.nodeId}"/>
			</c:forEach>
		</div>
</body>
</html>
