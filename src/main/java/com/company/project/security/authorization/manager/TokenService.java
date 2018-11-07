package com.company.project.security.authorization.manager;


/**
 * 对Token进行操作的接口
 */
 public interface TokenService {

    /**
     * 创建一个token关联上指定用户，并存入redis缓存服务器
     * @param userId 指定用户的id
     * @return 生成的token
     */
     String createToken(Integer userId);

    /**
     * 检查授权信息是否有效
     * @param authorization 存储在header中的授权信息
     * @return 授权通过后返回userId
     */
     int parseAuthorization(String authorization);
//    User parseAuthorization(String authorization);

    /**
     * 清除token
     * @param userId 登录用户的id
     */
     void deleteToken(Integer userId);

}
