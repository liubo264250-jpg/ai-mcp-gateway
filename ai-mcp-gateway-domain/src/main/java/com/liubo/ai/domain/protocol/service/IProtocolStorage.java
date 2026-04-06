package com.liubo.ai.domain.protocol.service;

import com.liubo.ai.domain.protocol.model.entity.StorageCommandEntity;

import java.util.List;

/**
 * @author 68
 * 2026/4/6 11:57
 */
public interface IProtocolStorage {
    List<Long> doStorage(StorageCommandEntity commandEntity);
}
