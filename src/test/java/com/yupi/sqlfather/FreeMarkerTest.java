package com.yupi.sqlfather;

import com.yupi.sqlfather.core.model.dto.JavaEntityGenerateDTO;
import com.yupi.sqlfather.core.model.dto.JavaEntityGenerateDTO.FieldDTO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * FreeMarker 测试
 *
 * @author https://github.com/liyupi
 */
public class FreeMarkerTest {

    @Test
    void test() throws IOException, TemplateException {
        // 配置
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);

        // 传递参数
        JavaEntityGenerateDTO javaEntityGenerateDTO = new JavaEntityGenerateDTO();
        javaEntityGenerateDTO.setClassName("Test");
        javaEntityGenerateDTO.setClassComment("测试");
        List<FieldDTO> fieldList = new ArrayList<>();
        FieldDTO field = new FieldDTO();
        field.setComment("字段注释");
        field.setJavaType("String");
        field.setFieldName("testFieldName");
        fieldList.add(field);
        javaEntityGenerateDTO.setFieldList(fieldList);
        StringWriter stringWriter = new StringWriter();
        Template temp = cfg.getTemplate("java_entity.ftl");
        temp.process(javaEntityGenerateDTO, stringWriter);
        System.out.println(stringWriter);
    }
}
