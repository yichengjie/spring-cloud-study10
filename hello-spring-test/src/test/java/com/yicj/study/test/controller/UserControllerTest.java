package com.yicj.study.test.controller;

import com.yicj.study.test.repository.entity.UserEntity;
import com.yicj.study.test.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.BDDMockito.given;

@Slf4j
@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc ;

    @MockBean
    private UserService userService ;

    @Test
    void findById() throws Exception {
        UserEntity entity = new UserEntity() ;
        entity.setId(1);
        entity.setUsername("test mock user");
        given(this.userService.findById(1)).willReturn(entity) ;
        //
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/findById/1")
                .accept(MediaType.APPLICATION_JSON) ;
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions.andExpect(
                    MockMvcResultMatchers.status().isOk()
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.username")
                        .value("test mock user")
                ).andExpect(
                        MockMvcResultMatchers.content()
                        .string(Matchers.containsString("mock user"))
                ).andDo(MockMvcResultHandlers.print());
    }
}