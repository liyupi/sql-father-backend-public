package com.yupi.sqlfather.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.sqlfather.annotation.AuthCheck;
import com.yupi.sqlfather.common.BaseResponse;
import com.yupi.sqlfather.common.DeleteRequest;
import com.yupi.sqlfather.common.ErrorCode;
import com.yupi.sqlfather.common.ResultUtils;
import com.yupi.sqlfather.constant.CommonConstant;
import com.yupi.sqlfather.core.GeneratorFacade;
import com.yupi.sqlfather.core.model.vo.GenerateVO;
import com.yupi.sqlfather.core.model.enums.MockTypeEnum;
import com.yupi.sqlfather.core.schema.TableSchema;
import com.yupi.sqlfather.core.schema.TableSchema.Field;
import com.yupi.sqlfather.exception.BusinessException;
import com.yupi.sqlfather.model.dto.DictAddRequest;
import com.yupi.sqlfather.model.dto.DictQueryRequest;
import com.yupi.sqlfather.model.dto.DictUpdateRequest;
import com.yupi.sqlfather.model.entity.Dict;
import com.yupi.sqlfather.model.entity.User;
import com.yupi.sqlfather.model.enums.ReviewStatusEnum;
import com.yupi.sqlfather.service.DictService;
import com.yupi.sqlfather.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 词条接口
 *
 * @author https://github.com/liyupi
 */
@RestController
@RequestMapping("/dict")
@Slf4j
public class DictController {

    @Resource
    private DictService dictService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建
     *
     * @param dictAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addDict(@RequestBody DictAddRequest dictAddRequest, HttpServletRequest request) {
        if (dictAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictAddRequest, dict);
        // 校验
        dictService.validAndHandleDict(dict, true);
        User loginUser = userService.getLoginUser(request);
        dict.setUserId(loginUser.getId());
        boolean result = dictService.save(dict);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(dict.getId());
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteDict(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Dict oldDict = dictService.getById(id);
        if (oldDict == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldDict.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = dictService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param dictUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> updateDict(@RequestBody DictUpdateRequest dictUpdateRequest) {
        if (dictUpdateRequest == null || dictUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictUpdateRequest, dict);
        // 参数校验
        dictService.validAndHandleDict(dict, false);
        long id = dictUpdateRequest.getId();
        // 判断是否存在
        Dict oldDict = dictService.getById(id);
        if (oldDict == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        boolean result = dictService.updateById(dict);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<Dict> getDictById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Dict dict = dictService.getById(id);
        return ResultUtils.success(dict);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param dictQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<Dict>> listDict(DictQueryRequest dictQueryRequest) {
        List<Dict> dictList = dictService.list(getQueryWrapper(dictQueryRequest));
        return ResultUtils.success(dictList);
    }

    /**
     * 分页获取列表
     *
     * @param dictQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<Dict>> listDictByPage(DictQueryRequest dictQueryRequest,
            HttpServletRequest request) {
        long current = dictQueryRequest.getCurrent();
        long size = dictQueryRequest.getPageSize();
        // 限制爬虫
        if (size > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<Dict> dictPage = dictService.page(new Page<>(current, size),
                getQueryWrapper(dictQueryRequest));
        return ResultUtils.success(dictPage);
    }

    /**
     * 获取当前用户可选的全部资源列表（只返回 id 和名称）
     *
     * @param dictQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/my/list")
    public BaseResponse<List<Dict>> listMyDict(DictQueryRequest dictQueryRequest,
            HttpServletRequest request) {
        if (dictQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 先查询所有审核通过的
        dictQueryRequest.setReviewStatus(ReviewStatusEnum.PASS.getValue());
        Dict dictQuery = new Dict();
        QueryWrapper<Dict> queryWrapper = getQueryWrapper(dictQueryRequest);
        final String[] fields = new String[]{"id", "name"};
        queryWrapper.select(fields);
        List<Dict> dictList = dictService.list(queryWrapper);
        // 再查所有本人的
        try {
            User loginUser = userService.getLoginUser(request);
            dictQuery.setReviewStatus(null);
            dictQuery.setUserId(loginUser.getId());
            queryWrapper = new QueryWrapper<>(dictQuery);
            queryWrapper.select(fields);
            dictList.addAll(dictService.list(queryWrapper));
        } catch (Exception e) {
            // 未登录
        }
        // 根据 id 去重
        List<Dict> resultList = dictList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Dict::getId))), ArrayList::new));
        return ResultUtils.success(resultList);
    }

    /**
     * 分页获取当前用户可选的资源列表
     *
     * @param dictQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/my/list/page")
    public BaseResponse<Page<Dict>> listMyDictByPage(DictQueryRequest dictQueryRequest,
            HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        long current = dictQueryRequest.getCurrent();
        long size = dictQueryRequest.getPageSize();
        // 限制爬虫
        if (size > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<Dict> queryWrapper = getQueryWrapper(dictQueryRequest);
        queryWrapper.eq("userId", loginUser.getId())
                .or()
                .eq("reviewStatus", ReviewStatusEnum.PASS.getValue());
        Page<Dict> dictPage = dictService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(dictPage);
    }

    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param dictQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/my/add/list/page")
    public BaseResponse<Page<Dict>> listMyAddDictByPage(DictQueryRequest dictQueryRequest,
            HttpServletRequest request) {
        if (dictQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        dictQueryRequest.setUserId(loginUser.getId());
        long current = dictQueryRequest.getCurrent();
        long size = dictQueryRequest.getPageSize();
        // 限制爬虫
        if (size > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<Dict> dictPage = dictService.page(new Page<>(current, size),
                getQueryWrapper(dictQueryRequest));
        return ResultUtils.success(dictPage);
    }

    // endregion

    /**
     * 生成创建表的 SQL
     *
     * @param id
     * @return
     */
    @PostMapping("/generate/sql")
    public BaseResponse<GenerateVO> generateCreateSql(@RequestBody long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Dict dict = dictService.getById(id);
        if (dict == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 根据词库生成 Schema
        TableSchema tableSchema = new TableSchema();
        String name = dict.getName();
        tableSchema.setTableName("dict");
        tableSchema.setTableComment(name);
        List<Field> fieldList = new ArrayList<>();
        Field idField = new Field();
        idField.setFieldName("id");
        idField.setFieldType("bigint");
        idField.setNotNull(true);
        idField.setComment("id");
        idField.setPrimaryKey(true);
        idField.setAutoIncrement(true);
        Field dataField = new Field();
        dataField.setFieldName("data");
        dataField.setFieldType("text");
        dataField.setComment("数据");
        dataField.setMockType(MockTypeEnum.DICT.getValue());
        dataField.setMockParams(String.valueOf(id));
        fieldList.add(idField);
        fieldList.add(dataField);
        tableSchema.setFieldList(fieldList);
        return ResultUtils.success(GeneratorFacade.generateAll(tableSchema));
    }

    /**
     * 获取查询包装类
     *
     * @param dictQueryRequest
     * @return
     */
    private QueryWrapper<Dict> getQueryWrapper(DictQueryRequest dictQueryRequest) {
        if (dictQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Dict dictQuery = new Dict();
        BeanUtils.copyProperties(dictQueryRequest, dictQuery);
        String sortField = dictQueryRequest.getSortField();
        String sortOrder = dictQueryRequest.getSortOrder();
        String name = dictQuery.getName();
        String content = dictQuery.getContent();
        // name、content 需支持模糊搜索
        dictQuery.setName(null);
        dictQuery.setContent(null);
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>(dictQuery);
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

}
