package com.liubo.ai.domain.session.service;

import com.liubo.ai.domain.session.model.valobj.McpSchemaVO;

/**
 * @author 68
 * 2026/3/27 09:3
 */
public interface ISessionMessageService {
    McpSchemaVO.JSONRPCResponse processHandlerMessage(String gatewayId,McpSchemaVO.JSONRPCMessage message);
}
