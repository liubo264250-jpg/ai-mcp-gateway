package com.liubo.ai.types.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 68
 * 2026/3/27 09:21
 */
@Getter
@AllArgsConstructor
public enum SessionMessageHandlerMethodEnum {
    // 根据实际业务需求定义方法类型
    INITIALIZE("initialize", "initializeHandler", "初始化请求"),
    TOOLS_LIST("tools/list", "toolsListHandler", "工具列表请求"),
    TOOLS_CALL("tools/call", "toolsCallHandler", "工具调用请求"),
    RESOURCES_LIST("resources/list", "resourcesListHandler", "资源列表请求"),
    ;

    private final String method;
    private final String handlerName;
    private final String description;

    public final static Map<String, SessionMessageHandlerMethodEnum> ENUM_MAP = new HashMap<>();

    static {
        for (SessionMessageHandlerMethodEnum value : values()) {
            ENUM_MAP.put(value.getMethod(), value);
        }
    }

    /**
     * 根据方法名获取枚举
     *
     * @param method 方法名
     * @return 对应的枚举，如果找不到返回UNKNOWN
     */
    public static SessionMessageHandlerMethodEnum getByMethod(String method) {
        if (StrUtil.isBlank(method)) {
            throw new RuntimeException("method value is Empty!");
        }
        SessionMessageHandlerMethodEnum result = ENUM_MAP.get(method);
        if (result == null) {
            throw new RuntimeException("method value " + method + " not exist!");
        }
        return result;
    }
}
