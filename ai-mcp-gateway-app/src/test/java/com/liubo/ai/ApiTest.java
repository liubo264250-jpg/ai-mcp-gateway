package com.liubo.ai;

import com.alibaba.fastjson.JSON;
import com.liubo.ai.domain.session.model.valobj.session.McpSchemaVO;
import com.liubo.ai.domain.session.service.message.handler.impl.ToolsListHandler;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @author 68
 * 2026/3/27 23:15
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ApiTest {

    @Resource
    private ChatClient.Builder chatClientBuilder;

    @Resource
    private ToolsListHandler toolsListHandler;


    @Test
    public void test() {
        ChatClient chatClient = chatClientBuilder.defaultOptions(
                        OpenAiChatOptions.builder()
                                .model("gpt-4.1-mini")
                                .toolCallbacks(new SyncMcpToolCallbackProvider(sseMcpClient02()).getToolCallbacks())
                                .build())
                .build();
        log.info("测试结果:{}", chatClient.prompt("有哪些工具可以使用").call().content());
    }

    public McpSyncClient sseMcpClient02() {
        HttpClientSseClientTransport sseClientTransport = HttpClientSseClientTransport
                .builder("http://127.0.0.1:8777")
                .sseEndpoint("/api-gateway/gateway_001/mcp/sse")
                .build();
        McpSyncClient mcpSyncClient = McpClient.sync(sseClientTransport).requestTimeout(Duration.ofMinutes(36000)).build();
        var init_sse = mcpSyncClient.initialize();
        log.info("Tool SSE MCP02 Initialized {}", init_sse);

        return mcpSyncClient;
    }

    @Test
    public void test_handle() {
        McpSchemaVO.JSONRPCResponse handle = toolsListHandler.
                handler("gateway_001",
                        new McpSchemaVO.JSONRPCRequest("2.0","tool/list","a355a5f7-0",""));
        log.info("测试结果:{}", JSON.toJSONString(handle.result()));
    }
}
