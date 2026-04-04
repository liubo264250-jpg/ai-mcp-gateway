package com.liubo.ai.cases.mcp.message;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.liubo.ai.cases.mcp.IMcpMessageService;
import com.liubo.ai.cases.mcp.message.factory.DefaultMcpMessageFactory;
import com.liubo.ai.domain.session.model.entity.HandleMessageCommandEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 68
 * 2026/3/24 09:45
 */
@Service
public class McpMessageService implements IMcpMessageService {
    @Resource
    private DefaultMcpMessageFactory defaultMcpMessageFactory;

    @Override
    public ResponseEntity<Void> handleMessage(HandleMessageCommandEntity commandEntity) throws Exception {
        StrategyHandler<HandleMessageCommandEntity, DefaultMcpMessageFactory.DynamicContext, ResponseEntity<Void>> strategyHandler
                = defaultMcpMessageFactory.strategyHandler();
        return strategyHandler.apply(commandEntity, new DefaultMcpMessageFactory.DynamicContext());
    }
}
