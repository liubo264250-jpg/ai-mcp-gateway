package com.liubo.ai.domain.session.service.message.handler.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.liubo.ai.domain.session.adapter.port.ISessionPort;
import com.liubo.ai.domain.session.adapter.repository.ISessionRepository;
import com.liubo.ai.domain.session.model.valobj.session.McpSchemaVO;
import com.liubo.ai.domain.session.model.valobj.gateway.McpToolProtocolConfigVO;
import com.liubo.ai.domain.session.service.message.handler.IRequestHandler;
import com.liubo.ai.types.constant.McpErrorCodes;
import com.liubo.ai.types.enums.ResponseCode;
import com.liubo.ai.types.execption.AppException;
import jakarta.annotation.Resource;
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
    @Resource
    private ISessionRepository repository;

    @Resource
    private ISessionPort port;

    @Override
    public McpSchemaVO.JSONRPCResponse handler(String gatewayId, McpSchemaVO.JSONRPCRequest message) {
        try {
            McpSchemaVO.CallToolRequest callToolRequest =
                    McpSchemaVO.unmarshalFrom(message.params(), new TypeReference<>() {
                    });
            Object argumentsObj = callToolRequest.arguments();
            String toolName = callToolRequest.name();
            McpToolProtocolConfigVO mcpToolProtocolConfigVO = repository.queryMcpGatewayProtocolConfig(gatewayId, toolName);
            if (null == mcpToolProtocolConfigVO) {
                throw new AppException(ResponseCode.METHOD_NOT_FOUND.getCode(), ResponseCode.METHOD_NOT_FOUND.getInfo());
            }

            Object result = port.toolCall(mcpToolProtocolConfigVO.getHttpConfig(), argumentsObj);
            return new McpSchemaVO.JSONRPCResponse(McpSchemaVO.JSONRPC_VERSION, message.id(), Map.of(
                    "content", new Object[]{
                            Map.of(
                                    "type", "text",
                                    "text", result
                            ),

                    },
                    "isError", "false"
            ), null);
        } catch (Exception e) {
            return new McpSchemaVO.JSONRPCResponse(McpSchemaVO.JSONRPC_VERSION,
                    message.id(),
                    null,
                    new McpSchemaVO.JSONRPCResponse.JSONRPCError(McpErrorCodes.INVALID_PARAMS, e.getMessage(), null));
        }
    }
}
