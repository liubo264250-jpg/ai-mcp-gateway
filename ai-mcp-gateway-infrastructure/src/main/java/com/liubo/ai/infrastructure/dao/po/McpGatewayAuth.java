package com.liubo.ai.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 用户网关权限表
 * @TableName mcp_gateway_auth
 */
@TableName(value ="mcp_gateway_auth")
@Data
public class McpGatewayAuth {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 网关ID
     */
    @TableField(value = "gateway_id")
    private String gatewayId;

    /**
     * API密钥
     */
    @TableField(value = "api_key")
    private String apiKey;

    /**
     * 速率限制（次/小时）
     */
    @TableField(value = "rate_limit")
    private Integer rateLimit;

    /**
     * 过期时间
     */
    @TableField(value = "expire_time")
    private Date expireTime;

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