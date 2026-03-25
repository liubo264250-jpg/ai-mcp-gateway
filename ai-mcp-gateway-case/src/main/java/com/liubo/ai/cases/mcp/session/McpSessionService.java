package com.liubo.ai.cases.mcp.session;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.liubo.ai.cases.mcp.IMcpSessionService;
import com.liubo.ai.cases.mcp.session.factory.DefaultMcpSessionFactory;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * @author 68
 * 2026/3/24 09:45
 */
@Service
public class McpSessionService implements IMcpSessionService {
    @Resource
    private DefaultMcpSessionFactory defaultMcpSessionFactory;

    @Override
    public Flux<ServerSentEvent<String>> createMcpSession(String gatewayId) throws Exception {
        StrategyHandler<String, DefaultMcpSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> handler = defaultMcpSessionFactory.strategyHandler();
        return handler.apply(gatewayId,new DefaultMcpSessionFactory.DynamicContext());
    }
}
