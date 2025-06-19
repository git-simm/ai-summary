package com.glmapper.ai.mcp.client;

import org.springframework.ai.mcp.client.autoconfigure.SseHttpClientTransportAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages = "com.glmapper.ai",exclude = SseHttpClientTransportAutoConfiguration.class)
public class McpClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(McpClientApplication.class, args);
    }
} 