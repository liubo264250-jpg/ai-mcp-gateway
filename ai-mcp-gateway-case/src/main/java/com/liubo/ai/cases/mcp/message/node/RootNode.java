package com.liubo.ai.cases.mcp.message.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.liubo.ai.cases.mcp.message.AbstractMcpMessageServiceSupport;
import com.liubo.ai.cases.mcp.message.factory.DefaultMcpMessageFactory;
import com.liubo.ai.domain.session.model.entity.HandleMessageCommandEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 68
 * 2026/4/4 09:21
 */
@Service("mcpMessageRootNode")
@Slf4j
public class RootNode extends AbstractMcpMessageServiceSupport {

    @Resource
    private SessionNode mcpMessageSessionNode;


    @Override
    protected ResponseEntity<Void> doApply(HandleMessageCommandEntity requestParameter, DefaultMcpMessageFactory.DynamicContext dynamicContext) throws Exception {
        try {
            log.info("消息处理 mcp message RootNode:{}", requestParameter);
            return router(requestParameter, dynamicContext);
        } catch (Exception e) {
            log.error("消息处理 mcp message RootNode:{}", requestParameter, e);
            throw e;
        }
    }

    @Override
    public StrategyHandler<HandleMessageCommandEntity, DefaultMcpMessageFactory.DynamicContext, ResponseEntity<Void>> get(HandleMessageCommandEntity requestParameter, DefaultMcpMessageFactory.DynamicContext dynamicContext) throws Exception {
        return mcpMessageSessionNode;
    }
}
