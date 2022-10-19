package com.yupi.sqlfather.model.dto;

import com.yupi.sqlfather.common.PageRequest;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询请求
 *
 * @author https://github.com/liyupi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReportQueryRequest extends PageRequest implements Serializable {

    /**
     * 内容
     */
    private String content;

    /**
     * 举报实体类型（0-词库）
     */
    private Integer type;

    /**
     * 被举报对象 id
     */
    private Long reportedId;

    /**
     * 被举报用户 id
     */
    private Long reportedUserId;

    /**
     * 状态（0-未处理, 1-已处理）
     */
    private Integer status;

    /**
     * 创建用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}