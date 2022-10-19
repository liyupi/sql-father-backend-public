package com.yupi.sqlfather.core.generator;

import com.yupi.sqlfather.core.schema.TableSchema.Field;
import java.util.List;

/**
 * 数据生成器
 *
 * @author https://github.com/liyupi
 */
public interface DataGenerator {

    /**
     * 生成
     *
     * @param field 字段信息
     * @param rowNum 行数
     * @return 生成的数据列表
     */
    List<String> doGenerate(Field field, int rowNum);

}
