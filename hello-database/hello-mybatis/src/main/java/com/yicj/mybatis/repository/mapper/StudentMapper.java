package com.yicj.mybatis.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yicj.mybatis.model.dto.StudentDetailDTO;
import com.yicj.mybatis.model.form.ListStudentForm;
import com.yicj.mybatis.repository.entity.StudentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: yicj
 * @date: 2023/8/11 10:15
 */
@Mapper
public interface StudentMapper extends BaseMapper<StudentEntity> {

    @Select(""" 
            <script>
            select a.id, a.student_num, a.user_id, a.class_num, b.name as user_name, b.job, b.company
            from t_student a
            left join t_user b on a.user_id = b.id
            where 1=1
            <if test = "form.studentNum != null and form.studentNum != '' ">
                and a.student_num = #{form.studentNum}
            </if>
            <if test = "form.userName != null and form.userName != '' ">
                and b.name like concat('%', #{form.userName} ,'%')
            </if>
            </script>
            """)
    IPage<StudentDetailDTO> pageStudentDetail(
            @Param("page") IPage<StudentEntity> page,
            @Param("form") ListStudentForm form) ;

    @Select("""
            <script>
            select count(*) 
            from t_student a 
            where 1=1
            <if test = "form.studentNum != null and form.studentNum != '' ">
                and a.student_num = #{form.studentNum}
            </if>
            </script>
            """)
    Long countStudentDetail(@Param("form") ListStudentForm form) ;
}

