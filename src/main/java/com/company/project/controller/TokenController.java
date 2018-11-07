package com.company.project.controller;

import com.company.project.security.authorization.annotation.Authorization;
import com.company.project.security.authorization.annotation.CurrentUser;
import com.company.project.security.authorization.manager.TokenService;
import com.company.project.core.Result;
import com.company.project.model.User;
import com.company.project.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取和删除token的请求地址，在Restful设计中其实就对应着登录和退出登录的资源映射
 *
 * @author ScienJus
 * @date 2015/7/30.
 */
@RestController
@RequestMapping("/tokens")
public class TokenController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "登录")
    public ResponseEntity<Result> login(@RequestParam String username, @RequestParam String password) {
        Assert.notNull(username, "username can not be empty");
        Assert.notNull(password, "password can not be empty");

        User user = new User(username);
//        System.err.println(user);
//        userService.save(user);

        //生成授权信息，保存用户登录状态
        String authorization = tokenService.createToken(1);
        return new ResponseEntity<>(Result.success(authorization), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @Authorization
//    @ApiResponses(value = {@ApiResponse(code = 405,message = "Invalid input",response = Integer.class)})
    @ApiOperation(value = "退出登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
//            @ApiImplicitParam(name = "sign", value = "sign", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<Result> logout(@ApiParam(hidden = true)
                                             @CurrentUser User user) {
        System.err.println(user);
//        tokenService.deleteToken(user.getId());
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }

}
