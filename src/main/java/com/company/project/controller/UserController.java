package com.company.project.controller;

import com.company.project.core.Result;
import com.company.project.model.User;
import com.company.project.service.RedisService;
import com.company.project.service.UserService;
import com.company.project.security.sign.annotation.Sign;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by CodeGenerator on 2018/10/24.
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Autowired
    RedisService redisService;

    @PostMapping("qwer")
    @Sign
    public Result add(@RequestBody User user) {
        System.err.println(user);
        userService.save(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id, HttpServletRequest request) {

//        userService.deleteById(id);
        return Result.success();
    }

    @PutMapping
    @Sign
    public Result update(@RequestBody User user) {
        System.err.println(user);
//        userService.update(user);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        User user = userService.findById(id);
        return Result.success(user);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll();
        PageInfo pageInfo = new PageInfo(list);


        return Result.success(pageInfo);
    }

    @GetMapping("redis")
    public Result redis() {
        redisService.addValue("zhou", "dong");
        Object zhou = redisService.getValue("zhou");
        Object jiao = redisService.getValue("jiao");
        System.err.println(zhou);
        System.err.println(jiao);

        return null;
    }
}
