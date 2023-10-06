package com.yicj.mybatis.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yicj.mybatis.repository.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: yicj
 * @date: 2023/8/11 9:01
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {


    Integer insertBatchSomeColumn(@Param("list") List<UserEntity> list) ;

    UserEntity findByUserId(Integer id) ;

    @Select("""
            <script>
            select *
            from t_user a 
            where 1=1
            <if test = "id != null ">
                and a.id = #{id}
            </if>
            <if test = "name != null and name != '' ">
                and a.name = #{name}
            </if>
            <if test = "job != null and job != '' ">
                and a.job = #{job}
            </if>
            <if test = "company != null and company != '' ">
                and a.company = #{company}
            </if>
            <if test = "createBy != null and createBy != '' ">
                and a.create_by = #{createBy}
            </if>
            </script>
            """)
    List<UserEntity> listByMultiParam(
            @Param("id") Integer id,
            @Param("name") String name,
            @Param("job") String job,
            @Param("company") String company,
            @Param("createBy") String createBy) ;

}
