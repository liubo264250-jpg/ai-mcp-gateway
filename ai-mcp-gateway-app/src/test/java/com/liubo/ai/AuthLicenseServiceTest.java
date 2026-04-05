package com.liubo.ai;

import com.liubo.ai.domain.auth.model.entity.LicenseCommandEntity;
import com.liubo.ai.domain.auth.service.IAuthLicenseService;
import com.liubo.ai.domain.auth.service.IAuthRegisterService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * @author 68
 * 2026/4/5 21:16
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class AuthLicenseServiceTest {
    @Resource
    private IAuthLicenseService authLicenseService;

    @Resource
    private IAuthRegisterService authRegisterService;

    @Test
    public void test_checkLicense() {
        // 1. 注册 可以走新注册 apiKey，或者找到数据表中现存的数据测试。
       /* RegisterCommandEntity registerCommandEntity = new RegisterCommandEntity();
        registerCommandEntity.setGatewayId("api-gateway-g4");
        registerCommandEntity.setRateLimit(10);
        registerCommandEntity.setExpireTime(new Date(System.currentTimeMillis() + 1000L * 60 * 60)); // 1小时后过期
        String apiKey = authRegisterService.register(registerCommandEntity);
        log.info("注册结果 apiKey: {}", apiKey);*/

        // 2. 鉴权
        LicenseCommandEntity commandEntity = new LicenseCommandEntity();
        commandEntity.setGatewayId("api-gateway-g4");
//        commandEntity.setApiKey(apiKey);
        commandEntity.setApiKey("gw-lf3HFzlJCdnrYl20oHbd5lJQxE7GWz8wjsSgjDZfctJNV8s5");

        boolean success = authLicenseService.checkLicense(commandEntity);
        log.info("鉴权结果 success: {}", success);
        assertTrue(success);
    }
}
