<#-- Typescript 类型生成模板 -->
/**
 * ${classComment}
 */
interface ${className} {
<#-- 循环生成字段 ---------->
<#list fieldList as field>
  <#if field.comment??>
    // ${field.comment}
  </#if>
  ${field.fieldName}: ${field.typescriptType};
</#list>
}
