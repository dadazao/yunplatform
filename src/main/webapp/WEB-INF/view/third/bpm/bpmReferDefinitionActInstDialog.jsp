<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程引用</title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerLayout.js" ></script>
<script type="text/javascript">
		$(function(){
			$("#defLayout").ligerLayout({ 
				leftWidth: 250,
				bottomHeight :40,
				allowRightResize:false,
				allowTopResize:false,
				allowBottomResize:false,
				height: '95%',
				minLeftWidth:250});
			$("#selectRefer").change(function(){
				var _this = $(this);
				if(!_this.val()){
					return;
				}
				var referDefId = _this.val();
				$("#processRunListFrame").contents().find("#btnSearch").trigger("click");
			});
			
			$('#btnClose').click(function(){
				window.close();
			});
			initData();
			var findStr = '';
			//快速查找
			$("input.quick-find").bind('keyup',function(){
				var str = $(this).val();
				if(!str)return;
				if(str==findStr)return;
				findStr = str;
				var  tbody = $("#instList"), 	
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

		//初始化父级窗口传进来的数据
		function initData(){
			var obj = window.dialogArguments;
			if(obj&&obj.length>0){
				for(var i=0,c;c=obj[i++];){
					var data = c.id+'#'+c.name;
					add(data);
				}
			}
		};

		function selectInst(){
			var aryInsts =$("input[name='inst']", $("#instList"));
			if(aryInsts.length==0){
				alert("请选择流程实例!");
				return;
			}
			var aryId=[];
			var aryName=[];
			var json = [];
			aryInsts.each(function(){
				var data=$(this).val();
				var aryInst=data.split("#");
				aryId.push(aryInst[0]);
				aryName.push(aryInst[1]);
				json.push({id:aryInst[0],name:aryInst[1]});
			});
			var instIds=aryId.join(",");
			var instNames=aryName.join(",");
			
			var obj={};
			obj.ids=instIds;
			obj.names=instNames;
			obj.json = json;
			window.returnValue=obj;
			window.close();
		};

		function add(data) {
			data = removeColorSpan(data);
			var aryTmp=data.split("#");
			var instId=aryTmp[0];
		
			var len= $("#inst_" + instId).length;
			if(len>0) return;
			var instTemplate= $("#instTemplate").val();
			
			var html=instTemplate.replace("#instId",instId)
					.replace("#data",data)
					.replace("#name",aryTmp[1]);
			$("#instList").append(html);
		};
		//去除流程标题中控制标题颜色的span标签
		function removeColorSpan(data){
			var div = $("<div></div>");
			div.html(data);
			return div.text();
		}
	
		function selectMulti(obj) {
			if ($(obj).attr("checked") == "checked"){
				var data = $(obj).val();
				add(data);
			}	
		};
		
		function dellAll() {
			$("#instList").empty();
		};
		function del(obj) {
			var tr = $(obj).closest("tr");
			$(tr).remove();
		};
		//清空所选实例
		function clearInst(){
			window.returnValue = {ids:'',names:''};
			window.close();
		}
	</script>
</head>
<body>
	<div id="defLayout">
			<div position="center" style="height:580px;">
				<div id="refer-div">
					<table class="table-detail">
						<tr>
							<th width="20%">引用的流程:</th>
							<td>
								<select id="selectRefer"  style="min-width: 100px">
									<option value="0" selected="selected">--请选择--</option>
									<c:forEach items="${refers}" var="refer">
										<option value="${refer.referDefId}">${refer.subject}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
					</table>
				</div>
				<iframe id="processRunListFrame" name="processRunListFrame" height="95%"
					width="100%" frameborder="0"
					src="${ctx}/platform/bpm/bpmReferDefinition/actInstSelector.ht"></iframe>
			</div>
			
			<div position="right" title="<span><a onclick='javascript:dellAll();' class='link del'>清空 </a><input type='text' class='quick-find' title='查找' style='width:60px;' /></span>" 
			style="overflow: auto;height:94%;width:170px;">
		        <table width="145"   class="table-grid table-list"  cellpadding="1" cellspacing="1">
		        	<tbody id="instList">
		        		<tr class="hidden"></tr>
		        	</tbody>
				</table>
			</div>
	
			<div position="bottom"  class="bottom" style="height:30px;" >
				<a href="#" id="btnSelect" class="button" onclick="selectInst()" style="margin-right:10px;" ><span class="icon ok"></span><span class="chosen">选择</span></a>
				<a href="#" class="button"  onclick="clearInst()"><span class="icon cancel" ></span><span class="chosen" >清空</span></a>
				<a href="#" id="btnClose"  class="button"  onclick="window.close()" style="margin-left:10px;" ><span class="icon cancel" ></span><span class="chosen" >取消</span></a>
			</div>
		</div>
	</div>
	
	<textarea id="instTemplate" style="display: none;">
	  	<tr id="inst_#instId">
	 			<td>
	 				<input type="hidden" name="inst" value="#data"><span>#name</span>
	 			</td>
	 			<td style="width: 30px;" nowrap="nowrap"><a onclick="javascript:del(this);" class="link del" >&nbsp;</a></td>
		 </tr>
    </textarea>
</body>
</html>


