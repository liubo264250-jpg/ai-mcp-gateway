package com.liubo.ai.cases.mcp.message.factory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.liubo.ai.cases.mcp.message.node.RootNode;
import com.liubo.ai.domain.session.model.entity.HandleMessageCommandEntity;
import com.liubo.ai.domain.session.model.valobj.SessionConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 68
 * 2026/4/4 09:20
 */
@Service
public class DefaultMcpMessageFactory {

    @Resource
    private RootNode mcpMessageRootNode;


    public StrategyHandler<HandleMessageCommandEntity, DefaultMcpMessageFactory.DynamicContext, ResponseEntity<Void>>  strategyHandler() {
        return mcpMessageRootNode;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DynamicContext {
        private SessionConfigVO sessionConfigVO;
    }
}
