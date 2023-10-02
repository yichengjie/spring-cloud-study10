1. 添加依赖
    ```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.hibernate.validator</groupId>
            <artifactId>hibernate-validator</artifactId>
        </dependency>
        <dependency>
            <groupId>jakarta.validation</groupId>
            <artifactId>jakarta.validation-api</artifactId>
        </dependency>
        <dependency>
            <groupId>org.glassfish</groupId>
            <artifactId>jakarta.el</artifactId>
        </dependency>
    </dependencies>
    ```
#### Spring MVC输入参数校验(ModelAttributeMethodProcessor -> resolveArgument -> validateIfApplicable)
1. 校验核心代码编写
   ```java
   @RestController
   @RequestMapping("/user")
   public class UserController {
       @PostMapping("/createUser")
       public User createUser(@Valid User user){
           user.setDesc("this is user from demo");
           return user ;
       }
       @Data
       static class User{
           @Size(min = 3, max = 18, message = "name.length >=3 and <= 18")
           private String name ;
           private String desc ;
       }
       @ExceptionHandler
       public ResponseEntity<String> handleException(Exception ex){
          log.error("error : ", ex);
          return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK) ;
       }
   }
   ```
2. 编写单元测试
   ```java
   @WebMvcTest
   public class ValidationTest {
       @Autowired
       private MockMvc mockMvc ;
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
   ```
#### Spring管理的bean方法参数校验(MethodValidationPostProcessor)
1. 校验核心代码编写
   ```java
   @Service
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
   ```
2. 配置类编写
   ```java
   @Configuration
   public class ValidationSkill {
       /**
        * 方法执行时验证参数是否符合 JSR-303规范
        */
       @Bean
       public BeanPostProcessor methodValidationPostProcessor(){
           return new MethodValidationPostProcessor() ;
       }
       @Bean
       public UserService userService(){
          return new UserService() ;
       }
   }
   ```
3. 编写单元测试
   ```java
   @Slf4j
   public class ValidationTest {
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
   }
   ```
#### Spring初始化过程验证bean的属性 (BeanValidationPostProcessor)
1. 校验核心代码编写
   ```java
   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   @Validated
   public class UserVO implements Serializable {
       @NotNull(message = "name not be null")
       @Size(min = 3, max = 18, message = "name.length >=3 and <= 18")
       private String name ;
       //
       @Min(value = 18, message = "age must >= 18")
       private int age ;
   }
   ```
2. 配置类编写
   ```java
   @Configuration
   public class AppConfig {
       @Bean
       public UserVO userVo(){
           return new UserVO("test", 16) ;
       }
       /**
        *  bean 初始化验证是否符合JSR-303规范
        */
       @Bean
       public BeanPostProcessor beanValidationPostProcessor(){
           return new BeanValidationPostProcessor() ;
       }
   }
   ```
3. 编写单元测试
   ```java
   @Slf4j
   public class ValidationTest {
       @Test
       public void testBeanInitValidation(){
           Exception exception = assertThrows(Exception.class, () -> {
               AnnotationConfigApplicationContext context =
                       new AnnotationConfigApplicationContext(ValidationSkill.class, AppConfig.class);
           });
           log.info("exception message : {}", exception.getMessage(), exception);
       }
   }
   ```
#### Validator的使用
1. api测试
   ```java
   @Slf4j
   public class ValidatorTest {
       private Validator validator;
       @BeforeEach
       public void init(){
           this.validator = Validation.buildDefaultValidatorFactory().getValidator();
       }
       @Test
       public void validate(){
           Set<ConstraintViolation<UserVO>> validateList = validator.validate(new UserVO());
           validateList.forEach(item -> {
               Path propertyPath = item.getPropertyPath();
               String message = item.getMessage();
               log.info("property path : {}, message : {}", propertyPath, message);
           });
       }
       @Test
       public void validateParameters() throws NoSuchMethodException {
           UserService target = new UserService() ;
           Method methodToValidate = UserService.class.getMethod("createUser", String.class, int.class);
           Object[] parameterValues = {"a", 12} ;
           ExecutableValidator execVal = this.validator.forExecutables();
           Set<ConstraintViolation<UserService>> validateList =
                   execVal.validateParameters(target, methodToValidate, parameterValues);
           validateList.forEach(item -> {
               Path propertyPath = item.getPropertyPath();
               String message = item.getMessage();
               log.info("property path : {}, message : {}", propertyPath, message);
           });
       }
   }
   ```
