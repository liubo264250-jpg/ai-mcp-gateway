package com.liubo.ai.cases.mcp.message.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.liubo.ai.cases.mcp.message.AbstractMcpMessageServiceSupport;
import com.liubo.ai.cases.mcp.message.factory.DefaultMcpMessageFactory;
import com.liubo.ai.domain.session.model.entity.HandleMessageCommandEntity;
import com.liubo.ai.domain.session.model.valobj.session.SessionConfigVO;
import com.liubo.ai.domain.session.service.ISessionManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 68
 * 2026/4/4 09:21
 */
@Slf4j
@Service("mcpMessageSessionNode")
public class SessionNode extends AbstractMcpMessageServiceSupport {

    @Resource
    private MessageHandlerNode mcpMessageMessageHandlerNode;

    @Resource
    private ISessionManagementService sessionManagementService;

    @Override
    protected ResponseEntity<Void> doApply(HandleMessageCommandEntity requestParameter, DefaultMcpMessageFactory.DynamicContext dynamicContext) throws Exception {
        String sessionId = requestParameter.getSessionId();
        String gatewayId = requestParameter.getGatewayId();
        SessionConfigVO session = sessionManagementService.getSession(sessionId);
        if (null == session) {
            log.warn("会话不存在或已过期，gatewayId:{} sessionId:{}", gatewayId, sessionId);
            return ResponseEntity.notFound().build();
        }
        dynamicContext.setSessionConfigVO(session);
        return router(requestParameter,dynamicContext);
    }

    @Override
    public StrategyHandler<HandleMessageCommandEntity, DefaultMcpMessageFactory.DynamicContext, ResponseEntity<Void>> get(HandleMessageCommandEntity requestParameter, DefaultMcpMessageFactory.DynamicContext dynamicContext) throws Exception {
        return mcpMessageMessageHandlerNode;
    }
}
