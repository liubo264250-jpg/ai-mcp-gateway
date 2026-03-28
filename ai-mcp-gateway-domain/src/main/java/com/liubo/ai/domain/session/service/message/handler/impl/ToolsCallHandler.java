package com.liubo.ai.domain.session.service.message.handler.impl;

import com.liubo.ai.domain.session.model.valobj.McpSchemaVO;
import com.liubo.ai.domain.session.service.message.handler.IRequestHandler;
import com.liubo.ai.types.constant.McpErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 68
 * 2026/3/27 22:48
 */
@Service
@Slf4j
public class ToolsCallHandler implements IRequestHandler {
    @Override
    public McpSchemaVO.JSONRPCResponse handler(McpSchemaVO.JSONRPCRequest message) {
        Object id = message.id();
        Object params = message.params();
        if (!(params instanceof Map)) {
            return new McpSchemaVO.JSONRPCResponse("2.0",
                    id,
                    null,
                    new McpSchemaVO.JSONRPCResponse.JSONRPCError(McpErrorCodes.INVALID_PARAMS, "无效参数 - 无效的方法参数", null));
        }

        Map<String, Object> paramsMap = (Map<String, Object>) params;
        String toolName = (String) paramsMap.get("name");
        Object argumentsObj = paramsMap.get("arguments");

        Map<String, Object> arguments = (Map<String, Object>) argumentsObj;
        if ("toUpperCase".equals(toolName)) {
            String word = arguments.get("word").toString();
            return new McpSchemaVO.JSONRPCResponse("2.0",
                    id,
                    Map.of(
                            "content", new Object[]{
                                    Map.of(
                                            "type", "text",
                                            "text", word.toUpperCase()
                                    )
                            }
                    ), null);
        }
        return new McpSchemaVO.JSONRPCResponse("2.0",
                id,
                null,
                new McpSchemaVO.JSONRPCResponse.JSONRPCError(McpErrorCodes.METHOD_NOT_FOUND, "方法未找到 - 方法不存在或不可用", null));
    }
}
