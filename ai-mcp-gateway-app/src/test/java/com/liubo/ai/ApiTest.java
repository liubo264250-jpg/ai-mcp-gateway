package com.liubo.ai;

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


    @Test
    public void test() {
        ChatClient chatClient = chatClientBuilder.defaultOptions(
                        OpenAiChatOptions.builder()
                                .model("gpt-4.1-mini")
                                .toolCallbacks(new SyncMcpToolCallbackProvider(sseMcpClient02()).getToolCallbacks())
                                .build())
                .build();
        System.out.println(io.netty.resolver.dns.DnsServerAddressStreamProviders.platformDefault().getClass().getName());
        log.info("测试结果:{}", chatClient.prompt("liubo小写转大写").call().content());
    }

    public McpSyncClient sseMcpClient02() {
        HttpClientSseClientTransport sseClientTransport = HttpClientSseClientTransport
                .builder("http://127.0.0.1:8777")
                .sseEndpoint("/api-gateway/test10001/mcp/sse")
                .build();
        McpSyncClient mcpSyncClient = McpClient.sync(sseClientTransport).requestTimeout(Duration.ofMinutes(36000)).build();
        var init_sse = mcpSyncClient.initialize();
        log.info("Tool SSE MCP02 Initialized {}", init_sse);

        return mcpSyncClient;
    }
}
