package com.liubo.ai.infrastructure.adapter.repository;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liubo.ai.domain.session.adapter.repository.ISessionRepository;
import com.liubo.ai.domain.session.model.valobj.gateway.McpGatewayConfigVO;
import com.liubo.ai.domain.session.model.valobj.gateway.McpToolConfigVO;
import com.liubo.ai.domain.session.model.valobj.gateway.McpToolProtocolConfigVO;
import com.liubo.ai.infrastructure.dao.po.McpGateway;
import com.liubo.ai.infrastructure.dao.po.McpGatewayTool;
import com.liubo.ai.infrastructure.dao.po.McpProtocolHttp;
import com.liubo.ai.infrastructure.dao.po.McpProtocolMapping;
import com.liubo.ai.infrastructure.dao.service.McpGatewayService;
import com.liubo.ai.infrastructure.dao.service.McpGatewayToolService;
import com.liubo.ai.infrastructure.dao.service.McpProtocolHttpService;
import com.liubo.ai.infrastructure.dao.service.McpProtocolMappingService;
import com.liubo.ai.types.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 68
 * 2026/3/29 22:51
 */
@Repository
public class SessionRepository implements ISessionRepository {

    @Autowired
    private McpGatewayService mcpGatewayService;

    @Autowired
    private McpProtocolHttpService mcpProtocolHttpService;

    @Autowired
    private McpGatewayToolService mcpGatewayToolService;

    @Autowired
    private McpProtocolMappingService mcpProtocolMappingService;

    @Override
    public McpGatewayConfigVO queryMcpGatewayConfigByGatewayId(String gatewayId) {
        if (StrUtil.isBlank(gatewayId)) return null;
        McpGateway mcpGateway = mcpGatewayService.getOne(Wrappers.<McpGateway>lambdaQuery()
                .eq(McpGateway::getGatewayId, gatewayId)
                .eq(McpGateway::getStatus, CommonConstant.ENABLE));
        if (mcpGateway == null) return null;
        return McpGatewayConfigVO.builder()
                .gatewayId(mcpGateway.getGatewayId())
                .gatewayName(mcpGateway.getGatewayName())
                .gatewayDesc(mcpGateway.getGatewayDesc())
                .version(mcpGateway.getVersion())
                .build();
    }

    @Override
    public List<McpToolConfigVO> queryMcpGatewayToolConfigListByGatewayId(String gatewayId) {
        List<McpToolConfigVO> mcpToolConfigVOS = new ArrayList<>();

        // 1. 查询工具列表
        List<McpGatewayTool> mcpGatewayToolPOList = mcpGatewayToolService.list(Wrappers.<McpGatewayTool>lambdaQuery()
                .eq(McpGatewayTool::getGatewayId, gatewayId));

        // 2. 组装参数信息
        for (McpGatewayTool tool : mcpGatewayToolPOList) {

            List<McpProtocolMapping> mappingPOList = mcpProtocolMappingService.list(Wrappers.<McpProtocolMapping>lambdaQuery()
                    .eq(McpProtocolMapping::getProtocolId, tool.getProtocolId()));

            List<McpToolProtocolConfigVO.ProtocolMapping> requestProtocolMappings = new ArrayList<>();

            // 协议信息
            for (McpProtocolMapping mcpProtocolMapping : mappingPOList) {
                McpToolProtocolConfigVO.ProtocolMapping protocolMapping = McpToolProtocolConfigVO.ProtocolMapping.builder()
                        .mappingType(mcpProtocolMapping.getMappingType())
                        .parentPath(mcpProtocolMapping.getParentPath())
                        .fieldName(mcpProtocolMapping.getFieldName())
                        .mcpPath(mcpProtocolMapping.getMcpPath())
                        .mcpType(mcpProtocolMapping.getMcpType())
                        .mcpDesc(mcpProtocolMapping.getMcpDesc())
                        .isRequired(mcpProtocolMapping.getIsRequired())
                        .sortOrder(mcpProtocolMapping.getSortOrder())
                        .build();
                requestProtocolMappings.add(protocolMapping);
            }

            // 组装数据
            McpToolConfigVO toolConfigVO = McpToolConfigVO.builder()
                    .gatewayId(tool.getGatewayId())
                    .toolId(tool.getToolId())
                    .toolName(tool.getToolName())
                    .toolDescription(tool.getToolDescription())
                    .toolVersion(tool.getToolVersion())
                    .mcpToolProtocolConfigVO(McpToolProtocolConfigVO.builder()
                            .requestProtocolMappings(requestProtocolMappings)
                            .build())
                    .build();

            mcpToolConfigVOS.add(toolConfigVO);
        }

        return mcpToolConfigVOS;
    }

    @Override
    public McpToolProtocolConfigVO queryMcpGatewayProtocolConfig(String gatewayId, String toolName) {
        // 获取协议ID - 根据网关ID + 工具名称
        McpGatewayTool mcpGatewayToolPO = mcpGatewayToolService.getOne(Wrappers.<McpGatewayTool>lambdaQuery()
                .eq(McpGatewayTool::getGatewayId, gatewayId)
                .eq(McpGatewayTool::getToolName, toolName));

        if (null == mcpGatewayToolPO) return null;
        Long protocolId = mcpGatewayToolPO.getProtocolId();

        // 查询协议
        McpProtocolHttp mcpProtocolHttpPO = mcpProtocolHttpService.getOne(Wrappers.<McpProtocolHttp>lambdaQuery()
                .eq(McpProtocolHttp::getProtocolId, protocolId)
                .eq(McpProtocolHttp::getStatus, CommonConstant.ENABLE));
        if (null == mcpProtocolHttpPO) return null;

        McpToolProtocolConfigVO.HTTPConfig httpConfig = new McpToolProtocolConfigVO.HTTPConfig();
        httpConfig.setHttpUrl(mcpProtocolHttpPO.getHttpUrl());
        httpConfig.setHttpHeaders(mcpProtocolHttpPO.getHttpHeaders());
        httpConfig.setHttpMethod(mcpProtocolHttpPO.getHttpMethod());
        httpConfig.setTimeout(mcpProtocolHttpPO.getTimeout());

        return McpToolProtocolConfigVO.builder().httpConfig(httpConfig).build();
    }
}
