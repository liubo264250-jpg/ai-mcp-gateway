package com.liubo.ai.cases.mcp;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

/**
 * @author 68
 * 2026/3/24 08:27
 */
public interface IMcpSessionService {
    Flux<ServerSentEvent<String>> createMcpSession(String gatewayId) throws Exception;
}
