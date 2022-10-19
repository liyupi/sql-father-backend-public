package com.yupi.sqlfather.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 举报状态枚举
 *
 * @author https://github.com/liyupi
 */
public enum ReportStatusEnum {

    DEFAULT("未处理", 0),
    HANDLED("已处理", 1);

    private final String text;

    private final int value;

    ReportStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
