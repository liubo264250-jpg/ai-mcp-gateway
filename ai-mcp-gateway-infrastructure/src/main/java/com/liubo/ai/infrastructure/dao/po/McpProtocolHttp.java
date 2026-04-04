package com.liubo.ai.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * HTTP工具注册表
 * @TableName mcp_protocol_http
 */
@TableName(value ="mcp_protocol_http")
@Data
public class McpProtocolHttp {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 协议id
     */
    @TableField(value = "protocol_id")
    private Long protocolId;

    /**
     * HTTP接口地址
     */
    @TableField(value = "http_url")
    private String httpUrl;

    /**
     * HTTP请求方法：GET/POST/PUT/DELETE
     */
    @TableField(value = "http_method")
    private String httpMethod;

    /**
     * HTTP请求头（JSON格式）
     */
    @TableField(value = "http_headers")
    private String httpHeaders;

    /**
     * 超时时间（毫秒）
     */
    @TableField(value = "timeout")
    private Integer timeout;

    /**
     * 重试次数
     */
    @TableField(value = "retry_times")
    private Integer retryTimes;

    /**
     * 状态：0-禁用，1-启用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
}