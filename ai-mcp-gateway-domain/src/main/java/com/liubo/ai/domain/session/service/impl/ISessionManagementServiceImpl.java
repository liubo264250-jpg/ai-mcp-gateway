package com.liubo.ai.domain.session.service.impl;

import cn.hutool.core.util.StrUtil;
import com.liubo.ai.domain.session.model.valobj.SessionConfigVO;
import com.liubo.ai.domain.session.service.ISessionManagementService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 68
 * 2026/3/23 08:36
 */
@Service
@Slf4j
public class ISessionManagementServiceImpl implements ISessionManagementService {

    /**
     * 会话超时时间（分钟）- 也可以把配置抽取到yml里
     */
    private static final long SESSION_TIMEOUT_MINUTES = 30;

    private final ScheduledExecutorService cleanUpScheduler = Executors.newSingleThreadScheduledExecutor();

    private final Map<String, SessionConfigVO> activeSessions = new ConcurrentHashMap<>();

    @Override
    public SessionConfigVO createSession(String gatewayId) {
        String sessionId = UUID.randomUUID().toString();

        Sinks.Many<ServerSentEvent<String>> sink = Sinks.many()
                .multicast() // 支持多个订阅者
                .onBackpressureBuffer();// 背压策略：数据发太快时先存入缓冲区
        SessionConfigVO sessionConfigVO = new SessionConfigVO(sessionId, sink);
        // 发送消息
        String messageEndpoint = "/" + gatewayId + "/mcp/message?sessionId=" + sessionId;
        sink.tryEmitNext(ServerSentEvent.<String>builder()
                .event("endpoint")
                .data(messageEndpoint)
                .build());
        activeSessions.put(sessionId, sessionConfigVO);
        log.info("创建会话 gatewayId:{} sessionId:{},当前活跃会话数:{}", gatewayId, sessionId, activeSessions.size());
        return sessionConfigVO;
    }

    @Override
    public SessionConfigVO getSession(String sessionId) {
        if (StrUtil.isBlank(sessionId) || null == activeSessions.get(sessionId)) return null;
        SessionConfigVO sessionConfigVO = activeSessions.get(sessionId);
        sessionConfigVO.updateLastAccessed();
        return sessionConfigVO;
    }

    @Override
    public void removeSession(String sessionId) {
        log.info("删除会话配置 sessionId:{}", sessionId);
        if (StrUtil.isBlank(sessionId)) return;
        SessionConfigVO sessionConfigVO = activeSessions.remove(sessionId);
        if (null == sessionConfigVO) return;
        sessionConfigVO.markInactive();
        try {
            sessionConfigVO.getSink().tryEmitComplete();
        }catch (Exception e){
            log.warn("关闭会话Sink时出错:{}", e.getMessage());
        }
        log.info("移除会话:{},剩余活跃会话数:{}", sessionId, activeSessions.size());
    }

    @Override
    public void shutdown() {
        for (String sessionId : activeSessions.keySet()) {
            removeSession(sessionId);
        }
        // 第一阶段：拒绝新任务，尝试让存量任务跑完
        cleanUpScheduler.shutdown();
        try {
            if (!cleanUpScheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                // 超时强制关闭
                cleanUpScheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            // 超时强制关闭
            cleanUpScheduler.shutdownNow();
            // 重新抛出中断信号，保持线程的中断状态
            Thread.currentThread().interrupt();
        }
    }

    // 定时任务
    public void cleanupExpiredSessions() {
        int cleanCount = 0;
        for (Map.Entry<String, SessionConfigVO> entry : activeSessions.entrySet()) {
            SessionConfigVO sessionConfigVO = entry.getValue();
            // 会话非活跃
            if (!sessionConfigVO.isActive() || sessionConfigVO.isExpired(SESSION_TIMEOUT_MINUTES)){
                removeSession(sessionConfigVO.getSessionId());
                cleanCount++;
            }
        }
        log.info("清理了 {} 个过期会话，剩余活跃会话数: {}", cleanCount, activeSessions.size());
    }
}
