package com.glmapper.ai.mcp.client.config;

import io.modelcontextprotocol.server.McpServer;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname McpClientConfig
 * @Description McpClientConfig
 * @Date 2025/4/15 14:53
 * @Created by glmapper
 */
@Configuration
public class McpClientConfig {
    /**
     * 注入ChatClient
     *
     * @param chatModel
     * @param toolCallbackProvider 报红不用管，没有任何问题
     * @return
     */
    @Bean
    ChatClient chatClient(DeepSeekChatModel chatModel, ToolCallbackProvider toolCallbackProvider) {
        return ChatClient.builder(chatModel).defaultToolCallbacks(toolCallbackProvider.getToolCallbacks()).build();
    }
}
