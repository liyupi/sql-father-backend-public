package com.yupi.sqlfather.core.schema;

import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.sql.ast.statement.SQLPrimaryKey;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlCreateTableParser;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.yupi.sqlfather.common.ErrorCode;
import com.yupi.sqlfather.core.schema.TableSchema.Field;
import com.yupi.sqlfather.core.builder.sql.MySQLDialect;
import com.yupi.sqlfather.core.model.enums.FieldTypeEnum;
import com.yupi.sqlfather.core.model.enums.MockTypeEnum;
import com.yupi.sqlfather.exception.BusinessException;
import com.yupi.sqlfather.model.entity.FieldInfo;
import com.yupi.sqlfather.service.FieldInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 表概要生成器
 *
 * @author https://github.com/liyupi
 */
@Component
@Slf4j
public class TableSchemaBuilder {

    private final static Gson GSON = new Gson();

    private static FieldInfoService fieldInfoService;

    private static final MySQLDialect sqlDialect = new MySQLDialect();

    @Resource
    public void setFieldInfoService(FieldInfoService fieldInfoService) {
        TableSchemaBuilder.fieldInfoService = fieldInfoService;
    }

    /**
     * 日期格式
     */
    private static final String[] DATE_PATTERNS = {"yyyy-MM-dd", "yyyy年MM月dd日", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};

    /**
     * 智能构建
     *
     * @param content
     * @return
     */
    public static TableSchema buildFromAuto(String content) {
        if (StringUtils.isBlank(content)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 切分单词
        String[] words = content.split("[,，]");
        if (ArrayUtils.isEmpty(words) || words.length > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 根据单词去词库里匹配列信息，未匹配到的使用默认值
        QueryWrapper<FieldInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("name", Arrays.asList(words)).or().in("fieldName", Arrays.asList(words));
        List<FieldInfo> fieldInfoList = fieldInfoService.list(queryWrapper);
        // 名称 => 字段信息
        Map<String, List<FieldInfo>> nameFieldInfoMap = fieldInfoList.stream().collect(Collectors.groupingBy(FieldInfo::getName));
        // 字段名称 => 字段信息
        Map<String, List<FieldInfo>> fieldNameFieldInfoMap = fieldInfoList.stream().collect(Collectors.groupingBy(FieldInfo::getFieldName));
        TableSchema tableSchema = new TableSchema();
        tableSchema.setTableName("my_table");
        tableSchema.setTableComment("自动生成的表");
        List<Field> fieldList = new ArrayList<>();
        for (String word : words) {
            Field field;
            List<FieldInfo> infoList = Optional.ofNullable(nameFieldInfoMap.get(word))
                    .orElse(fieldNameFieldInfoMap.get(word));
            if (CollectionUtils.isNotEmpty(infoList)) {
                field = GSON.fromJson(infoList.get(0).getContent(), Field.class);
            } else {
                // 未匹配到的使用默认值
                field = getDefaultField(word);
            }
            fieldList.add(field);
        }
        tableSchema.setFieldList(fieldList);
        return tableSchema;
    }

    /**
     * 根据建表 SQL 构建
     *
     * @param sql 建表 SQL
     * @return 生成的 TableSchema
     */
    public static TableSchema buildFromSql(String sql) {
        if (StringUtils.isBlank(sql)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        try {
            // 解析 SQL
            MySqlCreateTableParser parser = new MySqlCreateTableParser(sql);
            SQLCreateTableStatement sqlCreateTableStatement = parser.parseCreateTable();
            TableSchema tableSchema = new TableSchema();
            tableSchema.setDbName(sqlCreateTableStatement.getSchema());
            tableSchema.setTableName(sqlDialect.parseTableName(sqlCreateTableStatement.getTableName()));
            String tableComment = null;
            if (sqlCreateTableStatement.getComment() != null) {
                tableComment = sqlCreateTableStatement.getComment().toString();
                if (tableComment.length() > 2) {
                    tableComment = tableComment.substring(1, tableComment.length() - 1);
                }
            }
            tableSchema.setTableComment(tableComment);
            List<Field> fieldList = new ArrayList<>();
            // 解析列
            for (SQLTableElement sqlTableElement : sqlCreateTableStatement.getTableElementList()) {
                // 主键约束
                if (sqlTableElement instanceof SQLPrimaryKey) {
                    SQLPrimaryKey sqlPrimaryKey = (SQLPrimaryKey) sqlTableElement;
                    String primaryFieldName = sqlDialect.parseFieldName(sqlPrimaryKey.getColumns().get(0).toString());
                    fieldList.forEach(field -> {
                        if (field.getFieldName().equals(primaryFieldName)) {
                            field.setPrimaryKey(true);
                        }
                    });
                } else if (sqlTableElement instanceof SQLColumnDefinition) {
                    // 列
                    SQLColumnDefinition columnDefinition = (SQLColumnDefinition) sqlTableElement;
                    Field field = new Field();
                    field.setFieldName(sqlDialect.parseFieldName(columnDefinition.getNameAsString()));
                    field.setFieldType(columnDefinition.getDataType().toString());
                    String defaultValue = null;
                    if (columnDefinition.getDefaultExpr() != null) {
                        defaultValue = columnDefinition.getDefaultExpr().toString();
                    }
                    field.setDefaultValue(defaultValue);
                    field.setNotNull(columnDefinition.containsNotNullConstaint());
                    String comment = null;
                    if (columnDefinition.getComment() != null) {
                        comment = columnDefinition.getComment().toString();
                        if (comment.length() > 2) {
                            comment = comment.substring(1, comment.length() - 1);
                        }
                    }
                    field.setComment(comment);
                    field.setPrimaryKey(columnDefinition.isPrimaryKey());
                    field.setAutoIncrement(columnDefinition.isAutoIncrement());
                    String onUpdate = null;
                    if (columnDefinition.getOnUpdate() != null) {
                        onUpdate = columnDefinition.getOnUpdate().toString();
                    }
                    field.setOnUpdate(onUpdate);
                    field.setMockType(MockTypeEnum.NONE.getValue());
                    fieldList.add(field);
                }
            }
            tableSchema.setFieldList(fieldList);
            return tableSchema;
        } catch (Exception e) {
            log.error("SQL 解析错误", e);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请确认 SQL 语句正确");
        }
    }

    /**
     * 根据 Excel 文件构建
     *
     * @param file Excel 文件
     * @return 生成的 TableSchema
     */
    public static TableSchema buildFromExcel(MultipartFile file) {
        try {
            List<Map<Integer, String>> dataList = EasyExcel.read(file.getInputStream()).sheet().headRowNumber(0).doReadSync();
            if (CollectionUtils.isEmpty(dataList)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "表格无数据");
            }
            // 第一行为表头
            Map<Integer, String> map = dataList.get(0);
            List<Field> fieldList = map.values().stream().map(name -> {
                Field field = new Field();
                field.setFieldName(name);
                field.setComment(name);
                field.setFieldType(FieldTypeEnum.TEXT.getValue());
                return field;
            }).collect(Collectors.toList());
            // 第二行为值
            if (dataList.size() > 1) {
                Map<Integer, String> dataMap = dataList.get(1);
                for (int i = 0; i < fieldList.size(); i++) {
                    String value = dataMap.get(i);
                    // 根据值判断类型
                    String fieldType = getFieldTypeByValue(value);
                    fieldList.get(i).setFieldType(fieldType);
                }
            }
            TableSchema tableSchema = new TableSchema();
            tableSchema.setFieldList(fieldList);
            return tableSchema;
        } catch (Exception e) {
            log.error("buildFromExcel error", e);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "表格解析错误");
        }
    }

    /**
     * 判断字段类型
     *
     * @param value
     * @return
     */
    public static String getFieldTypeByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return FieldTypeEnum.TEXT.getValue();
        }
        // 布尔
        if ("false".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value)) {
            return FieldTypeEnum.TINYINT.getValue();
        }
        // 整数
        if (StringUtils.isNumeric(value)) {
            long number = Long.parseLong(value);
            if (number > Integer.MAX_VALUE) {
                return FieldTypeEnum.BIGINT.getValue();
            }
            return FieldTypeEnum.INT.getValue();
        }
        // 小数
        if (isDouble(value)) {
            return FieldTypeEnum.DOUBLE.getValue();
        }
        // 日期
        if (isDate(value)) {
            return FieldTypeEnum.DATETIME.getValue();
        }
        return FieldTypeEnum.TEXT.getValue();
    }

    /**
     * 判断字符串是不是 double 型
     *
     * @param str
     * @return
     */
    private static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("[0-9]+[.]{0,1}[0-9]*[dD]{0,1}");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 判断是否为日期
     *
     * @param str
     * @return
     */
    private static boolean isDate(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        try {
            DateUtils.parseDate(str, DATE_PATTERNS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取默认字段
     *
     * @param word
     * @return
     */
    private static Field getDefaultField(String word) {
        final Field field = new Field();
        field.setFieldName(word);
        field.setFieldType("text");
        field.setDefaultValue("");
        field.setNotNull(false);
        field.setComment(word);
        field.setPrimaryKey(false);
        field.setAutoIncrement(false);
        field.setMockType("");
        field.setMockParams("");
        field.setOnUpdate("");
        return field;
    }

}
