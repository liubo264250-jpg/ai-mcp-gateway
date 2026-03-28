package com.liubo.ai.domain.session.service.message;

import com.alibaba.fastjson.JSON;
import com.liubo.ai.domain.session.model.valobj.McpSchemaVO;
import com.liubo.ai.domain.session.service.ISessionMessageService;
import com.liubo.ai.domain.session.service.message.handler.IRequestHandler;
import com.liubo.ai.types.enums.SessionMessageHandlerMethodEnum;
import com.liubo.ai.types.execption.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.liubo.ai.types.enums.ResponseCode.METHOD_NOT_FOUND;

/**
 * @author 68
 * 2026/3/27 09:30
 */
@Service
@Slf4j
public class SessionMessageService implements ISessionMessageService {

    @Autowired
    private Map<String, IRequestHandler> requestHandlerMap;

    @Override
    public McpSchemaVO.JSONRPCResponse processHandlerMessage(McpSchemaVO.JSONRPCMessage message) {
        if (message instanceof McpSchemaVO.JSONRPCResponse response) {
            log.info("收到结果消息");
        }
        if (message instanceof McpSchemaVO.JSONRPCRequest request) {
            String method = request.method();
            log.info("开始处理请求，方法: {}", method);
            SessionMessageHandlerMethodEnum methodEnum = SessionMessageHandlerMethodEnum.getByMethod(method);
            IRequestHandler handler = requestHandlerMap.get(methodEnum.getHandlerName());
            if (handler == null) {
                throw new AppException(METHOD_NOT_FOUND.getCode(), METHOD_NOT_FOUND.getInfo());
            }
            return handler.handler(request);
        }
        if (message instanceof McpSchemaVO.JSONRPCNotification notification) {
            log.info("收到即将处理的通知 {} {}", notification.method(), JSON.toJSONString(notification.params()));
        }
        return null;
    }
}
