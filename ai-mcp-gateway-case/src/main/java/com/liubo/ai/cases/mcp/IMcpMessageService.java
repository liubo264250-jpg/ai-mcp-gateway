package com.liubo.ai.cases.mcp;

import com.liubo.ai.domain.session.model.entity.HandleMessageCommandEntity;
import org.springframework.http.ResponseEntity;

/**
 * @author 68
 * 2026/4/4 09:13
 */
public interface IMcpMessageService {
    ResponseEntity<Void> handleMessage(HandleMessageCommandEntity commandEntity) throws Exception;
}
