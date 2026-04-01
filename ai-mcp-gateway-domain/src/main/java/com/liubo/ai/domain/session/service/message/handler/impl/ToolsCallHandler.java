package com.liubo.ai.domain.session.service.message.handler.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.liubo.ai.domain.session.adapter.port.ISessionPort;
import com.liubo.ai.domain.session.adapter.repository.ISessionRepository;
import com.liubo.ai.domain.session.model.valobj.McpSchemaVO;
import com.liubo.ai.domain.session.model.valobj.gateway.McpGatewayProtocolConfigVO;
import com.liubo.ai.domain.session.service.message.handler.IRequestHandler;
import com.liubo.ai.types.constant.McpErrorCodes;
import com.liubo.ai.types.execption.AppException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.liubo.ai.types.enums.ResponseCode.CONFIG_NOT_EXIST;

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
            McpGatewayProtocolConfigVO protocolConfigVO = repository.queryMcpGatewayProtocolConfig(gatewayId);
            if (protocolConfigVO == null) {
                throw new AppException(CONFIG_NOT_EXIST.getCode(), CONFIG_NOT_EXIST.getInfo());
            }
            McpSchemaVO.CallToolRequest callToolRequest =
                    McpSchemaVO.unmarshalFrom(message.params(), new TypeReference<>() {
                    });
            Object argumentsObj = callToolRequest.arguments();

            Object result = port.toolCall(protocolConfigVO.getHttpConfig(), argumentsObj);
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
