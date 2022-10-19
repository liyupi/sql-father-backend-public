package com.yupi.sqlfather.core.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

/**
 * 模拟参数随机类型枚举
 *
 * @author https://github.com/liyupi
 */
public enum MockParamsRandomTypeEnum {

    STRING("字符串"),
    NAME("人名"),
    CITY("城市"),
    URL("网址"),
    EMAIL("邮箱"),
    IP("IP"),
    INTEGER("整数"),
    DECIMAL("小数"),
    UNIVERSITY("大学"),
    DATE("日期"),
    TIMESTAMP("时间戳"),
    PHONE("手机号");

    private final String value;

    MockParamsRandomTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(MockParamsRandomTypeEnum::getValue).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static MockParamsRandomTypeEnum getEnumByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (MockParamsRandomTypeEnum mockTypeEnum : MockParamsRandomTypeEnum.values()) {
            if (mockTypeEnum.value.equals(value)) {
                return mockTypeEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }
}
