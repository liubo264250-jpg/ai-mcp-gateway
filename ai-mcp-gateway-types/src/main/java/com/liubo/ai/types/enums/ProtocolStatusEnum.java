package com.liubo.ai.types.enums;

import com.liubo.ai.types.execption.AppException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 68
 * 2026/4/6 16:40
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProtocolStatusEnum {

    ENABLE(1,"启用"),
    DISABLE(0,"禁用")
    ;

    private Integer code;
    private String info;

    public static ProtocolStatusEnum getByCode(Integer code){
        if(null == code){
            return null;
        }
        for (ProtocolStatusEnum anEnum : ProtocolStatusEnum.values()) {
            if(anEnum.getCode().equals(code)){
                return anEnum;
            }
        }

        throw new AppException(ResponseCode.ENUM_NOT_FOUND.getCode(), ResponseCode.ENUM_NOT_FOUND.getInfo());
    }

}
