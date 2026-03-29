package com.liubo.ai.domain.session.service.message.handler.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.liubo.ai.domain.session.adapter.repository.ISessionRepository;
import com.liubo.ai.domain.session.model.valobj.McpSchemaVO;
import com.liubo.ai.domain.session.model.valobj.gateway.McpGatewayConfigVO;
import com.liubo.ai.domain.session.service.message.handler.IRequestHandler;
import com.liubo.ai.types.execption.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.liubo.ai.types.enums.ResponseCode.CONFIG_NOT_EXIST;

/**
 * @author 68
 * 2026/3/27 22:47
 */
@Service
@Slf4j
public class InitializeHandler implements IRequestHandler {

    @Autowired
    private ISessionRepository sessionRepository;

    @Override
    public McpSchemaVO.JSONRPCResponse handler(String gatewayId, McpSchemaVO.JSONRPCRequest message) {
        log.info("消息处理服务-initialize gatewayId:{} request.params:{}", gatewayId, JSON.toJSONString(message.params()));
        // 1. 转换参数
        McpSchemaVO.InitializeRequest initializeRequest = McpSchemaVO.unmarshalFrom(message.params(), new TypeReference<>() {
        });
        McpGatewayConfigVO mcpGatewayConfigVO = sessionRepository.queryMcpGatewayConfigByGatewayId(gatewayId);
        if (mcpGatewayConfigVO == null) {
            throw new AppException(CONFIG_NOT_EXIST.getCode(), CONFIG_NOT_EXIST.getInfo());
        }
        McpSchemaVO.InitializeResult initializeResult = new McpSchemaVO.InitializeResult(
                initializeRequest.protocolVersion(),
                new McpSchemaVO.ServerCapabilities(
                        new McpSchemaVO.ServerCapabilities.CompletionCapabilities(),
                        new HashMap<>(),
                        new McpSchemaVO.ServerCapabilities.LoggingCapabilities(),
                        new McpSchemaVO.ServerCapabilities.PromptCapabilities(true),
                        new McpSchemaVO.ServerCapabilities.ResourceCapabilities(false, true),
                        new McpSchemaVO.ServerCapabilities.ToolCapabilities(true)
                ),
                new McpSchemaVO.Implementation(mcpGatewayConfigVO.getGatewayName(), mcpGatewayConfigVO.getToolVersion()),
                mcpGatewayConfigVO.getToolDescription()
        );
        return new McpSchemaVO.JSONRPCResponse(McpSchemaVO.JSONRPC_VERSION, message.id(), initializeResult, null);
    }
}
