package com.liubo.ai.infrastructure.adapter.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liubo.ai.domain.gateway.adapter.repository.IGatewayRepository;
import com.liubo.ai.domain.gateway.model.entity.GatewayConfigCommandEntity;
import com.liubo.ai.domain.gateway.model.entity.GatewayToolConfigCommandEntity;
import com.liubo.ai.domain.gateway.model.valobj.GatewayConfigVO;
import com.liubo.ai.domain.gateway.model.valobj.GatewayToolConfigVO;
import com.liubo.ai.infrastructure.dao.po.McpGateway;
import com.liubo.ai.infrastructure.dao.po.McpGatewayTool;
import com.liubo.ai.infrastructure.dao.service.McpGatewayService;
import com.liubo.ai.infrastructure.dao.service.McpGatewayToolService;
import com.liubo.ai.types.enums.GatewayEnum;
import com.liubo.ai.types.enums.ResponseCode;
import com.liubo.ai.types.execption.AppException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * @author 68
 * 2026/4/6 17:40
 */
@Repository
public class GatewayRepository implements IGatewayRepository {
    @Resource
    private McpGatewayService mcpGatewayService;

    @Resource
    private McpGatewayToolService mcpGatewayToolService;

    @Override
    public void saveGatewayConfig(GatewayConfigCommandEntity commandEntity) {
        GatewayConfigVO gatewayConfigVO = commandEntity.getGatewayConfigVO();

        McpGateway mcpGatewayPO = new McpGateway();
        mcpGatewayPO.setGatewayId(gatewayConfigVO.getGatewayId());
        mcpGatewayPO.setGatewayName(gatewayConfigVO.getGatewayName());
        mcpGatewayPO.setGatewayDesc(gatewayConfigVO.getGatewayDesc());
        mcpGatewayPO.setVersion(gatewayConfigVO.getVersion());
        mcpGatewayPO.setAuth(null != gatewayConfigVO.getAuth() ? gatewayConfigVO.getAuth().getCode() : GatewayEnum.GatewayAuthStatusEnum.ENABLE.getCode());
        mcpGatewayPO.setStatus(null != gatewayConfigVO.getStatus() ? gatewayConfigVO.getStatus().getCode() : GatewayEnum.GatewayStatus.NOT_VERIFIED.getCode());
        mcpGatewayService.save(mcpGatewayPO);
    }

    @Override
    public void updateGatewayAuthStatus(GatewayConfigCommandEntity commandEntity) {
        GatewayConfigVO gatewayConfigVO = commandEntity.getGatewayConfigVO();
        if (null == gatewayConfigVO.getAuth()) {
            return;
        }

        boolean success = mcpGatewayService.update(Wrappers.<McpGateway>lambdaUpdate()
                .set(McpGateway::getAuth, gatewayConfigVO.getAuth().getCode())
                .eq(McpGateway::getGatewayId, gatewayConfigVO.getGatewayId()));
        if (!success) {
            throw new AppException(ResponseCode.UN_ERROR.getCode(), ResponseCode.UN_ERROR.getInfo());
        }
    }

    @Override
    public void saveGatewayToolConfig(GatewayToolConfigCommandEntity commandEntity) {
        GatewayToolConfigVO gatewayToolConfigVO = commandEntity.getGatewayToolConfigVO();

        McpGatewayTool mcpGatewayToolPO = new McpGatewayTool();
        mcpGatewayToolPO.setGatewayId(gatewayToolConfigVO.getGatewayId());
        mcpGatewayToolPO.setToolId(gatewayToolConfigVO.getToolId());
        mcpGatewayToolPO.setToolName(gatewayToolConfigVO.getToolName());
        mcpGatewayToolPO.setToolType(gatewayToolConfigVO.getToolType());
        mcpGatewayToolPO.setToolDescription(gatewayToolConfigVO.getToolDescription());
        mcpGatewayToolPO.setToolVersion(gatewayToolConfigVO.getToolVersion());
        mcpGatewayToolPO.setProtocolId(gatewayToolConfigVO.getProtocolId());
        mcpGatewayToolPO.setProtocolType(gatewayToolConfigVO.getProtocolType());
        mcpGatewayToolService.save(mcpGatewayToolPO);
    }

    @Override
    public void updateGatewayToolProtocol(GatewayToolConfigCommandEntity commandEntity) {
        GatewayToolConfigVO gatewayToolConfigVO = commandEntity.getGatewayToolConfigVO();

        boolean success = mcpGatewayToolService.update(Wrappers.<McpGatewayTool>lambdaUpdate()
                .set(McpGatewayTool::getProtocolId, gatewayToolConfigVO.getProtocolId())
                .set(McpGatewayTool::getProtocolType, gatewayToolConfigVO.getProtocolType())
                .eq(McpGatewayTool::getGatewayId, gatewayToolConfigVO.getGatewayId()));
        if (!success) {
            throw new AppException(ResponseCode.UN_ERROR.getCode(), ResponseCode.UN_ERROR.getInfo());
        }
    }
}
