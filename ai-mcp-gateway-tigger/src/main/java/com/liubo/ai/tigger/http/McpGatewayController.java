package com.liubo.ai.tigger.http;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liubo.ai.api.IMcpGatewayService;
import com.liubo.ai.cases.mcp.IMcpSessionService;
import com.liubo.ai.domain.session.model.valobj.McpSchemaVO;
import com.liubo.ai.domain.session.model.valobj.SessionConfigVO;
import com.liubo.ai.domain.session.service.ISessionManagementService;
import com.liubo.ai.domain.session.service.ISessionMessageService;
import com.liubo.ai.types.enums.ResponseCode;
import com.liubo.ai.types.execption.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author 68
 * 2026/3/24 08:39
 */
@Slf4j
@RestController
@RequestMapping("/")
public class McpGatewayController implements IMcpGatewayService {

    @Resource
    private IMcpSessionService mcpSessionService;

    @Resource
    private ISessionManagementService sessionManagementService;

    @Resource
    private ISessionMessageService serviceMessageService;

    private static final ObjectMapper objectMapper = new ObjectMapper();


    @GetMapping(value = "{gatewayId}/mcp/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Override
    public Flux<ServerSentEvent<String>> establishSSEConnection(@PathVariable("gatewayId") String gatewayId) throws Exception {
        try {
            log.info("建立 MCP SSE 连接，gatewayId:{}", gatewayId);
            if (StrUtil.isBlank(gatewayId)) {
                log.info("非法参数，gateway is null");
                throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
            }
            return mcpSessionService.createMcpSession(gatewayId);
        } catch (Exception e) {
            log.error("建立 MCP SSE 连接失败，gatewayId: {}", gatewayId, e);
            throw e;
        }
    }

    @PostMapping(value = "{gatewayId}/mcp/sse", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public Mono<ResponseEntity<Object>> handleMessage(@PathVariable("gatewayId") String gatewayId,
                                                      @RequestParam("sessionId") String sessionId,
                                                      @RequestBody String messageBody) {
        try {
            log.info("处理 MCP SSE 消息，gatewayId:{} sessionId:{} messageBody:{}", gatewayId, sessionId, messageBody);
            SessionConfigVO session = sessionManagementService.getSession(sessionId);
            if (null == session) {
                log.warn("会话不存在或已过期，gatewayId:{} sessionId:{}", gatewayId, sessionId);
                return Mono.just(ResponseEntity.notFound().build());
            }
            McpSchemaVO.JSONRPCMessage rpcMessage = McpSchemaVO.deserializeJsonRpcMessage(messageBody);
            McpSchemaVO.JSONRPCResponse jsonrpcResponse = serviceMessageService.processHandlerMessage(gatewayId,rpcMessage);
            log.info("调用结果:{}", JSON.toJSONString(jsonrpcResponse));
            if (null != jsonrpcResponse) {
                String responseJson = objectMapper.writeValueAsString(jsonrpcResponse);
                session.getSink().tryEmitNext(ServerSentEvent.<String>builder()
                        .event("message")
                        .data(responseJson)
                        .build());
            }
            return Mono.just(ResponseEntity.accepted().build());
        } catch (Exception e) {
            log.info("处理 MCP SSE 消息失败，gatewayId:{} sessionId:{} messageBody:{}", gatewayId, sessionId, messageBody, e);
            return Mono.just(ResponseEntity.internalServerError().build());
        }
    }
}
