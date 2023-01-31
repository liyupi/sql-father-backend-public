<#-- Typescript 类型生成模板 -->
/**
 * ${classComment}
 */
interface ${className} {
<#-- 循环生成字段 ---------->
<#list fieldList as field>
  // ${field.comment}
  ${field.fieldName}: ${field.typescriptType};
</#list>
}
