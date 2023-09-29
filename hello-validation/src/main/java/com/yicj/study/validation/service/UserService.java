package com.yicj.study.validation.service;

import com.yicj.study.validation.model.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author: yicj
 * @date: 2023/9/29 13:41
 */
@Validated
public class UserService {

    public UserVO createUser(
            @NotNull(message = "name not be null")
            @Size(min = 3, max = 18,message = "name.length >=3 and <= 18")
            String name,
            @Min(value = 18, message = "age must >= 18")
            int age
    ){
        UserVO userVO = new UserVO();
        userVO.setName(name);
        userVO.setAge(age);
        return userVO ;
    }
}
