	function selectAll(obj, cName) {
		//var checkboxs = document.getElementsByName(cName);
		var checkboxs = $("input[name='"+cName+"']", navTab.getCurrentPanel());
		if(checkboxs.length == 0 ){
			alertMsg.warn("当前列表无数据!");
			$(obj).attr('checked', false);
			return false;
		}
		for ( var i = 0; i < checkboxs.length; i++) {
			if(checkboxs[i].disabled == false) {
				checkboxs[i].checked = obj.checked;	
			}
		}
	}
	
	function dialogSelectAll(obj, cName) {
		var checkboxs = $("input[name='"+cName+"']", $.pdialog.getCurrent());
		//var checkboxs = document.getElementsByName(cName);
		if(checkboxs.length == 0 ){
			alertMsg.warn("当前列表无数据!");
			$(obj).attr('checked', false);
			return false;
		}
		for ( var i = 0; i < checkboxs.length; i++) {
			if(checkboxs[i].disabled == false) {
				checkboxs[i].checked = obj.checked;	
			}
		}
	}
	
	function showQuery(flag) {
		if(flag==0) {
			$("#defaultQuery").show();
			$("#advanceQuery").hide();
			$(".gridScroller").height(($(".gridScroller").height()+$("#advanceQuery").height() - 40 )+"px");
		}else if(flag==1){
			$("#defaultQuery").hide();
			$("#advanceQuery").show();
			$(".gridScroller").height(($(".gridScroller").height()-$("#advanceQuery").height() + 40 )+"px");
		}
	}
	
	function yunArt(result){
		var items = $("input[name='selectedIDs']:checked").length;//
		if(items == 0){
			art.dialog( {
				title : "错误提示",
				lock : true,
				icon : 'error',
				width : 460,
				height : 100,
				content : "请选择要删除的数据!",
				cancelValue : '确定',
				cancel : function() {
				}
			});
			return;
		}
		art.dialog( {
			title : "数据删除",
			lock : true,
			icon : 'question',
			width : 460,
			height : 100,
			content : "你确定要删除吗?",
			okValue : '确定',
			cancelValue : '取消',
			ok : function() {
				ajaxTodo(result);
			},
			cancel : function() {
			}
		});
	}
	
	function yunLhgdialog(result){
		$.dialog({ 
		    titl: '数据删除', 
		    content: '你确定要删除此数据吗?',
		    icon: 'alert.gif',
		    width : 350,
			height : 100,
		    ok: function(){ 
		        ajaxTodo(result);
		    }, 
		    cancelVal: '关闭', 
		    cancel: true /*为true等价于function(){}*/ 
		});
	};
	
	function showOrderDiv(columnName){
		var orderDivAsc = $("#orderdivasc_"+columnName);
		var orderDivDesc = $("#orderdivdesc_"+columnName);
		if(orderDivAsc!=undefined){
			orderDivAsc.show();
		}
		if(orderDivDesc!=undefined){
			orderDivDesc.show()
		}
	}
	
	function hideOrderDiv(columnName){
		var orderDivAsc = $("#orderdivasc_"+columnName);
		var orderDivDesc = $("#orderdivdesc_"+columnName);
		if(orderDivAsc!=undefined){
			orderDivAsc.hide();
		}
		if(orderDivDesc!=undefined){
			orderDivDesc.hide();
		}
	}