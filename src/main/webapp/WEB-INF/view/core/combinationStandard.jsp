<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
<!--
function combinationStandardHideTr(num) {
	var remain = num%2;
		var row = 0;
		if(remain == 0) {
			row = num/2;
		}else {
			row = Math.floor(num/2) + 1;
		}
		for(var k=row+1; k<=16; k++) {
			$("#compexDomainTabEdit_tr_t" + k).hide();	
		}
}
//-->
</script>
<table>
  <tr>
    <td>
    	<div id="partitionForm1"></div>
    </td>
  </tr>
  <tr>
    <td>
    	<div id="partitionList1"></div>
    </td>
  </tr>
</table>