package com.yicj.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yicj.mybatis.repository.entity.UserEntity;
import com.yicj.mybatis.repository.mapper.UserMapper;
import com.yicj.mybatis.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author: yicj
 * @date: 2023/8/11 11:20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

}
