package com.liubo.ai.domain.session.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Sinks;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author 68
 * 2026/3/22 22:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionConfigVO {
    /**
     * 会话id
     */
    private String sessionId;
    /**
     * 流失响应
     */
    private Sinks.Many<ServerSentEvent<String>> sink;
    /**
     * 会话时间戳
     */
    private Instant createTime;
    /**
     * 上次会话时间戳
     */
    private volatile Instant lastAccessedTime;
    /**
     * 会话状态
     */
    private volatile boolean active;

    public SessionConfigVO(String sessionId, Sinks.Many<ServerSentEvent<String>> sink) {
        this.sessionId = sessionId;
        this.sink = sink;
        this.createTime = Instant.now();
        this.lastAccessedTime = Instant.now();
        this.active = true;
    }

    /**
     * 标记会话为非活跃状态
     */
    public void markInactive() {
        this.active = false;
    }

    /**
     * 更新最后访问时间
     */
    public void updateLastAccessed() {
        this.lastAccessedTime = Instant.now();
    }

    /**
     * 过期时间判断
     */
    public boolean isExpired(long timeoutMinutes) {
        return lastAccessedTime.isBefore(Instant.now().minus(timeoutMinutes, ChronoUnit.MINUTES));
    }
}
