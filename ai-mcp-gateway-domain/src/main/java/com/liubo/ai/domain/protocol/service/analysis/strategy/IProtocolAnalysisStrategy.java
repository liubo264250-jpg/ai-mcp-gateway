package com.liubo.ai.domain.protocol.service.analysis.strategy;

import com.alibaba.fastjson.JSONObject;
import com.liubo.ai.domain.protocol.model.valobj.HTTPProtocolVO;

import java.util.List;

/**
 * @author 68
 * 2026/4/6 12:02
 */
public interface IProtocolAnalysisStrategy {
    void doAnalysis(JSONObject operation, JSONObject definitions, List<HTTPProtocolVO.ProtocolMapping> mappings);
}
