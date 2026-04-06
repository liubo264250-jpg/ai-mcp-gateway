package com.liubo.ai.domain.gateway.model.entity;

import com.liubo.ai.domain.gateway.model.valobj.GatewayToolConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 68
 * 2026/4/6 17:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatewayToolConfigCommandEntity {
    private GatewayToolConfigVO gatewayToolConfigVO;

    public static GatewayToolConfigCommandEntity buildUpdateGatewayProtocol(String gatewayId, Long toolId, Long protocolId, String protocolType) {
        return GatewayToolConfigCommandEntity.builder()
                .gatewayToolConfigVO(
                        GatewayToolConfigVO.builder()
                                .gatewayId(gatewayId)
                                .toolId(toolId)
                                .protocolId(protocolId)
                                .protocolType(protocolType)
                                .build())
                .build();
    }

}
