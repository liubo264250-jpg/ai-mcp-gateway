package com.liubo.ai.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @TableName mcp_protocol_mapping
 */
@TableName(value ="mcp_protocol_mapping")
@Data
public class McpProtocolMapping {
    private Long id;

    private String gatewayId;

    private Long toolId;

    private String mappingType;

    private String parentPath;

    private String fieldName;

    private String mcpPath;

    private String mcpType;

    private String mcpDesc;

    private Integer isRequired;

    private String httpPath;

    private String httpLocation;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;
}