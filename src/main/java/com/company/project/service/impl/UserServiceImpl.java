package com.company.project.service.impl;

import com.company.project.mapper.UserMapper;
import com.company.project.model.User;
import com.company.project.service.UserService;
import com.company.project.core.BaseCRUDService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/10/22.
 */
@Service
@Transactional
public class UserServiceImpl extends BaseCRUDService<User> implements UserService {
    @Resource
    private UserMapper userMapper;

}
