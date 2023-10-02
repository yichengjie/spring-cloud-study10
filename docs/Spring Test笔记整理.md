#### Spring MVC 单元测试
1. 编写业务Controller业务代码
    ```java
    @RestController
    @RequestMapping("/user")
    public class UserController {
        @Autowired
        private UserService userService ;
        @GetMapping("/findById/{id}")
        public UserEntity findById(@PathVariable("id") Integer id){
            return userService.findById(id) ;
        }
    }
    ```
2. 编写单元测试类
    ```java
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
    ```