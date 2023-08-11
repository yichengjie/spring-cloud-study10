package com.yicj.mybatis.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: yicj
 * @date: 2023/8/11 8:58
 */
@Data
@TableName("user")
public class UserEntity {

    private Integer id ;

    private String name ;

    private String job ;

    private String company ;

}
