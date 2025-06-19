package com.glmapper.ai.mcp.client.config;

import lombok.Value;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

//@Component
//public class DynamicMcpToolCallbackProvider implements ToolCallbackProvider {
//    @Value("${spring.ai.mcp.client.sse.connections.weather-mcp-server.url}")
//    private String mcpServerUrl;
//
//    @Override
//    public ToolCallback[] getToolCallbacks() {
//        return List.of(
//                (input,toolContext) -> {
//                    WebClient.Builder builder = WebClient.builder().baseUrl(mcpServerUrl);
//                    builder.defaultHeader("auth","123456");
//                    WebClient webClient = builder.build();
//                    return webClient.post()
//                            .uri(toolContext.get("uri")) // 动态获取接口地址
//                            .bodyValue(input)
//                            .retrieve()
//                            .bodyToMono(String.class)
//                            .block();
//                }
//        ).toArray();
//    }
//}
