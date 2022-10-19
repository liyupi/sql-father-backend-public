package com.yupi.sqlfather.core.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

/**
 * 字段类型枚举
 *
 * @author https://github.com/liyupi
 */
public enum FieldTypeEnum {

    TINYINT("tinyint", "Integer", "number"),
    SMALLINT("smallint", "Integer", "number"),
    MEDIUMINT("mediumint", "Integer", "number"),
    INT("int", "Integer", "number"),
    BIGINT("bigint", "Long", "number"),
    FLOAT("float", "Double", "number"),
    DOUBLE("double", "Double", "number"),
    DECIMAL("decimal", "BigDecimal", "number"),
    DATE("date", "Date", "Date"),
    TIME("time", "Time", "Date"),
    YEAR("year", "Integer", "number"),
    DATETIME("datetime", "Date", "Date"),
    TIMESTAMP("timestamp", "Long", "number"),
    CHAR("char", "String", "string"),
    VARCHAR("varchar", "String", "string"),
    TINYTEXT("tinytext", "String", "string"),
    TEXT("text", "String", "string"),
    MEDIUMTEXT("mediumtext", "String", "string"),
    LONGTEXT("longtext", "String", "string"),
    TINYBLOB("tinyblob", "byte[]", "string"),
    BLOB("blob", "byte[]", "string"),
    MEDIUMBLOB("mediumblob", "byte[]", "string"),
    LONGBLOB("longblob", "byte[]", "string"),
    BINARY("binary", "byte[]", "string"),
    VARBINARY("varbinary", "byte[]", "string");

    private final String value;

    private final String javaType;

    private final String typescriptType;

    FieldTypeEnum(String value, String javaType, String typescriptType) {
        this.value = value;
        this.javaType = javaType;
        this.typescriptType = typescriptType;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(FieldTypeEnum::getValue).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static FieldTypeEnum getEnumByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (FieldTypeEnum mockTypeEnum : FieldTypeEnum.values()) {
            if (mockTypeEnum.value.equals(value)) {
                return mockTypeEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getJavaType() {
        return javaType;
    }

    public String getTypescriptType() {
        return typescriptType;
    }
}
