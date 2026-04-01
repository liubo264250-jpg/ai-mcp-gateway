package com.liubo.ai.infrastructure.adapter.repository;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liubo.ai.domain.session.adapter.repository.ISessionRepository;
import com.liubo.ai.domain.session.model.valobj.gateway.McpGatewayConfigVO;
import com.liubo.ai.domain.session.model.valobj.gateway.McpGatewayProtocolConfigVO;
import com.liubo.ai.domain.session.model.valobj.gateway.McpGatewayToolConfigVO;
import com.liubo.ai.infrastructure.dao.po.McpGateway;
import com.liubo.ai.infrastructure.dao.po.McpProtocolMapping;
import com.liubo.ai.infrastructure.dao.po.McpProtocolRegistry;
import com.liubo.ai.infrastructure.dao.service.McpGatewayService;
import com.liubo.ai.infrastructure.dao.service.McpProtocolMappingService;
import com.liubo.ai.infrastructure.dao.service.McpProtocolRegistryService;
import com.liubo.ai.types.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 68
 * 2026/3/29 22:51
 */
@Repository
public class SessionRepository implements ISessionRepository {

    @Autowired
    private McpGatewayService mcpGatewayService;

    @Autowired
    private McpProtocolRegistryService mcpProtocolRegistryService;

    @Autowired
    private McpProtocolMappingService mcpProtocolMappingService;

    @Override
    public McpGatewayConfigVO queryMcpGatewayConfigByGatewayId(String gatewayId) {
        if (StrUtil.isBlank(gatewayId)) return null;
        McpGateway mcpGateway = mcpGatewayService.getOne(Wrappers.<McpGateway>lambdaQuery()
                .eq(McpGateway::getGatewayId, gatewayId)
                .eq(McpGateway::getStatus, CommonConstant.ENABLE));
        if (mcpGateway == null) return null;

        McpProtocolRegistry mcpProtocolRegistry = mcpProtocolRegistryService.getOne(Wrappers.<McpProtocolRegistry>lambdaQuery()
                .eq(McpProtocolRegistry::getGatewayId, gatewayId)
                .eq(McpProtocolRegistry::getStatus, CommonConstant.ENABLE));
        if (null == mcpProtocolRegistry) return null;

        return McpGatewayConfigVO.builder()
                .gatewayId(mcpGateway.getGatewayId())
                .gatewayName(mcpGateway.getGatewayName())
                .toolId(mcpProtocolRegistry.getToolId())
                .toolName(mcpProtocolRegistry.getToolName())
                .toolDescription(mcpProtocolRegistry.getToolDescription())
                .toolVersion(mcpProtocolRegistry.getToolVersion())
                .build();
    }

    @Override
    public List<McpGatewayToolConfigVO> queryMcpGatewayToolConfigListByGatewayId(String gatewayId) {
        if (StrUtil.isBlank(gatewayId)) return null;
        McpGateway mcpGateway = mcpGatewayService.getOne(Wrappers.<McpGateway>lambdaQuery()
                .eq(McpGateway::getGatewayId, gatewayId)
                .eq(McpGateway::getStatus, CommonConstant.ENABLE));
        if (mcpGateway == null) return null;

        List<McpProtocolMapping> protocolMappingList = mcpProtocolMappingService.list(Wrappers.<McpProtocolMapping>lambdaQuery()
                .eq(McpProtocolMapping::getGatewayId, gatewayId));
        if (CollectionUtils.isEmpty(protocolMappingList)) return null;

        return protocolMappingList.stream().map(mcpProtocolMapping -> McpGatewayToolConfigVO.builder()
                .gatewayId(mcpProtocolMapping.getGatewayId())
                .toolId(mcpProtocolMapping.getToolId())
                .mappingType(mcpProtocolMapping.getMappingType())
                .parentPath(mcpProtocolMapping.getParentPath())
                .fieldName(mcpProtocolMapping.getFieldName())
                .mcpPath(mcpProtocolMapping.getMcpPath())
                .mcpType(mcpProtocolMapping.getMcpType())
                .mcpDesc(mcpProtocolMapping.getMcpDesc())
                .isRequired(mcpProtocolMapping.getIsRequired())
                .httpPath(mcpProtocolMapping.getHttpPath())
                .httpLocation(mcpProtocolMapping.getHttpLocation())
                .sortOrder(mcpProtocolMapping.getSortOrder())
                .build()).collect(Collectors.toList());
    }

    @Override
    public McpGatewayProtocolConfigVO queryMcpGatewayProtocolConfig(String gatewayId) {
        if (StrUtil.isBlank(gatewayId)) return null;
        McpProtocolRegistry mcpProtocolRegistry = mcpProtocolRegistryService.getOne(Wrappers.<McpProtocolRegistry>lambdaQuery()
                .eq(McpProtocolRegistry::getGatewayId, gatewayId)
                .eq(McpProtocolRegistry::getStatus, CommonConstant.ENABLE));
        if (null == mcpProtocolRegistry) return null;

        McpGatewayProtocolConfigVO.HTTPConfig httpConfig = McpGatewayProtocolConfigVO.HTTPConfig.builder()
                .httpUrl(mcpProtocolRegistry.getHttpUrl())
                .httpHeaders(mcpProtocolRegistry.getHttpHeaders())
                .timeout(mcpProtocolRegistry.getTimeout())
                .httpMethod(mcpProtocolRegistry.getHttpMethod())
                .build();
        return McpGatewayProtocolConfigVO.builder().httpConfig(httpConfig).build();
    }
}
