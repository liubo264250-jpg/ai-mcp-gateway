package com.liubo.ai.infrastructure.adapter.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liubo.ai.domain.admin.adapter.repository.IAdminRepository;
import com.liubo.ai.domain.admin.model.entity.*;
import com.liubo.ai.infrastructure.dao.po.*;
import com.liubo.ai.infrastructure.dao.service.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 68
 * 2026/4/6 20:26
 */
@Slf4j
@Repository
public class AdminRepository implements IAdminRepository {

    @Resource
    private McpGatewayService mcpGatewayService;

    @Resource
    private McpGatewayToolService mcpGatewayToolService;

    @Resource
    private McpProtocolHttpService mcpProtocolHttpService;

    @Resource
    private McpProtocolMappingService mcpProtocolMappingService;

    @Resource
    private McpGatewayAuthService mcpGatewayAuthService;

    @Override
    public List<GatewayConfigEntity> queryGatewayConfigList() {
        List<McpGateway> mcpGatewayPOS = mcpGatewayService.list();
        return mcpGatewayPOS.stream().map(po -> GatewayConfigEntity.builder()
                .gatewayId(po.getGatewayId())
                .gatewayName(po.getGatewayName())
                .gatewayDesc(po.getGatewayDesc())
                .version(po.getVersion())
                .auth(po.getAuth())
                .status(po.getStatus())
                .build()).collect(Collectors.toList());
    }

    @Override
    public GatewayConfigPageEntity queryGatewayConfigPage(GatewayConfigQueryEntity queryEntity) {
        Page<McpGateway> page = new Page<>(queryEntity.getPage(), queryEntity.getRows());
        LambdaQueryWrapper<McpGateway> wrapper = Wrappers.<McpGateway>lambdaQuery()
                .eq(queryEntity.getGatewayId() != null, McpGateway::getGatewayId, queryEntity.getGatewayId())
                .like(queryEntity.getGatewayName() != null, McpGateway::getGatewayName, queryEntity.getGatewayName());

        mcpGatewayService.page(page, wrapper);

        List<GatewayConfigEntity> dataList = page.getRecords().stream().map(po -> GatewayConfigEntity.builder()
                .gatewayId(po.getGatewayId())
                .gatewayName(po.getGatewayName())
                .gatewayDesc(po.getGatewayDesc())
                .version(po.getVersion())
                .auth(po.getAuth())
                .status(po.getStatus())
                .build()).collect(Collectors.toList());

        return GatewayConfigPageEntity.builder()
                .dataList(dataList)
                .total(page.getTotal())
                .build();
    }

    @Override
    public List<GatewayToolConfigEntity> queryGatewayToolList() {
        List<McpGatewayTool> mcpGatewayToolPOS = mcpGatewayToolService.list();
        return mcpGatewayToolPOS.stream().map(po -> GatewayToolConfigEntity.builder()
                .gatewayId(po.getGatewayId())
                .toolId(po.getToolId())
                .toolName(po.getToolName())
                .toolType(po.getToolType())
                .toolDescription(po.getToolDescription())
                .toolVersion(po.getToolVersion())
                .protocolId(po.getProtocolId())
                .protocolType(po.getProtocolType())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<GatewayProtocolConfigEntity> queryGatewayProtocolList() {
        List<McpProtocolHttp> pos = mcpProtocolHttpService.list();
        return pos.stream().map(po -> {
            List<McpProtocolMapping> mappings = mcpProtocolMappingService.list(Wrappers.<McpProtocolMapping>lambdaQuery()
                    .eq(McpProtocolMapping::getProtocolId, po.getProtocolId()));
            return GatewayProtocolConfigEntity.builder()
                    .protocolId(po.getProtocolId())
                    .httpUrl(po.getHttpUrl())
                    .httpMethod(po.getHttpMethod())
                    .httpHeaders(po.getHttpHeaders())
                    .timeout(po.getTimeout())
                    .mappings(mappings == null ? null : mappings.stream().map(m -> GatewayProtocolConfigEntity.ProtocolMappingEntity.builder()
                            .mappingType(m.getMappingType())
                            .parentPath(m.getParentPath())
                            .fieldName(m.getFieldName())
                            .mcpPath(m.getMcpPath())
                            .mcpType(m.getMcpType())
                            .mcpDesc(m.getMcpDesc())
                            .isRequired(m.getIsRequired())
                            .sortOrder(m.getSortOrder())
                            .build()).collect(Collectors.toList()))
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<GatewayAuthConfigEntity> queryGatewayAuthList() {
        List<McpGatewayAuth> mcpGatewayAuthPOS = mcpGatewayAuthService.list();
        return mcpGatewayAuthPOS.stream().map(po -> GatewayAuthConfigEntity.builder()
                .gatewayId(po.getGatewayId())
                .apiKey(po.getApiKey())
                .rateLimit(po.getRateLimit())
                .expireTime(po.getExpireTime())
                .build()).collect(Collectors.toList());
    }

    @Override
    public GatewayAuthPageEntity queryGatewayAuthPage(GatewayAuthQueryEntity queryEntity) {
        Page<McpGatewayAuth> page = new Page<>(queryEntity.getPage(), queryEntity.getRows());
        LambdaQueryWrapper<McpGatewayAuth> wrapper = Wrappers.<McpGatewayAuth>lambdaQuery()
                .eq(queryEntity.getGatewayId() != null, McpGatewayAuth::getGatewayId, queryEntity.getGatewayId());

        mcpGatewayAuthService.page(page, wrapper);

        List<GatewayAuthConfigEntity> dataList = page.getRecords().stream().map(po -> GatewayAuthConfigEntity.builder()
                .gatewayId(po.getGatewayId())
                .apiKey(po.getApiKey())
                .rateLimit(po.getRateLimit())
                .expireTime(po.getExpireTime())
                .build()).collect(Collectors.toList());

        return GatewayAuthPageEntity.builder()
                .dataList(dataList)
                .total(page.getTotal())
                .build();
    }

    @Override
    public GatewayToolPageEntity queryGatewayToolPage(GatewayToolQueryEntity queryEntity) {
        Page<McpGatewayTool> page = new Page<>(queryEntity.getPage(), queryEntity.getRows());
        LambdaQueryWrapper<McpGatewayTool> wrapper = Wrappers.<McpGatewayTool>lambdaQuery()
                .eq(queryEntity.getGatewayId() != null, McpGatewayTool::getGatewayId, queryEntity.getGatewayId())
                .eq(queryEntity.getToolId() != null && !queryEntity.getToolId().trim().isEmpty(), McpGatewayTool::getToolId, queryEntity.getToolId());

        mcpGatewayToolService.page(page, wrapper);

        List<GatewayToolConfigEntity> dataList = page.getRecords().stream().map(po -> GatewayToolConfigEntity.builder()
                .gatewayId(po.getGatewayId())
                .toolId(po.getToolId())
                .toolName(po.getToolName())
                .toolType(po.getToolType())
                .toolDescription(po.getToolDescription())
                .toolVersion(po.getToolVersion())
                .protocolId(po.getProtocolId())
                .protocolType(po.getProtocolType())
                .build()).collect(Collectors.toList());

        return GatewayToolPageEntity.builder()
                .dataList(dataList)
                .total(page.getTotal())
                .build();
    }

    @Override
    public List<GatewayToolConfigEntity> queryGatewayToolListByGatewayId(String gatewayId) {
        List<McpGatewayTool> pos = mcpGatewayToolService.list(Wrappers.<McpGatewayTool>lambdaQuery()
                .eq(McpGatewayTool::getGatewayId, gatewayId));
        return pos.stream().map(po -> GatewayToolConfigEntity.builder()
                .gatewayId(po.getGatewayId())
                .toolId(po.getToolId())
                .toolName(po.getToolName())
                .toolType(po.getToolType())
                .toolDescription(po.getToolDescription())
                .toolVersion(po.getToolVersion())
                .protocolId(po.getProtocolId())
                .protocolType(po.getProtocolType())
                .build()).collect(Collectors.toList());
    }

    @Override
    public GatewayProtocolPageEntity queryGatewayProtocolPage(GatewayProtocolQueryEntity queryEntity) {
        Page<McpProtocolHttp> page = new Page<>(queryEntity.getPage(), queryEntity.getRows());
        LambdaQueryWrapper<McpProtocolHttp> wrapper = Wrappers.<McpProtocolHttp>lambdaQuery()
                .eq(queryEntity.getProtocolId() != null, McpProtocolHttp::getProtocolId, queryEntity.getProtocolId())
                .like(queryEntity.getHttpUrl() != null, McpProtocolHttp::getHttpUrl, queryEntity.getHttpUrl());

        mcpProtocolHttpService.page(page, wrapper);

        List<McpProtocolHttp> pos = page.getRecords();
        if (pos.isEmpty()) {
            return GatewayProtocolPageEntity.builder()
                    .dataList(new ArrayList<>())
                    .total(page.getTotal())
                    .build();
        }

        List<Long> protocolIds = pos.stream().map(McpProtocolHttp::getProtocolId).collect(Collectors.toList());
        List<McpProtocolMapping> mappings = mcpProtocolMappingService.list(Wrappers.<McpProtocolMapping>lambdaQuery()
                .in(McpProtocolMapping::getProtocolId, protocolIds));

        List<GatewayProtocolConfigEntity> dataList = pos.stream().map(po -> {
            List<McpProtocolMapping> protocolMappings = mappings.stream()
                    .filter(m -> m.getProtocolId().equals(po.getProtocolId()))
                    .collect(Collectors.toList());

            return GatewayProtocolConfigEntity.builder()
                    .protocolId(po.getProtocolId())
                    .httpUrl(po.getHttpUrl())
                    .httpMethod(po.getHttpMethod())
                    .httpHeaders(po.getHttpHeaders())
                    .timeout(po.getTimeout())
                    .mappings(protocolMappings.isEmpty() ? null : protocolMappings.stream().map(m -> GatewayProtocolConfigEntity.ProtocolMappingEntity.builder()
                            .mappingType(m.getMappingType())
                            .parentPath(m.getParentPath())
                            .fieldName(m.getFieldName())
                            .mcpPath(m.getMcpPath())
                            .mcpType(m.getMcpType())
                            .mcpDesc(m.getMcpDesc())
                            .isRequired(m.getIsRequired())
                            .sortOrder(m.getSortOrder())
                            .build()).collect(Collectors.toList()))
                    .build();
        }).collect(Collectors.toList());

        return GatewayProtocolPageEntity.builder()
                .dataList(dataList)
                .total(page.getTotal())
                .build();
    }

    @Override
    public List<GatewayProtocolConfigEntity> queryGatewayProtocolListByProtocolIds(List<Long> protocolIds) {
        if (protocolIds == null || protocolIds.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        List<McpProtocolHttp> pos = mcpProtocolHttpService.list(Wrappers.<McpProtocolHttp>lambdaQuery()
                .in(McpProtocolHttp::getProtocolId, protocolIds));
        List<McpProtocolMapping> mappings = mcpProtocolMappingService.list(Wrappers.<McpProtocolMapping>lambdaQuery()
                .in(McpProtocolMapping::getProtocolId, protocolIds));

        return pos.stream().map(po -> {
            List<McpProtocolMapping> protocolMappings = mappings.stream()
                    .filter(m -> m.getProtocolId().equals(po.getProtocolId()))
                    .collect(Collectors.toList());

            return GatewayProtocolConfigEntity.builder()
                    .protocolId(po.getProtocolId())
                    .httpUrl(po.getHttpUrl())
                    .httpMethod(po.getHttpMethod())
                    .httpHeaders(po.getHttpHeaders())
                    .timeout(po.getTimeout())
                    .mappings(protocolMappings.isEmpty() ? null : protocolMappings.stream().map(m -> GatewayProtocolConfigEntity.ProtocolMappingEntity.builder()
                            .mappingType(m.getMappingType())
                            .parentPath(m.getParentPath())
                            .fieldName(m.getFieldName())
                            .mcpPath(m.getMcpPath())
                            .mcpType(m.getMcpType())
                            .mcpDesc(m.getMcpDesc())
                            .isRequired(m.getIsRequired())
                            .sortOrder(m.getSortOrder())
                            .build()).collect(Collectors.toList()))
                    .build();
        }).collect(Collectors.toList());
    }
}
