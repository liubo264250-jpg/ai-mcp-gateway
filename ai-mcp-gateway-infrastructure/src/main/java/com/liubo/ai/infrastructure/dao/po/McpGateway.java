package com.liubo.ai.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName mcp_gateway
 */
@TableName(value ="mcp_gateway")
@Data
public class McpGateway {
    private Long id;

    private String gatewayId;

    private String gatewayName;

    private String gatewayDesc;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}