package com.liubo.ai.domain.session.service.message.handler;

import com.liubo.ai.domain.session.model.valobj.McpSchemaVO;

/**
 * @author 68
 * 2026/3/27 09:31
 */
public interface IRequestHandler {
    McpSchemaVO.JSONRPCResponse handler(String gatewayId,McpSchemaVO.JSONRPCRequest request);
}
