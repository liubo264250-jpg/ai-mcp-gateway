package com.liubo.ai.infrastructure.adapter.repository;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liubo.ai.domain.protocol.adapter.repository.IProtocolRepository;
import com.liubo.ai.domain.protocol.model.valobj.HTTPProtocolVO;
import com.liubo.ai.infrastructure.dao.po.McpProtocolHttp;
import com.liubo.ai.infrastructure.dao.po.McpProtocolMapping;
import com.liubo.ai.infrastructure.dao.service.McpProtocolHttpService;
import com.liubo.ai.infrastructure.dao.service.McpProtocolMappingService;
import com.liubo.ai.types.enums.ProtocolStatusEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 68
 * 2026/4/6 16:33
 */
@Repository
public class ProtocolRepository implements IProtocolRepository {

    @Resource
    private McpProtocolHttpService  mcpProtocolHttpService;

    @Resource
    private McpProtocolMappingService  mcpProtocolMappingService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> saveHttpProtocolAndMapping(List<HTTPProtocolVO> httpProtocolVOS) {
        List<Long> protocolIdList = new ArrayList<>();

        for (HTTPProtocolVO httpProtocolVO : httpProtocolVOS) {

            // 0. 生成协议ID，八位数字的。
            long protocolId = Long.parseLong(RandomUtil.randomNumbers(8));

            // 1. 保存 HTTP 协议配置
            McpProtocolHttp mcpProtocolHttpPO = new McpProtocolHttp();
            mcpProtocolHttpPO.setProtocolId(protocolId);
            mcpProtocolHttpPO.setHttpUrl(httpProtocolVO.getHttpUrl());
            mcpProtocolHttpPO.setHttpMethod(httpProtocolVO.getHttpMethod());
            mcpProtocolHttpPO.setHttpHeaders(httpProtocolVO.getHttpHeaders());
            mcpProtocolHttpPO.setTimeout(httpProtocolVO.getTimeout());
            mcpProtocolHttpPO.setRetryTimes(3);
            mcpProtocolHttpPO.setStatus(ProtocolStatusEnum.ENABLE.getCode());
            mcpProtocolHttpService.save(mcpProtocolHttpPO);

            // 2. 保存协议映射配置
            List<HTTPProtocolVO.ProtocolMapping> mappings = httpProtocolVO.getMappings();
            if (null == mappings || mappings.isEmpty()) continue;

            for (HTTPProtocolVO.ProtocolMapping mapping : mappings) {
                McpProtocolMapping mcpProtocolMappingPO = new McpProtocolMapping();
                mcpProtocolMappingPO.setProtocolId(protocolId);
                mcpProtocolMappingPO.setMappingType(mapping.getMappingType());
                mcpProtocolMappingPO.setParentPath(mapping.getParentPath());
                mcpProtocolMappingPO.setFieldName(mapping.getFieldName());
                mcpProtocolMappingPO.setMcpPath(mapping.getMcpPath());
                mcpProtocolMappingPO.setMcpType(mapping.getMcpType());
                mcpProtocolMappingPO.setMcpDesc(mapping.getMcpDesc());
                mcpProtocolMappingPO.setIsRequired(mapping.getIsRequired());
                mcpProtocolMappingPO.setSortOrder(mapping.getSortOrder());
                mcpProtocolMappingService.save(mcpProtocolMappingPO);
            }

            protocolIdList.add(protocolId);
        }

        return protocolIdList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteGatewayProtocol(Long protocolId) {
        mcpProtocolHttpService.remove(Wrappers.<McpProtocolHttp>lambdaQuery().eq(McpProtocolHttp::getProtocolId, protocolId));
        mcpProtocolMappingService.remove(Wrappers.<McpProtocolMapping>lambdaQuery().eq(McpProtocolMapping::getProtocolId, protocolId));
    }
}
