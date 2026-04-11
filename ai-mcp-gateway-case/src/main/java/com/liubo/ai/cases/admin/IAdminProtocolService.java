package com.liubo.ai.cases.admin;

import com.liubo.ai.domain.protocol.model.entity.AnalysisCommandEntity;
import com.liubo.ai.domain.protocol.model.entity.StorageCommandEntity;
import com.liubo.ai.domain.protocol.model.valobj.HTTPProtocolVO;

import java.util.List;

/**
 * @author 68
 * 2026/4/6 20:31
 */
public interface IAdminProtocolService {
    void saveGatewayProtocol(StorageCommandEntity commandEntity);
    void deleteGatewayProtocol(Long protocolId);
    void importGatewayProtocol(AnalysisCommandEntity commandEntity);
    List<HTTPProtocolVO> analysisProtocol(AnalysisCommandEntity commandEntity);
}
