package com.liubo.ai.domain.protocol.model.entity;

import com.liubo.ai.types.enums.AnalysisTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 68
 * 2026/4/6 11:57
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisCommandEntity {
    /**
     * 解析类型枚举
     */
    private AnalysisTypeEnum type;

    /**
     * swagger 导出的 api json 数据
     */
    private String openApiJson;

    /**
     * 解析的接口端点
     */
    private List<String> endpoints;
}
