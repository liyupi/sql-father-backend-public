package com.yupi.sqlfather.core.builder;

import cn.hutool.core.util.StrUtil;
import com.yupi.sqlfather.core.model.dto.TypescriptTypeGenerateDTO;
import com.yupi.sqlfather.core.model.dto.TypescriptTypeGenerateDTO.FieldDTO;
import com.yupi.sqlfather.core.model.enums.FieldTypeEnum;
import com.yupi.sqlfather.core.schema.TableSchema;
import com.yupi.sqlfather.core.schema.TableSchema.Field;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Java 代码生成器
 *
 * @author https://github.com/liyupi
 */
@Component
@Slf4j
public class FrontendCodeBuilder {

    private static Configuration configuration;

    @Resource
    public void setConfiguration(Configuration configuration) {
        FrontendCodeBuilder.configuration = configuration;
    }

    /**
     * 构造 Typescript 类型代码
     *
     * @param tableSchema 表概要
     * @return 生成的代码
     */
    @SneakyThrows
    public static String buildTypeScriptTypeCode(TableSchema tableSchema) {
        // 传递参数
        TypescriptTypeGenerateDTO generateDTO = new TypescriptTypeGenerateDTO();
        String tableName = tableSchema.getTableName();

        String tableComment = tableSchema.getTableComment();
        String upperCamelTableName = StringUtils.capitalize(StrUtil.toCamelCase(tableName));
        // 类名为大写的表名
        generateDTO.setClassName(upperCamelTableName);
        // 类注释为表注释 > 表名
        generateDTO.setClassComment(Optional.ofNullable(tableComment).orElse(upperCamelTableName));
        // 依次填充每一列
        List<FieldDTO> fieldDTOList = new ArrayList<>();
        for (Field field : tableSchema.getFieldList()) {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setComment(field.getComment());
            FieldTypeEnum fieldTypeEnum = Optional.ofNullable(FieldTypeEnum.getEnumByValue(field.getFieldType())).orElse(FieldTypeEnum.TEXT);
            fieldDTO.setTypescriptType(fieldTypeEnum.getTypescriptType());
            fieldDTO.setFieldName(StrUtil.toCamelCase(field.getFieldName()));
            fieldDTOList.add(fieldDTO);
        }
        generateDTO.setFieldList(fieldDTOList);
        StringWriter stringWriter = new StringWriter();
        Template temp = configuration.getTemplate("typescript_type.ftl");
        temp.process(generateDTO, stringWriter);
        return stringWriter.toString();
    }

}
