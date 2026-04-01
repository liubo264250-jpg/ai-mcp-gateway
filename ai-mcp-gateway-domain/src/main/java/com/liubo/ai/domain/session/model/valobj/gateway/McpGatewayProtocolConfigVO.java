package com.liubo.ai.domain.session.model.valobj.gateway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 68
 * 2026/3/30 09:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class McpGatewayProtocolConfigVO {

    private HTTPConfig httpConfig;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HTTPConfig {
        private String httpUrl;
        private String httpHeaders;
        private String httpMethod;
        private Integer timeout;
    }
}
