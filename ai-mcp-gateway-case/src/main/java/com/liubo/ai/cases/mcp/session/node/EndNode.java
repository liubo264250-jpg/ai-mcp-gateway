package com.liubo.ai.cases.mcp.session.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.liubo.ai.cases.mcp.session.AbstractMcpSessionServiceSupport;
import com.liubo.ai.cases.mcp.session.factory.DefaultMcpSessionFactory;
import com.liubo.ai.domain.session.model.valobj.SessionConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

/**
 * @author 68
 * 2026/3/24 08:28
 */
@Service("mcpSessionEndNode")
@Slf4j
public class EndNode extends AbstractMcpSessionServiceSupport {

    @Override
    protected Flux<ServerSentEvent<String>> doApply(String gatewayId, DefaultMcpSessionFactory.DynamicContext dynamicContext) throws Exception {
        SessionConfigVO sessionConfigVO = dynamicContext.getSessionConfigVO();
        String sessionId = sessionConfigVO.getSessionId();
        Sinks.Many<ServerSentEvent<String>> sink = sessionConfigVO.getSink();
        return sink.asFlux().mergeWith(
                // 心跳机制 - 防止连接超时，延长间隔避免干扰正常通信
                Flux.interval(Duration.ofSeconds(60))
                        .map(i -> ServerSentEvent.<String>builder()
                                .event("ping")
                                .data("ping")
                                .build())
        ).doOnCancel(() -> {
            // (用户主动离开) 当用户 关闭浏览器标签页 、 刷新页面 或者前端调用 eventSource.close() 时触发
            log.info("SSE连接取消，会话ID: {}", sessionId);
            sessionManagementService.removeSession(sessionId);
        }).doOnTerminate(() -> {
            // (连接正常或异常结束) 当后端调用 sink.tryEmitComplete() （回答完了）或者发生报错时触发
            log.info("SSE连接终止，会话ID: {}", sessionId);
            sessionManagementService.removeSession(sessionId);
        });
    }

    @Override
    public StrategyHandler<String, DefaultMcpSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> get(String requestParameter, DefaultMcpSessionFactory.DynamicContext dynamicContext) throws Exception {
        return defaultStrategyHandler;
    }
}
