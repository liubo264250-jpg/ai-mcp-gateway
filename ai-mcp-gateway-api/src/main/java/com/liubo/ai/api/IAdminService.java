package com.liubo.ai.api;

import com.liubo.ai.api.dto.GatewayConfigDTO;
import com.liubo.ai.api.dto.GatewayConfigRequestDTO;
import com.liubo.ai.api.dto.GatewayConfigResponseDTO;
import com.liubo.ai.api.response.Response;

import java.util.List;

/**
 * @author 68
 * 2026/4/6 19:49
 */
public interface IAdminService {
    Response<GatewayConfigResponseDTO> saveGatewayConfig(GatewayConfigRequestDTO.GatewayConfig requestDTO);

    Response<GatewayConfigResponseDTO> saveGatewayToolConfig(GatewayConfigRequestDTO.GatewayToolConfig requestDTO);

    Response<GatewayConfigResponseDTO> saveGatewayProtocol(GatewayConfigRequestDTO.GatewayProtocol requestDTO);

    Response<GatewayConfigResponseDTO> saveGatewayAuth(GatewayConfigRequestDTO.GatewayAuth requestDTO);

    Response<List<GatewayConfigDTO>> queryGatewayConfigList();
}
