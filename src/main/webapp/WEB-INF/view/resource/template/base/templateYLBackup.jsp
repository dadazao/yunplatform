<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
	$(function(){
		$.ajaxSetup( {async : false});
		$('#yunDialog1').attr('style', 'height: 100%;OVERFLOW-x:hidden;OVERFLOW-Y:auto;');
		if($('#templateDesignDiv').html() != undefined){
			setTemplateView();
		}
		
		var _tds = 0;
		$.each($('#templateViewTd tr:eq(0)').children(),function (entryIndex,entry){
			if($(entry).attr('colspan') != undefined){
				_tds += parseInt($(entry).attr('colspan'));
			}else{
				_tds++;
			}
		});
		var m=0,n=4;
		$.each($('#templateViewTd td'),function (entryIndex,entry){
			if($('#compexDomainTabEdit_label_t'+entryIndex).html() == ''){
				$('#compexDomainTabEdit_label_t'+entryIndex).html('字段'+entryIndex);
			}
			if($('#compexDomainTabEdit_value_t'+entryIndex).html()!=null){
				if(_tds==2){
					$('#compexDomainTabEdit_value_t'+entryIndex).html($('#tempaletYl_'+m).html());
					m++;
					if(m>5){m=0;}
				}else{
					if($('#compexDomainTabEdit_value_t'+entryIndex).parent().attr('colspan')==(_tds-1) || _tds==2){
					$('#compexDomainTabEdit_value_t'+entryIndex).html($('#tempaletYl_'+n).html());
					n++;
					}else{
						$('#compexDomainTabEdit_value_t'+entryIndex).html($('#tempaletYl_'+m).html());
						m++;
					}
					if(m>3){m=0;}
					if(n>5){n=4;}
				}
			}
		})
		$('#templateYl').html($('#templateViewTd').html());
		//$('#templateViewTd').html('');
		
		$.ajaxSetup( {async : true});
		for(var m=0;m<6;m++){
			$('#tempaletYl_'+m).hide();
		}
		ns.common.mouseForButton();
	});
	
</script>
</head>
<body>
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
	<div id="tempaletYl_4"><textarea style="width:99%;" rows="10" ></textarea></div>
	<div id="tempaletYl_5"><textarea class="editor textInput" name="platformtest_details" rows="10" style="width: 99.5%; display: none;"></textarea></div>
</body>
</html>