package com.liubo.ai.domain.protocol.adapter.repository;

import com.liubo.ai.domain.protocol.model.valobj.HTTPProtocolVO;

import java.util.List;

/**
 * @author 68
 * 2026/4/6 16:30
 */
public interface IProtocolRepository {
    List<Long> saveHttpProtocolAndMapping(List<HTTPProtocolVO> httpProtocolVOS);
}
