package com.liubo.ai.cases.mcp.session.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.liubo.ai.cases.mcp.session.AbstractMcpSessionServiceSupport;
import com.liubo.ai.cases.mcp.session.factory.DefaultMcpSessionFactory;
import com.liubo.ai.domain.session.model.valobj.SessionConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * @author 68
 * 2026/3/24 09:45
 */
@Service("mcpSessionSessionNode")
@Slf4j
public class SessionNode extends AbstractMcpSessionServiceSupport {

    @Resource
    private EndNode mcpSessionEndNode;


    @Override
    protected Flux<ServerSentEvent<String>> doApply(String gatewayId, DefaultMcpSessionFactory.DynamicContext dynamicContext) throws Exception {
        log.info("创建会话-SessionNode:{}", gatewayId);
        SessionConfigVO sessionConfigVO = sessionManagementService.createSession(gatewayId);
        dynamicContext.setSessionConfigVO(sessionConfigVO);
        return router(gatewayId, dynamicContext);
    }

    @Override
    public StrategyHandler<String, DefaultMcpSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> get(String requestParameter, DefaultMcpSessionFactory.DynamicContext dynamicContext) throws Exception {
        return mcpSessionEndNode;
    }
}
