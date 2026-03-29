package com.liubo.ai.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * MCP工具注册表
 * @TableName mcp_protocol_registry
 */
@TableName(value ="mcp_protocol_registry")
@Data
public class McpProtocolRegistry {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属网关ID
     */
    @TableField(value = "gateway_id")
    private String gatewayId;

    /**
     * 工具ID
     */
    @TableField(value = "tool_id")
    private Long toolId;

    /**
     * MCP工具名称（如：JavaSDKMCPClient_getCompanyEmployee）
     */
    @TableField(value = "tool_name")
    private String toolName;

    /**
     * 工具类型：function/resource
     */
    @TableField(value = "tool_type")
    private String toolType;

    /**
     * 工具描述
     */
    @TableField(value = "tool_description")
    private String toolDescription;

    /**
     * 工具版本
     */
    @TableField(value = "tool_version")
    private String toolVersion;

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