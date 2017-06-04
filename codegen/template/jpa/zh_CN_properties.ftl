#start_${mainTable.variables.class?uncap_first}
<#list mainTable.columns as col>
${mainTable.variables.class?uncap_first}.${col.colName}=${col.chName}
</#list>
#end_${mainTable.variables.class?uncap_first}