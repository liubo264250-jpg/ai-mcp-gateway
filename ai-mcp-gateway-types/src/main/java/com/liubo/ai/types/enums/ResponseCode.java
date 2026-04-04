package com.liubo.ai.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 68
 * 2026/3/25 08:10
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseCode {
    SUCCESS("0000", "成功"),
    UN_ERROR("0001", "未知失败"),
    ILLEGAL_PARAMETER("0002", "非法参数"),
    METHOD_NOT_FOUND("0003", "方法未找到"),
    CONFIG_NOT_EXIST("0004", "配置不存在"),
    ;
    private String code;
    private String info;
}
