1. 编写业务Controller
    ```java
    @RestController
    public class MyFilterController {
        @GetMapping("/filter/index")
        public String index(){
            return "MyFilterController index" ;
        }
    }
    ```
2. 自定义过滤器规则
    ```java
    public class MyTypeExcludeFilter extends TypeExcludeFilter {
        //metadataReader：读取到的当前正在扫描的类的信息
        //metadataReaderFactory：可以获取到其他任何类的信息的（工厂）
        @Override
        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
            // 过滤掉 MyFilterController 的加载
            return metadataReader.getAnnotationMetadata().getClassName().equals(MyFilterController.class.getName()) ;
        }
    }
    ```
3. 将TypeExcludeFilter添加到容器中
    ```java
    public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            applicationContext.addBeanFactoryPostProcessor(new TypeExcludeFilterRegistry());
        }
        private static class TypeExcludeFilterRegistry
                implements PriorityOrdered, BeanDefinitionRegistryPostProcessor{
            @Override
            public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
                registry.registerBeanDefinition(
                        MyTypeExcludeFilter.class.getName(), new RootBeanDefinition(MyTypeExcludeFilter.class));
            }
            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            }
            @Override
            public int getOrder() {
                // 最高优先级
                return HIGHEST_PRECEDENCE;
            }
        }
    }
    ```
4. spring.factories中添加配置
    ```properties
    org.springframework.context.ApplicationContextInitializer=\
      com.yicj.study.ioc.excludefilter.MyApplicationContextInitializer
    ```
5. 验证发现无法访问：/filter/index接口