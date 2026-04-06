package com.liubo.ai.tigger.http;

import com.liubo.ai.api.IAdminService;
import com.liubo.ai.api.dto.GatewayConfigDTO;
import com.liubo.ai.api.dto.GatewayConfigRequestDTO;
import com.liubo.ai.api.dto.GatewayConfigResponseDTO;
import com.liubo.ai.api.response.Response;
import com.liubo.ai.cases.admin.IAdminAuthService;
import com.liubo.ai.cases.admin.IAdminGatewayService;
import com.liubo.ai.cases.admin.IAdminManageService;
import com.liubo.ai.cases.admin.IAdminProtocolService;
import com.liubo.ai.domain.admin.model.entity.GatewayConfigEntity;
import com.liubo.ai.domain.auth.model.entity.RegisterCommandEntity;
import com.liubo.ai.domain.gateway.model.entity.GatewayConfigCommandEntity;
import com.liubo.ai.domain.gateway.model.entity.GatewayToolConfigCommandEntity;
import com.liubo.ai.domain.gateway.model.valobj.GatewayConfigVO;
import com.liubo.ai.domain.gateway.model.valobj.GatewayToolConfigVO;
import com.liubo.ai.domain.protocol.model.entity.StorageCommandEntity;
import com.liubo.ai.domain.protocol.model.valobj.HTTPProtocolVO;
import com.liubo.ai.types.enums.GatewayEnum;
import com.liubo.ai.types.enums.ResponseCode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 68
 * 2026/4/6 20:35
 */
@Slf4j
@RestController
@RequestMapping("/admin/")
public class AdminController implements IAdminService {
    @Resource
    private IAdminGatewayService adminGatewayService;
    @Resource
    private IAdminAuthService adminAuthService;
    @Resource
    private IAdminProtocolService adminProtocolService;
    @Resource
    private IAdminManageService adminManageService;

    @PostMapping(value = "save_gateway_config")
    @Override
    public Response<GatewayConfigResponseDTO> saveGatewayConfig(@RequestBody GatewayConfigRequestDTO.GatewayConfig requestDTO) {
        try {
            log.info("保存网关配置开始 gatewayId: {}", requestDTO.getGatewayId());
            GatewayConfigCommandEntity commandEntity = GatewayConfigCommandEntity.builder()
                    .gatewayConfigVO(GatewayConfigVO.builder()
                            .gatewayId(requestDTO.getGatewayId())
                            .gatewayName(requestDTO.getGatewayName())
                            .gatewayDesc(requestDTO.getGatewayDesc())
                            .version(requestDTO.getVersion())
                            .auth(GatewayEnum.GatewayAuthStatusEnum.getByCode(requestDTO.getAuth()))
                            .status(GatewayEnum.GatewayStatus.get(requestDTO.getStatus()))
                            .build())
                    .build();
            adminGatewayService.saveGatewayConfig(commandEntity);
            log.info("保存网关配置完成 gatewayId: {}", requestDTO.getGatewayId());
            return Response.<GatewayConfigResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(GatewayConfigResponseDTO.builder().success(true).build())
                    .build();
        } catch (Exception e) {
            log.error("保存网关配置失败 gatewayId: {}", requestDTO.getGatewayId(), e);
            return Response.<GatewayConfigResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    @PostMapping(value = "save_gateway_tool_config")
    @Override
    public Response<GatewayConfigResponseDTO> saveGatewayToolConfig(@RequestBody GatewayConfigRequestDTO.GatewayToolConfig requestDTO) {
        try {
            log.info("保存网关工具配置开始 gatewayId: {}", requestDTO.getGatewayId());
            GatewayToolConfigCommandEntity commandEntity = GatewayToolConfigCommandEntity.builder()
                    .gatewayToolConfigVO(GatewayToolConfigVO.builder()
                            .gatewayId(requestDTO.getGatewayId())
                            .toolId(requestDTO.getToolId())
                            .toolName(requestDTO.getToolName())
                            .toolType(requestDTO.getToolType())
                            .toolDescription(requestDTO.getToolDescription())
                            .toolVersion(requestDTO.getToolVersion())
                            .protocolId(requestDTO.getProtocolId())
                            .protocolType(requestDTO.getProtocolType())
                            .build())
                    .build();
            adminGatewayService.saveGatewayToolConfig(commandEntity);
            log.info("保存网关工具配置完成 gatewayId: {}", requestDTO.getGatewayId());
            return Response.<GatewayConfigResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(GatewayConfigResponseDTO.builder().success(true).build())
                    .build();
        } catch (Exception e) {
            log.error("保存网关工具配置失败 gatewayId: {}", requestDTO.getGatewayId(), e);
            return Response.<GatewayConfigResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    @PostMapping(value = "save_gateway_protocol")
    @Override
    public Response<GatewayConfigResponseDTO> saveGatewayProtocol(@RequestBody GatewayConfigRequestDTO.GatewayProtocol requestDTO) {
        try {
            log.info("保存网关协议配置开始");
            StorageCommandEntity commandEntity = new StorageCommandEntity();
            if (requestDTO.getHttpProtocols() != null) {
                commandEntity.setHttpProtocolVOS(requestDTO.getHttpProtocols().stream().map(p -> {
                    HTTPProtocolVO vo = new HTTPProtocolVO();
                    vo.setProtocolId(p.getProtocolId());
                    vo.setHttpUrl(p.getHttpUrl());
                    vo.setHttpHeaders(p.getHttpHeaders());
                    vo.setHttpMethod(p.getHttpMethod());
                    vo.setTimeout(p.getTimeout());
                    if (p.getMappings() != null) {
                        vo.setMappings(p.getMappings().stream().map(m -> HTTPProtocolVO.ProtocolMapping.builder()
                                .mappingType(m.getMappingType())
                                .parentPath(m.getParentPath())
                                .fieldName(m.getFieldName())
                                .mcpPath(m.getMcpPath())
                                .mcpType(m.getMcpType())
                                .mcpDesc(m.getMcpDesc())
                                .isRequired(m.getIsRequired())
                                .sortOrder(m.getSortOrder())
                                .build()).collect(Collectors.toList()));
                    }
                    return vo;
                }).collect(Collectors.toList()));
            }
            adminProtocolService.saveGatewayProtocol(commandEntity);
            log.info("保存网关协议配置完成");
            return Response.<GatewayConfigResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(GatewayConfigResponseDTO.builder().success(true).build())
                    .build();
        } catch (Exception e) {
            log.error("保存网关协议配置失败", e);
            return Response.<GatewayConfigResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    @PostMapping(value = "save_gateway_auth")
    @Override
    public Response<GatewayConfigResponseDTO> saveGatewayAuth(@RequestBody GatewayConfigRequestDTO.GatewayAuth requestDTO) {
        try {
            log.info("保存网关auth认证开始 gatewayId: {}", requestDTO.getGatewayId());
            RegisterCommandEntity commandEntity = RegisterCommandEntity.builder()
                    .gatewayId(requestDTO.getGatewayId())
                    .rateLimit(requestDTO.getRateLimit())
                    .expireTime(requestDTO.getExpireTime())
                    .build();
            adminAuthService.saveGatewayAuth(commandEntity);
            log.info("保存网关auth认证完成 gatewayId: {}", requestDTO.getGatewayId());
            return Response.<GatewayConfigResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(GatewayConfigResponseDTO.builder().success(true).build())
                    .build();
        } catch (Exception e) {
            log.error("保存网关auth认证失败 gatewayId: {}", requestDTO.getGatewayId(), e);
            return Response.<GatewayConfigResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    @GetMapping(value = "query_gateway_config_list")
    @Override
    public Response<List<GatewayConfigDTO>> queryGatewayConfigList() {
        try {
            log.info("查询网关配置列表开始");
            List<GatewayConfigEntity> entities = adminManageService.queryGatewayConfigList();
            List<GatewayConfigDTO> dtoList = entities.stream().map(e -> GatewayConfigDTO.builder()
                    .gatewayId(e.getGatewayId())
                    .gatewayName(e.getGatewayName())
                    .gatewayDesc(e.getGatewayDesc())
                    .version(e.getVersion())
                    .auth(e.getAuth())
                    .status(e.getStatus())
                    .build()).collect(Collectors.toList());
            log.info("查询网关配置列表完成 count: {}", dtoList.size());
            return Response.<List<GatewayConfigDTO>>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(dtoList)
                    .build();
        } catch (Exception e) {
            log.error("查询网关配置列表失败", e);
            return Response.<List<GatewayConfigDTO>>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}
