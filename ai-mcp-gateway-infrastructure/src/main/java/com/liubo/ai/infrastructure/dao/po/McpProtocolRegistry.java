package com.liubo.ai.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName mcp_protocol_registry
 */
@TableName(value ="mcp_protocol_registry")
@Data
public class McpProtocolRegistry {
    private Long id;

    private String gatewayId;

    private Long toolId;

    private String toolName;

    private String toolType;

    private String toolDescription;

    private String httpUrl;

    private String httpMethod;

    private String httpHeaders;

    private Integer timeout;

    private Integer retryTimes;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}