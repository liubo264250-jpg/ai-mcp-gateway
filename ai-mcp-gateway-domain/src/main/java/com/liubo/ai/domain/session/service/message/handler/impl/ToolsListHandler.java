package com.liubo.ai.domain.session.service.message.handler.impl;

import com.liubo.ai.domain.session.model.valobj.McpSchemaVO;
import com.liubo.ai.domain.session.service.message.handler.IRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 68
 * 2026/3/27 22:47
 */
@Service
@Slf4j
public class ToolsListHandler implements IRequestHandler {
    @Override
    public McpSchemaVO.JSONRPCResponse handler(String gatewayId,McpSchemaVO.JSONRPCRequest message) {
        return new McpSchemaVO.JSONRPCResponse("2.0",
                message.id(),
                Map.of(
                        "tools", new Object[]{
                                Map.of(
                                        "name", "toUpperCase",
                                        "description", "小写转大写",
                                        "inputSchema", Map.of(
                                                "type", "object",
                                                "properties", Map.of(
                                                        "word", Map.of(
                                                                "type", "string",
                                                                "description", "单词，字符串"
                                                        )
                                                ),
                                                "required", new String[]{"word"}
                                        )
                                )
                        }
                ),
                null);
    }
}
