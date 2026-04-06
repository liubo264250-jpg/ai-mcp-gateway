package com.liubo.ai.domain.protocol.service;

import com.liubo.ai.domain.protocol.model.entity.AnalysisCommandEntity;
import com.liubo.ai.domain.protocol.model.valobj.HTTPProtocolVO;

import java.util.List;

/**
 * @author 68
 * 2026/4/6 11:57
 */
public interface IProtocolAnalysis {
    List<HTTPProtocolVO> doAnalysis(AnalysisCommandEntity commandEntity);
}
