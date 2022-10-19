package com.yupi.sqlfather.core.builder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据 JSON 生成器
 *
 * @author https://github.com/liyupi
 */
@Slf4j
public class JsonBuilder {

    /**
     * 构造数据 json
     * e.g. {"id": 1}
     *
     * @param dataList 数据列表
     * @return 生成的 json 数组字符串
     */
    public static String buildJson(List<Map<String, Object>> dataList) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(dataList);
    }
}
