<#-- Java 实体模板 -->
import lombok.Data;

/**
 * ${classComment}
 */
@Data
public class ${className} implements Serializable {

    <#-- 序列化 -->
    private static final long serialVersionUID = 1L;

<#-- 循环生成字段 ---------->
<#list fieldList as field>
    <#if field.comment!?length gt 0>
    /**
     * ${field.comment}
     */
    </#if>
    private ${field.javaType} ${field.fieldName};

</#list>
}
