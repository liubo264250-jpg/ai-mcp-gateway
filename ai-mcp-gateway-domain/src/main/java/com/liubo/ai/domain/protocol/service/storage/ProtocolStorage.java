package com.liubo.ai.domain.protocol.service.storage;

import com.liubo.ai.domain.protocol.adapter.repository.IProtocolRepository;
import com.liubo.ai.domain.protocol.model.entity.StorageCommandEntity;
import com.liubo.ai.domain.protocol.service.IProtocolStorage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 68
 * 2026/4/6 12:00
 */
@Slf4j
@Service
public class ProtocolStorage implements IProtocolStorage {
    @Resource
    private IProtocolRepository repository;

    @Override
    public List<Long> doStorage(StorageCommandEntity commandEntity) {
        return repository.saveHttpProtocolAndMapping(commandEntity.getHttpProtocolVOS());
    }
}
