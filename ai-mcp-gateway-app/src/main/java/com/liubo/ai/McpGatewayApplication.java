package com.liubo.ai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 68
 * 2026/3/24 08:42
 */
@SpringBootApplication
@Configurable
@MapperScan(basePackages = "com.liubo.ai.infrastructure.dao.mapper")
public class McpGatewayApplication {
    public static void main(String[] args){
        SpringApplication.run(McpGatewayApplication.class);
    }
}
