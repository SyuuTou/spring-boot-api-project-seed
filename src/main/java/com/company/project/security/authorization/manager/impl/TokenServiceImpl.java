package com.company.project.security.authorization.manager.impl;

import com.company.project.security.authorization.manager.TokenService;
import com.company.project.constant.SysConstant;
import com.company.project.core.ServiceException;
import com.company.project.enums.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * 通过Redis存储和验证token的实现类
 */
@Component
@Slf4j
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String createToken(Integer userId) {
        //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        //存储到redis并设置过期时间
        redisTemplate.boundValueOps(String.valueOf(userId)).set(token, SysConstant.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        //格式化为相关的 %s_%s 的授权信息供前端缓存，可以考虑增加加密
        String authorization = String.format(SysConstant.USER_TOKEN_FORMAT, userId, token);
        return authorization;
    }

    @Override
    public int parseAuthorization(String authorization) {
        //token格式不正确
        if (StringUtils.isEmpty(authorization)) {
            throw new ServiceException(ExceptionEnum.AUTHORIZATION_ERROR);
//            return 0;
        }

        String[] param = authorization.split("_");
        //token格式不正确
        if (param.length != 2) {
            throw new ServiceException(ExceptionEnum.AUTHORIZATION_ERROR);
//            return 0;
        }
        //使用userId和源token简单拼接成的token，可以增加加密措施
        String userId = param[0];
        String token = param[1];
        String cachedToken = (String) redisTemplate.boundValueOps(userId).get();
        log.info("用户授权信息解析出的token：【{}】;缓存在redis中的用户token：【{}】", token, cachedToken);

        //token已经过期
        if (cachedToken == null) {
            throw new ServiceException(ExceptionEnum.AUTHORIZATION_EXPIRE);
//            return 1;
        }
        //token不匹配，被别人强登录
        if (!token.equals(cachedToken)) {
            throw new ServiceException(ExceptionEnum.AUTHORIZATION_INVALIDATION);
//            return 2;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redisTemplate.boundValueOps(userId).expire(SysConstant.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return Integer.valueOf(userId);
    }

    @Override
    public void deleteToken(Integer userId) {
        redisTemplate.delete(String.valueOf(userId));
    }
}
