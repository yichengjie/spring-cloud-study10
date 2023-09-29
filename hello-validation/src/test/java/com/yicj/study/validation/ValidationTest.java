package com.yicj.study.validation;

import com.yicj.study.validation.config.AppConfig;
import com.yicj.study.validation.config.ValidationSkill;
import com.yicj.study.validation.model.UserVO;
import com.yicj.study.validation.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author: yicj
 * @date: 2023/9/29 14:07
 */
@Slf4j
@WebMvcTest
public class ValidationTest {

    @Autowired
    private MockMvc mockMvc ;

    @Test
    public void testBeanInitValidation(){
        Exception exception = assertThrows(Exception.class, () -> {
            AnnotationConfigApplicationContext context =
                    new AnnotationConfigApplicationContext(ValidationSkill.class, AppConfig.class);
        });
        log.info("exception message : {}", exception.getMessage(), exception);
    }

    @Test
    public void testMethodValidation(){
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ValidationSkill.class) ;
        UserService bean = context.getBean(UserService.class);
        Exception exception = assertThrows(Exception.class, () -> {
            UserVO vo = bean.createUser("a", 16);
        });
        log.info("exception message : {}", exception.getMessage(), exception);
    }

    @Test
    public void testCreateUser() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/createUser")
                .param("name", "a")
                .accept(MediaType.APPLICATION_JSON) ;
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions.andExpect(status().isOk())
                .andDo(print()) ;
    }
}
