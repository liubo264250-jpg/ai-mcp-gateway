package com.liubo.ai.cases.mcp.message.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.liubo.ai.cases.mcp.message.AbstractMcpMessageServiceSupport;
import com.liubo.ai.cases.mcp.message.factory.DefaultMcpMessageFactory;
import com.liubo.ai.domain.auth.model.entity.RateLimitCommandEntity;
import com.liubo.ai.domain.auth.service.IAuthRateLimitService;
import com.liubo.ai.domain.session.model.entity.HandleMessageCommandEntity;
import com.liubo.ai.domain.session.model.valobj.session.McpSchemaVO;
import com.liubo.ai.types.constant.McpErrorCodes;
import com.liubo.ai.types.enums.SessionMessageHandlerMethodEnum;
import com.liubo.ai.types.execption.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 68
 * 2026/4/5 22:58
 */
@Slf4j
@Service("mcpMessageVerifyNode")
public class VerifyNode extends AbstractMcpMessageServiceSupport {

    @Resource
    private MessageHandlerNode mcpMessageMessageHandlerNode;

    @Resource
    private IAuthRateLimitService authRateLimitService;

    @Override
    protected ResponseEntity<Void> doApply(HandleMessageCommandEntity requestParameter, DefaultMcpMessageFactory.DynamicContext dynamicContext) throws Exception {
        log.info("消息处理 mcp message VerifyNode:{}", requestParameter);
        if (requestParameter.getJsonrpcMessage() instanceof McpSchemaVO.JSONRPCRequest request) {
            String method = request.method();
            SessionMessageHandlerMethodEnum methodEnum = SessionMessageHandlerMethodEnum.getByMethod(method);
            if (SessionMessageHandlerMethodEnum.TOOLS_CALL.equals(methodEnum)) {
                boolean isHit = authRateLimitService.rateLimit(new RateLimitCommandEntity(requestParameter.getGatewayId(), requestParameter.getApiKey()));
                if (isHit) {
                    log.warn("消息处理 mcp message RootNode - 命中限流{} {}", requestParameter.getGatewayId(), requestParameter.getApiKey());
                    throw new AppException(McpErrorCodes.INSUFFICIENT_PERMISSIONS, "fail to auth apikey rateLimiter");
                }
            }
        }
        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<HandleMessageCommandEntity, DefaultMcpMessageFactory.DynamicContext, ResponseEntity<Void>> get(HandleMessageCommandEntity requestParameter, DefaultMcpMessageFactory.DynamicContext dynamicContext) throws Exception {
        return mcpMessageMessageHandlerNode;
    }
}
