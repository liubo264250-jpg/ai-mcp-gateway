package com.liubo.ai.cases.mcp.message.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liubo.ai.cases.mcp.message.AbstractMcpMessageServiceSupport;
import com.liubo.ai.cases.mcp.message.factory.DefaultMcpMessageFactory;
import com.liubo.ai.domain.session.model.entity.HandleMessageCommandEntity;
import com.liubo.ai.domain.session.model.valobj.session.McpSchemaVO;
import com.liubo.ai.domain.session.model.valobj.session.SessionConfigVO;
import com.liubo.ai.domain.session.service.ISessionMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 68
 * 2026/4/4 21:44
 */
@Service("mcpMessageMessageHandlerNode")
@Slf4j
public class MessageHandlerNode extends AbstractMcpMessageServiceSupport {

    @Resource
    private ISessionMessageService serviceMessageService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected ResponseEntity<Void> doApply(HandleMessageCommandEntity requestParameter, DefaultMcpMessageFactory.DynamicContext dynamicContext) throws Exception {
        log.info("消息处理 mcp message MessageHandlerNode:{}", requestParameter);
        String gatewayId = requestParameter.getGatewayId();
        McpSchemaVO.JSONRPCMessage rpcMessage = requestParameter.getJsonrpcMessage();
        McpSchemaVO.JSONRPCResponse jsonrpcResponse = serviceMessageService.processHandlerMessage(gatewayId,rpcMessage);
        if (null != jsonrpcResponse) {
            String responseJson = objectMapper.writeValueAsString(jsonrpcResponse);
            SessionConfigVO session = dynamicContext.getSessionConfigVO();
            session.getSink().tryEmitNext(ServerSentEvent.<String>builder()
                    .event("message")
                    .data(responseJson)
                    .build());
        }
        return ResponseEntity.accepted().build();
    }

    @Override
    public StrategyHandler<HandleMessageCommandEntity, DefaultMcpMessageFactory.DynamicContext, ResponseEntity<Void>> get(HandleMessageCommandEntity requestParameter, DefaultMcpMessageFactory.DynamicContext dynamicContext) throws Exception {
        return defaultStrategyHandler;
    }
}
