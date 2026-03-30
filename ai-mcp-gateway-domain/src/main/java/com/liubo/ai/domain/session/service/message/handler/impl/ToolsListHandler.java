package com.liubo.ai.domain.session.service.message.handler.impl;

import cn.hutool.core.util.StrUtil;
import com.liubo.ai.domain.session.adapter.repository.ISessionRepository;
import com.liubo.ai.domain.session.model.valobj.McpSchemaVO;
import com.liubo.ai.domain.session.model.valobj.gateway.McpGatewayConfigVO;
import com.liubo.ai.domain.session.model.valobj.gateway.McpGatewayToolConfigVO;
import com.liubo.ai.domain.session.service.message.handler.IRequestHandler;
import com.liubo.ai.types.execption.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.liubo.ai.types.enums.ResponseCode.CONFIG_NOT_EXIST;
import static com.liubo.ai.types.enums.ResponseCode.TOOL_NOT_EXIST;

/**
 * @author 68
 * 2026/3/27 22:47
 */
@Service
@Slf4j
public class ToolsListHandler implements IRequestHandler {

    @Autowired
    private ISessionRepository sessionRepository;

    @Override
    public McpSchemaVO.JSONRPCResponse handler(String gatewayId, McpSchemaVO.JSONRPCRequest message) {
        McpGatewayConfigVO mcpGatewayConfigVO = sessionRepository.queryMcpGatewayConfigByGatewayId(gatewayId);
        if (mcpGatewayConfigVO == null) {
            throw new AppException(CONFIG_NOT_EXIST.getCode(), CONFIG_NOT_EXIST.getInfo());
        }
        List<McpGatewayToolConfigVO> mcpGatewayToolConfigVOList = sessionRepository.queryMcpGatewayToolConfigListByGatewayId(gatewayId);
        if (CollectionUtils.isEmpty(mcpGatewayToolConfigVOList)) {
            throw new AppException(TOOL_NOT_EXIST.getCode(), TOOL_NOT_EXIST.getInfo());
        }
        List<McpSchemaVO.Tool> tools = buildTools(mcpGatewayConfigVO, mcpGatewayToolConfigVOList);

        return new McpSchemaVO.JSONRPCResponse("2.0", message.id(), Map.of("tools", tools), null);
    }

    private List<McpSchemaVO.Tool> buildTools(McpGatewayConfigVO gatewayConfig, List<McpGatewayToolConfigVO> toolConfigs) {
        // 1. 通过 toolId 一组组转换为 Map 结构
        Map<Long, List<McpGatewayToolConfigVO>> toolsMap = toolConfigs.stream()
                .collect(Collectors.groupingBy(McpGatewayToolConfigVO::getToolId));

        List<McpSchemaVO.Tool> tools = new ArrayList<>();

        for (Map.Entry<Long, List<McpGatewayToolConfigVO>> entry : toolsMap.entrySet()) {
            Long toolId = entry.getKey();
            List<McpGatewayToolConfigVO> configs = entry.getValue();

            // 排序 (C. configs 按 sortOrder 排序)
            configs.sort(Comparator.comparingInt(o -> o.getSortOrder() != null ? o.getSortOrder() : 0));

            // 父子元素 Map parentPath -> List<Children> (D. 拆分树结构)
            Map<String, List<McpGatewayToolConfigVO>> childrenMap = new HashMap<>();
            List<McpGatewayToolConfigVO> roots = new ArrayList<>();

            for (McpGatewayToolConfigVO config : configs) {
                if (StrUtil.isBlank(config.getParentPath())) {
                    roots.add(config);
                } else {
                    childrenMap.computeIfAbsent(config.getParentPath(), k -> new ArrayList<>()).add(config);
                }
            }

            // E. roots 按 sortOrder 排序
            roots.sort(Comparator.comparingInt(o -> o.getSortOrder() != null ? o.getSortOrder() : 0));

            // 构建输入结构 (F. 生成 inputSchema.properties + required)
            Map<String, Object> properties = new LinkedHashMap<>();
            List<String> required = new ArrayList<>();

            for (McpGatewayToolConfigVO root : roots) {
                properties.put(root.getFieldName(), buildProperty(root, childrenMap));
                if (Objects.equals(1, root.getIsRequired())) {
                    required.add(root.getFieldName());
                }
            }

            // G. 获取类型 (roots.size == 1 ? roots[0].mcpType : object)
            String type = roots.size() == 1 ? roots.get(0).getMcpType() : "object";

            // H. new JsonSchema(type, properties, required, additionalProperties=false)
            McpSchemaVO.JsonSchema inputSchema = new McpSchemaVO.JsonSchema(
                    type,
                    properties,
                    required.isEmpty() ? null : required,
                    false,
                    null,
                    null
            );

            // I. 工具描述
            String name = "unknown-tool-" + toolId;
            String desc = "";
            if (gatewayConfig != null && Objects.equals(gatewayConfig.getToolId(), toolId)) {
                name = gatewayConfig.getToolName();
                desc = gatewayConfig.getToolDescription();
            }

            tools.add(new McpSchemaVO.Tool(name, desc, inputSchema));
        }
        return tools;
    }

    private Map<String, Object> buildProperty(McpGatewayToolConfigVO current, Map<String, List<McpGatewayToolConfigVO>> childrenMap) {
        Map<String, Object> property = new LinkedHashMap<>();
        property.put("type", current.getMcpType());
        if (StrUtil.isNotBlank(current.getMcpDesc())) {
            property.put("description", current.getMcpDesc());
        }

        // 校验孩子元素
        List<McpGatewayToolConfigVO> children = childrenMap.get(current.getMcpPath());
        if (!CollectionUtils.isEmpty(children)) {
            Map<String, Object> props = new LinkedHashMap<>();
            List<String> reqs = new ArrayList<>();

            // 排序 (按 sortOrder 排序)
            children.sort(Comparator.comparingInt(o -> o.getSortOrder() != null ? o.getSortOrder() : 0));

            for (McpGatewayToolConfigVO child : children) {
                // 注意，buildProperty 嵌套递归，一层层的寻找，是否还有孩子元素（children）
                props.put(child.getFieldName(), buildProperty(child, childrenMap));
                if (Objects.equals(1, child.getIsRequired())) {
                    reqs.add(child.getFieldName());
                }
            }

            property.put("properties", props);
            if (!reqs.isEmpty()) {
                property.put("required", reqs);
            }
            // 嵌套对象也增加 additionalProperties=false 限制
            if ("object".equals(current.getMcpType())) {
                property.put("additionalProperties", false);
            }
        }
        return property;
    }
}
