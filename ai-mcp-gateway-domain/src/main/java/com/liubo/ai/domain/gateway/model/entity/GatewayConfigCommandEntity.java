package com.liubo.ai.domain.gateway.model.entity;

import com.liubo.ai.domain.gateway.model.valobj.GatewayConfigVO;
import com.liubo.ai.types.enums.GatewayEnum;
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
public class GatewayConfigCommandEntity {
    private GatewayConfigVO gatewayConfigVO;

    public static GatewayConfigCommandEntity buildUpdateGatewayAuthStatusVO(String gatewayId, GatewayEnum.GatewayAuthStatusEnum auth) {
        return GatewayConfigCommandEntity.builder()
                .gatewayConfigVO(GatewayConfigVO.builder()
                        .gatewayId(gatewayId)
                        .auth(auth)
                        .build())
                .build();
    }

}
