package com.liubo.ai.domain.session.service.message.handler.impl;

import com.liubo.ai.domain.session.model.valobj.session.McpSchemaVO;
import com.liubo.ai.domain.session.service.message.handler.IRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 68
 * 2026/3/27 22:48
 */
@Service
@Slf4j
public class ResourcesListHandler implements IRequestHandler {
    @Override
    public McpSchemaVO.JSONRPCResponse handler(String gatewayId,McpSchemaVO.JSONRPCRequest request) {
        return new McpSchemaVO.JSONRPCResponse("2.0",
                request.id(),
                Map.of("resources",Map.of("resources",new Object[]{})),
                null);
    }
}
