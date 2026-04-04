package com.liubo.ai.cases.mcp.message;

import cn.bugstack.wrench.design.framework.tree.AbstractMultiThreadStrategyRouter;
import com.liubo.ai.cases.mcp.message.factory.DefaultMcpMessageFactory;
import com.liubo.ai.domain.session.model.entity.HandleMessageCommandEntity;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author 68
 * 2026/3/24 09:45
 */
public abstract class AbstractMcpMessageServiceSupport extends AbstractMultiThreadStrategyRouter<HandleMessageCommandEntity, DefaultMcpMessageFactory.DynamicContext, ResponseEntity<Void>> {

    @Override
    protected void multiThread(HandleMessageCommandEntity requestParameter, DefaultMcpMessageFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {

    }
}
