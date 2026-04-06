package com.liubo.ai.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 68
 * 2026/4/6 19:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable{
    private static final long serialVersionUID = 7000723935764546321L;

    private String code;
    private String info;
    private T data;
}
