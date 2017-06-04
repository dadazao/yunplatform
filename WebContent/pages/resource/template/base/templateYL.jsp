<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script type="text/javascript">
	$(function(){
		
		try{
			$.ajaxSetup( {async : false});
			$('#yunDialog1').attr('style', 'height: 100%;OVERFLOW-x:hidden;OVERFLOW-Y:auto;');
			var tableHtml = '';
			if($('#tblContent').val() == undefined){
				tableHtml = $('#tblContentView').val();
			}else{
				tableHtml = $('#tblContent').val();
			}
			tableHtml = templateContentHtml(tableHtml);
			$('#templateYl').html(tableHtml);
			var contents = tableHtml.split("<div id='compexDomainTabEdit_label_t");
			var m=0;
			for(var i=0; i<contents.length; i++){
				$('#compexDomainTabEdit_label_t'+(i+1)).html('字段'+(i+1));
				$('#compexDomainTabEdit_value_t'+(i+1)).html($('#tempaletYl_'+m).html());
				if(m<3){
					m++;
				}else{
					m = 0;
				}
			}
			
			$.ajaxSetup( {async : true});
			for(var m=0;m<4;m++){
				$('#tempaletYl_'+m).hide();
			}
			ns.common.mouseForButton();
		}catch(e){}
	});
	
	function templateContentHtml(tblContent){
		var sbdContent = '';
		var tmp = "";
		var flag = false;
		for (var i = 0; i < tblContent.length; i++) {
			if(tblContent.charAt(i)=="】"){
				if(tmp.split(",")[0]=="数据字段"){
					sbdContent+="<div id='compexDomainTabEdit_label_t";
					sbdContent+=tmp.split(",")[1];
					sbdContent+="'></div>";
				}
				if(tmp.split(",")[0]=="数据值"){
					sbdContent+="<div id='compexDomainTabEdit_value_t";
					sbdContent+=tmp.split(",")[1];
					sbdContent+="'></div>";
				}
				tmp = "";
				flag = false;
			}else if(flag){
				tmp += tblContent.charAt(i);
			}else if(tblContent.charAt(i)=="【"){
				flag = true;
			}else{
				sbdContent+=tblContent.charAt(i);
			}
		}
		return sbdContent;
	}
</script>
<div id="yunDialog1">
	<div  class="buttonPanel">
		<button class="listbutton" name="b1" id="XG" type="button">修改</button>
		<button class="listbutton" name="b2" id="BC" type="button">保存</button>
		<button class="listbutton" name="b3" id="DY" type="button">打印</button>
		<button class="listbutton" name="b4" id="BZ" type="button">帮助</button>
	</div>
	<div class="tabs">
      <div class="tabsHeader">
            <div class="tabsHeaderContent">
                  <ul>
                        <li class="selected"><a href="#"><span>基本信息</span></a></li>
                  </ul>
            </div>
      </div>
      <div class="tabsContent" style="height:100%;">
            <div id="templateYl"></div>
      </div>
      <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
      </div>
	</div>
</div>
<div id="tempaletYl_0"><input class=" textInput required" value="" name="injureinfo_name" style="width:140px;height:15px;" maxlength="25"><!-- <span class="star">*</span> --></div>
<div id="tempaletYl_1"><input realvalue="" class="textInput readonly" readonly="true" style="width: px;" id="student_xingming" name="student_xingming" value="" type="text"><img src="js/My97DatePicker/skin/datePicker.gif" style="cursor:pointer" align="absmiddle" height="22" width="16"></div>
<div id="tempaletYl_2"><input type="radio" checked="checked" value="1" name="injureinfo_sex">男<input type="radio" value="0" name="injureinfo_sex">女</div>
<div id="tempaletYl_3"><select style="width:148px;" class="valid"><option value="0">选项1</option><option value="1">选项2</option><option value="2">选项3</option></select></div>