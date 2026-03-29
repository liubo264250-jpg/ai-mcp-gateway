package com.liubo.ai.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * MCP映射配置表
 * @TableName mcp_protocol_mapping
 */
@TableName(value ="mcp_protocol_mapping")
@Data
public class McpProtocolMapping {
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
     * 所属工具ID
     */
    @TableField(value = "tool_id")
    private Long toolId;

    /**
     * 映射类型：request-请求参数映射，response-响应数据映射
     */
    @TableField(value = "mapping_type")
    private String mappingType;

    /**
     * 父级路径（如：xxxRequest01，用于构建嵌套结构，根节点为NULL）
     */
    @TableField(value = "parent_path")
    private String parentPath;

    /**
     * 字段名称（如：city、company、name）
     */
    @TableField(value = "field_name")
    private String fieldName;

    /**
     * MCP完整路径（如：xxxRequest01.city、xxxRequest01.company.name）
     */
    @TableField(value = "mcp_path")
    private String mcpPath;

    /**
     * MCP数据类型：string/number/boolean/object/array
     */
    @TableField(value = "mcp_type")
    private String mcpType;

    /**
     * MCP字段描述
     */
    @TableField(value = "mcp_desc")
    private String mcpDesc;

    /**
     * 是否必填：0-否，1-是（用于生成required数组）
     */
    @TableField(value = "is_required")
    private Integer isRequired;

    /**
     * HTTP路径（JSON路径，如：company.name 或 data.result，object类型可为空）
     */
    @TableField(value = "http_path")
    private String httpPath;

    /**
     * HTTP位置：body/query/path/header（仅对request类型有效）
     */
    @TableField(value = "http_location")
    private String httpLocation;

    /**
     * 排序顺序（同级字段排序）
     */
    @TableField(value = "sort_order")
    private Integer sortOrder;

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