package com.yupi.sqlfather.core.generator;

import com.yupi.sqlfather.core.model.enums.MockParamsRandomTypeEnum;
import com.yupi.sqlfather.core.schema.TableSchema.Field;
import com.yupi.sqlfather.core.utils.FakerUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 随机值数据生成器
 *
 * @author https://github.com/liyupi
 */
public class RandomDataGenerator implements DataGenerator {

    @Override
    public List<String> doGenerate(Field field, int rowNum) {
        String mockParams = field.getMockParams();
        List<String> list = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            MockParamsRandomTypeEnum randomTypeEnum = Optional.ofNullable(
                            MockParamsRandomTypeEnum.getEnumByValue(mockParams))
                    .orElse(MockParamsRandomTypeEnum.STRING);
            String randomString = FakerUtils.getRandomValue(randomTypeEnum);
            list.add(randomString);
        }
        return list;
    }
}
