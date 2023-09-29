1. 编写Converter配置类
    ```java
    @Configuration
    public class ConverterSkillConfig {
        @Bean
        public ConversionServiceFactoryBean conversionService(){
            return new ConversionServiceFactoryBean() ;
        }
    }
    ```
2. 编写单元测试
    ```java
    @Slf4j
    @SpringJUnitConfig(ConverterSkillConfig.class)
    public class ConverterSkillTest {
        @Autowired
        private ConversionService conversionService ;
        @Test
        public void convert(){
            Long value = conversionService.convert("123", Long.class);
            log.info("value : {}", value);
        }
    }
    ```