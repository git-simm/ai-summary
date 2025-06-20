package com.glmapper.ai.mcp.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.mcp.client.autoconfigure.NamedClientMcpTransport;
import org.springframework.ai.mcp.client.autoconfigure.properties.McpClientCommonProperties;
import org.springframework.ai.mcp.client.autoconfigure.properties.McpSseClientProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
    @AutoConfiguration（Spring Boot 3.x 新增）和 @Configuration 不同，不会被包扫描自动发现，而是需要通过 Spring Boot 的自动装配机制注册（即 SPI 机制）。
    关键点
    @AutoConfiguration 注解的类，不会因为包扫描自动生效。
    必须在 META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports 文件中手动声明，Spring Boot 才会自动加载它。
    @AutoConfiguration(
            after = {SseWebFluxTransportAutoConfiguration.class}
    )
*/
@ConditionalOnClass({McpSchema.class, McpSyncClient.class})
@ConditionalOnMissingClass({"io.modelcontextprotocol.client.transport.WebFluxSseClientTransport"})
@EnableConfigurationProperties({McpSseClientProperties.class, McpClientCommonProperties.class})
@ConditionalOnProperty(
        prefix = "spring.ai.mcp.client",
        name = {"enabled"},
        havingValue = "true",
        matchIfMissing = true
)
@Configuration
public class SseHttpClientTransportCustomConfiguration {
    @Bean
    public List<NamedClientMcpTransport> mcpHttpClientTransports(McpSseClientProperties sseProperties, ObjectProvider<ObjectMapper> objectMapperProvider) {
        ObjectMapper objectMapper = objectMapperProvider.getIfAvailable(ObjectMapper::new);
        List<NamedClientMcpTransport> sseTransports = new ArrayList();

        for(Map.Entry<String, McpSseClientProperties.SseParameters> serverParameters : sseProperties.getConnections().entrySet()) {
            String baseUrl = serverParameters.getValue().url();
            String sseEndpoint = serverParameters.getValue().sseEndpoint() != null ? serverParameters.getValue().sseEndpoint() : "/sse";
            // 不同的 Mcp-server 可以设置不同的 认证header
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .header("Content-Type", "application/json");
            CustomSseClientTransport transport = CustomSseClientTransport.builder(baseUrl)
                    .requestBuilder(requestBuilder).consumerRequest(builder -> {
                        builder.header("Authorization", this.getToken());
                    }).sseEndpoint(sseEndpoint)
                    .clientBuilder(HttpClient.newBuilder()).objectMapper(objectMapper).build();
            sseTransports.add(new NamedClientMcpTransport(serverParameters.getKey(), transport));
        }

        return sseTransports;
    }

    private String getToken(){
        return "test-simm";
    }
}
