package com.liubo.ai.domain.auth.service;

import com.liubo.ai.domain.auth.model.entity.LicenseCommandEntity;

/**
 * @author 68
 * 2026/4/5 20:06
 */
public interface IAuthLicenseService {

    boolean checkLicense(LicenseCommandEntity commandEntity);
}
