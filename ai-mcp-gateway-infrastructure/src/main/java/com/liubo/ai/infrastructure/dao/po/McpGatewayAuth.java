package com.liubo.ai.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName mcp_gateway_auth
 */
@TableName(value ="mcp_gateway_auth")
@Data
public class McpGatewayAuth {
    private Long id;

    private String gatewayId;

    private String apiKey;

    private Integer rateLimit;

    private Date expireTime;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}