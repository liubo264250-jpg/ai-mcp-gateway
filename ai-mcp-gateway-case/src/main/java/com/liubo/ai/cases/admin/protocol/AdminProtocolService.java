package com.liubo.ai.cases.admin.protocol;

import com.liubo.ai.cases.admin.IAdminProtocolService;
import com.liubo.ai.domain.protocol.model.entity.StorageCommandEntity;
import com.liubo.ai.domain.protocol.service.IProtocolStorage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 68
 * 2026/4/6 20:34
 */
@Slf4j
@Service
public class AdminProtocolService implements IAdminProtocolService {
    @Resource
    private IProtocolStorage protocolStorage;

    @Override
    public void saveGatewayProtocol(StorageCommandEntity commandEntity) {
        protocolStorage.doStorage(commandEntity);
    }
}
