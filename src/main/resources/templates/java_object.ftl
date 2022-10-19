<#-- Java 对象模板 -->
${className} ${objectName} = new ${className}();
<#-- 循环生成字段 ---------->
<#list fieldList as field>
${objectName}.${field.setMethod}(${field.value});
</#list>
