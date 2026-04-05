package com.liubo.ai.domain.session.service;

import com.liubo.ai.domain.session.model.valobj.session.SessionConfigVO;

/**
 * @author 68
 * 2026/3/23 08:35
 */
public interface ISessionManagementService {
    SessionConfigVO createSession(String gatewayId);

    SessionConfigVO getSession(String sessionId);

    void removeSession(String sessionId);

    void shutdown();
}
