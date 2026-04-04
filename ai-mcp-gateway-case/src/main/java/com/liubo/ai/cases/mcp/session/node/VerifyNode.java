package com.liubo.ai.cases.mcp.session.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.liubo.ai.cases.mcp.session.AbstractMcpSessionServiceSupport;
import com.liubo.ai.cases.mcp.session.factory.DefaultMcpSessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * @author 68
 * 2026/3/24 09:44
 */
@Service("mcpSessionVerifyNode")
@Slf4j
public class VerifyNode extends AbstractMcpSessionServiceSupport {

    @Resource
    private SessionNode mcpSessionSessionNode;

    @Override
    protected Flux<ServerSentEvent<String>> doApply(String gatewayId, DefaultMcpSessionFactory.DynamicContext dynamicContext) throws Exception {
        return router(gatewayId, dynamicContext);
    }

    @Override
    public StrategyHandler<String, DefaultMcpSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> get(String requestParameter, DefaultMcpSessionFactory.DynamicContext dynamicContext) throws Exception {
        return mcpSessionSessionNode;
    }
}
