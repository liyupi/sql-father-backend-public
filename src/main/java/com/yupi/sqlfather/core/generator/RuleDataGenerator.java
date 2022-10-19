package com.yupi.sqlfather.core.generator;

import com.mifmif.common.regex.Generex;
import com.yupi.sqlfather.core.schema.TableSchema.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 正则表达式数据生成器
 *
 * @author https://github.com/liyupi
 */
public class RuleDataGenerator implements DataGenerator {

    @Override
    public List<String> doGenerate(Field field, int rowNum) {
        String mockParams = field.getMockParams();
        List<String> list = new ArrayList<>(rowNum);
        Generex generex = new Generex(mockParams);
        for (int i = 0; i < rowNum; i++) {
            String randomStr = generex.random();
            list.add(randomStr);
        }
        return list;
    }
}
