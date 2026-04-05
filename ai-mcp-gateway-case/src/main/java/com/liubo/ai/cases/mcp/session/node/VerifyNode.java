package com.liubo.ai.cases.mcp.session.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.liubo.ai.cases.mcp.session.AbstractMcpSessionServiceSupport;
import com.liubo.ai.cases.mcp.session.factory.DefaultMcpSessionFactory;
import com.liubo.ai.domain.auth.model.entity.LicenseCommandEntity;
import com.liubo.ai.domain.auth.service.IAuthLicenseService;
import com.liubo.ai.types.constant.McpErrorCodes;
import com.liubo.ai.types.execption.AppException;
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

    @Resource
    private IAuthLicenseService authLicenseService;

    @Override
    protected Flux<ServerSentEvent<String>> doApply(String gatewayId, DefaultMcpSessionFactory.DynamicContext dynamicContext) throws Exception {
        log.info("创建会话-VerifyNode:{}", gatewayId);
        boolean isCheckSuccess = authLicenseService.checkLicense(new LicenseCommandEntity(gatewayId, dynamicContext.getApiKey()));
        if (!isCheckSuccess) {
            throw new AppException(McpErrorCodes.INSUFFICIENT_PERMISSIONS, "fail to auth apikey");
        }
        return router(gatewayId, dynamicContext);
    }

    @Override
    public StrategyHandler<String, DefaultMcpSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> get(String requestParameter, DefaultMcpSessionFactory.DynamicContext dynamicContext) throws Exception {
        return mcpSessionSessionNode;
    }
}
