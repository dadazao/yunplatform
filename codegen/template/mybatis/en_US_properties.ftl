#start_${mainTable.variables.class?uncap_first}
<#list mainTable.columns as col>
${mainTable.variables.class?uncap_first}.${col.colName}=${col.enName!col.colName}
</#list>
#end_${mainTable.variables.class?uncap_first}