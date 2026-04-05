package com.liubo.ai.domain.auth.service;

import com.liubo.ai.domain.auth.model.entity.RateLimitCommandEntity;

/**
 * @author 68
 * 2026/4/5 20:07
 */
public interface IAuthRateLimitService {

    boolean rateLimit(RateLimitCommandEntity commandEntity);
}
