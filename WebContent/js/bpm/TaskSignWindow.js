//任务会签窗口  依赖于LigerWindow 
function TaskSignWindow(conf)
{
	var win=null;
	if(!conf) conf={};
	var defSetting={
		buttons:[
		         {
		        	 text:"确定",
		        	 handler:function()
		        	 {
		        		 var form=$('#signTaskForm');
		        		 if(form!=null)
		        		 {
		        			 var isAgree=$('#signTaskForm :radio[name="isAgree"]:checked').val();
		        			 var content=$('#signTaskForm textarea[name="content"]').val();
		        			
		        			 $.post(conf.postUrl,
		        			 {
		        				 isAgree:isAgree,
		        				 content:content
			        		 },function(){
			        			 $.ligerMessageBox.success('操作成功','你成功进行了会签投票!');
			        			 if(win) win.close();
			        		 });
		        		 }
		        		 else
		        		 {
		        			 if(win) win.close();
		        		 }
		        	 }
		         }
		       ],
		       onReady:function(){
		    	  //TODO
		       }
	};
	jQuery.extend(defSetting,conf);
	win=new HtWindow(defSetting);
	return win;
};

