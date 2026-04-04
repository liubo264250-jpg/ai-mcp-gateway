package com.liubo.ai.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * MCP工具表
 * @TableName mcp_gateway_tool
 */
@TableName(value ="mcp_gateway_tool")
@Data
public class McpGatewayTool {
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
     * 协议id
     */
    @TableField(value = "protocol_id")
    private Long protocolId;

    /**
     * 协议类型 http/dubbo
     */
    @TableField(value = "protocol_type")
    private String protocolType;

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