package com.yicj.study.mybatis.repository.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author: yicj
 * @date: 2023/8/11 8:58
 */
@Data
@Accessors(chain = true)
@TableName("t_user")
public class UserEntity {

    private Integer id ;

    private String name ;

    private String job ;

//    @FieldEncrypt
    private String company ;

    @TableLogic
    private Integer delFlag ;

    /**
     * 创建时间
     */
    // 注意！这里需要标记为填充字段
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime ;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy ;



    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyTime ;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String modifyBy ;

}
