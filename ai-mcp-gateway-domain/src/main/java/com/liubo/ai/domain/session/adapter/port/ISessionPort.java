package com.liubo.ai.domain.session.adapter.port;

import com.liubo.ai.domain.session.model.valobj.gateway.McpToolProtocolConfigVO;

import java.io.IOException;

/**
 * @author 68
 * 2026/3/31 08:38
 */
public interface ISessionPort {
    Object toolCall(McpToolProtocolConfigVO.HTTPConfig httpConfig, Object params) throws IOException;
}
