package com.liubo.ai.cases.admin;

import com.liubo.ai.domain.protocol.model.entity.StorageCommandEntity;

/**
 * @author 68
 * 2026/4/6 20:31
 */
public interface IAdminProtocolService {
    void saveGatewayProtocol(StorageCommandEntity commandEntity);
}
