package com.liubo.ai.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * MCP网关配置表
 * @TableName mcp_gateway
 */
@TableName(value ="mcp_gateway")
@Data
public class McpGateway {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 网关唯一标识
     */
    @TableField(value = "gateway_id")
    private String gatewayId;

    /**
     * 网关名称
     */
    @TableField(value = "gateway_name")
    private String gatewayName;

    /**
     * 网关描述
     */
    @TableField(value = "gateway_desc")
    private String gatewayDesc;

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