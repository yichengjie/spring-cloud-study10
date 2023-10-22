#### SpringApplication#prepareContext方法加载所有的配置Bean
1. BeanDefinitionLoader#load如果没有特殊设置，sources只有启动类一个
2. 进入BeanDefinitionLoader#load(Class<?> source)
3. AnnotatedBeanDefinitionReader#register(source)将启动类注册到容器中
#### 容器后处理ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry
1. 构建Bean容器refresh invokeBeanFactoryPostProcessors
2. ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry
3. 配置类解析ConfigurationClassParser#parse(AnnotationMetadata metadata, String beanName)
4. ConfigurationClassParser#processConfigurationClass(ConfigurationClass configClass, Predicate<String> filter)
5. ConfigurationClassParser#doProcessConfigurationClass
