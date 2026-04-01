package com.liubo.ai.infrastructure.adapter.port;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liubo.ai.domain.session.adapter.port.ISessionPort;
import com.liubo.ai.domain.session.model.valobj.gateway.McpGatewayProtocolConfigVO;
import com.liubo.ai.infrastructure.gateway.GenericHttpGateway;
import com.liubo.ai.types.enums.ResponseCode;
import com.liubo.ai.types.execption.AppException;
import jakarta.annotation.Resource;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 68
 * 2026/3/31 08:43
 */
@Component
public class SessionPort implements ISessionPort {
    @Resource
    private GenericHttpGateway gateway;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object toolCall(McpGatewayProtocolConfigVO.HTTPConfig httpConfig, Object params) throws IOException {
        String httpHeadersJson = httpConfig.getHttpHeaders();
        Map<String, Object> headers = objectMapper.readValue(httpHeadersJson, Map.class);
        String httpMethod = httpConfig.getHttpMethod().toLowerCase();
        if (!(params instanceof Map<?, ?> arguments)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        switch (httpMethod) {
            case "post" : {
                RequestBody requestBody = RequestBody.create(JSON.toJSONString(arguments.values().toArray()[0]), MediaType.parse("application/json"));
                Call<ResponseBody> call = gateway.post(httpConfig.getHttpUrl(), headers, requestBody);
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    return response.body().string();
                } else if (response.errorBody() != null) {
                    return response.errorBody().string();
                }
                return "Internal Error: Response body is null";
            }
            case "get" : {
                Map<String, Object> objMapRequest = new java.util.HashMap<>((Map<String, Object>) arguments.values().toArray()[0]);

                String url = httpConfig.getHttpUrl();
                // 替换路径参数
                Matcher matcher = Pattern.compile("\\{([^}]+)\\}").matcher(url);
                while (matcher.find()) {
                    String name = matcher.group(1);
                    if (objMapRequest.containsKey(name)) {
                        url = url.replace("{" + name + "}", String.valueOf(objMapRequest.get(name)));
                        objMapRequest.remove(name);
                    }
                }
                Call<ResponseBody> call = gateway.get(url, headers, objMapRequest);
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    return response.body().string();
                } else if (response.errorBody() != null) {
                    return response.errorBody().string();
                }
                return "Internal Error: Response body is null";
            }
        }
        throw new AppException(ResponseCode.METHOD_NOT_FOUND.getCode(), ResponseCode.METHOD_NOT_FOUND.getInfo());
    }
}
