package com.yupi.sqlfather.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.sqlfather.model.entity.FieldInfo;

/**
 * @author https://github.com/liyupili
 * @description 针对表【field_info】的数据库操作Service
 */
public interface FieldInfoService extends IService<FieldInfo> {

    /**
     * 校验并处理
     *
     * @param fieldInfo
     * @param add 是否为创建校验
     */
    void validAndHandleFieldInfo(FieldInfo fieldInfo, boolean add);
}
