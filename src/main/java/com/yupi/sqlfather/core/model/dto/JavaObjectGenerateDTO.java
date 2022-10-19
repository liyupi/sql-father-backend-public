package com.yupi.sqlfather.core.model.dto;

import java.util.List;
import lombok.Data;

/**
 * Java 对象生成封装类
 *
 * @author https://github.com/liyupi
 */
@Data
public class JavaObjectGenerateDTO {

    /**
     * 类名
     */
    private String className;

    /**
     * 对象名
     */
    private String objectName;

    /**
     * 列信息列表
     */
    private List<FieldDTO> fieldList;

    /**
     * 列信息
     */
    @Data
    public static class FieldDTO {
        /**
         * set 方法名
         */
        private String setMethod;

        /**
         * 值
         */
        private String value;
    }

}
