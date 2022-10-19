package com.yupi.sqlfather.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.yupi.sqlfather.common.ErrorCode;
import com.yupi.sqlfather.exception.BusinessException;
import com.yupi.sqlfather.mapper.DictMapper;
import com.yupi.sqlfather.model.entity.Dict;
import com.yupi.sqlfather.model.enums.ReviewStatusEnum;
import com.yupi.sqlfather.service.DictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author https://github.com/liyupili
 * @description 针对表【dict(词条)】的数据库操作Service实现
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    private final static Gson GSON = new Gson();

    @Override
    public void validAndHandleDict(Dict dict, boolean add) {
        if (dict == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String content = dict.getContent();
        String name = dict.getName();
        Integer reviewStatus = dict.getReviewStatus();
        // 创建时，所有参数必须非空
        if (add && StringUtils.isAnyBlank(name, content)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isNotBlank(name) && name.length() > 30) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
        if (StringUtils.isNotBlank(content)) {
            if (content.length() > 20000) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
            }
            // 对 content 进行转换
            try {
                String[] words = content.split("[,，]");
                // 移除开头结尾空格
                for (int i = 0; i < words.length; i++) {
                    words[i] = words[i].trim();
                }
                // 过滤空单词
                List<String> wordList = Arrays.stream(words)
                        .filter(StringUtils::isNotBlank)
                        .collect(Collectors.toList());
                dict.setContent(GSON.toJson(wordList));
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容格式错误");
            }
        }
        if (reviewStatus != null && !ReviewStatusEnum.getValues().contains(reviewStatus)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }
}




