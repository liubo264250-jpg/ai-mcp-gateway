package com.liubo.ai.domain.protocol.model.entity;

import com.liubo.ai.domain.protocol.model.valobj.HTTPProtocolVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 68
 * 2026/4/6 16:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StorageCommandEntity {
    /**
     * 协议列表数据
     */
    private List<HTTPProtocolVO> httpProtocolVOS;
}
