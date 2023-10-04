### 导入配置类
1. EnableAspectJAutoProxy 注解导入 AspectJAutoProxyRegistrar
2. ImportBeanDefinitionRegistrar#registerBeanDefinitions向容器中加入AnnotationAwareAspectJAutoProxyCreator
3. AnnotationAwareAspectJAutoProxyCreator#initBeanFactory初始化ReflectiveAspectJAdvisorFactory
    ```text
    protected void initBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        super.initBeanFactory(beanFactory);
        if (this.aspectJAdvisorFactory == null) {
            this.aspectJAdvisorFactory = new ReflectiveAspectJAdvisorFactory(beanFactory);
        }
        this.aspectJAdvisorsBuilder =
                new BeanFactoryAspectJAdvisorsBuilderAdapter(beanFactory, this.aspectJAdvisorFactory);
    }
    ```
### 解析出所有的Advisor对象
1. AbstractAutoProxyCreator#postProcessBeforeInstantiation
   ```text
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
        Object cacheKey = getCacheKey(beanClass, beanName);
        if (!StringUtils.hasLength(beanName) || !this.targetSourcedBeans.contains(beanName)) {
            if (this.advisedBeans.containsKey(cacheKey)) {
                return null;
            }
            if (isInfrastructureClass(beanClass) || shouldSkip(beanClass, beanName)) {
                this.advisedBeans.put(cacheKey, Boolean.FALSE);
                return null;
            }
        }
        return null ;
    }
   ```
2. AspectJAwareAdvisorAutoProxyCreator#shouldSkip 
3. AnnotationAwareAspectJAutoProxyCreator#findCandidateAdvisors
4. BeanFactoryAspectJAdvisorsBuilder#buildAspectJAdvisors
5. 获取Spring容器中被Aspect注解的bean, 通过ReflectiveAspectJAdvisorFactory#getAdvisors获取Advisor
   ```text
   MetadataAwareAspectInstanceFactory factory =
       new BeanFactoryAspectInstanceFactory(this.beanFactory, beanName);
   List<Advisor> classAdvisors = this.advisorFactory.getAdvisors(factory);
   ```
6. ReflectiveAspectJAdvisorFactory#getAdvisors(factory)
   ```text
   public List<Advisor> getAdvisors(MetadataAwareAspectInstanceFactory aspectInstanceFactory) {
       Class<?> aspectClass = aspectInstanceFactory.getAspectMetadata().getAspectClass();
       String aspectName = aspectInstanceFactory.getAspectMetadata().getAspectName();
       MetadataAwareAspectInstanceFactory lazySingletonAspectInstanceFactory =
                new LazySingletonAspectInstanceFactoryDecorator(aspectInstanceFactory);
       List<Advisor> advisors = new ArrayList<>();
       for (Method method : getAdvisorMethods(aspectClass)) {
            Advisor advisor = getAdvisor(method, lazySingletonAspectInstanceFactory, 0, aspectName);
            if (advisor != null) {
                advisors.add(advisor);
            }
       }
   }
   ```
### 生产代理对象
1. AbstractAutoProxyCreator#postProcessAfterInitialization
   ```text
    public Object postProcessAfterInitialization(@Nullable Object bean, String beanName) {
        Object cacheKey = getCacheKey(bean.getClass(), beanName);
        if (this.earlyProxyReferences.remove(cacheKey) != bean) {
            return wrapIfNecessary(bean, beanName, cacheKey);
        }
        return bean;
    }
   ```
2. AbstractAutoProxyCreator#wrapIfNecessary
   ```text
   protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        // Create proxy if we have advice.
        Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, null);
        this.advisedBeans.put(cacheKey, Boolean.TRUE);
        Object proxy = createProxy(
            bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
        this.proxyTypes.put(cacheKey, proxy.getClass());
        return proxy;
   }
   ```
#### 获取符合条件的Advisor集合
1. AbstractAdvisorAutoProxyCreator#getAdvicesAndAdvisorsForBean
   ```text
   protected Object[] getAdvicesAndAdvisorsForBean(
            Class<?> beanClass, String beanName, @Nullable TargetSource targetSource) {
       List<Advisor> advisors = findEligibleAdvisors(beanClass, beanName);
       if (advisors.isEmpty()) {
           return DO_NOT_PROXY;
       }
       return advisors.toArray();
   }
   ```
2. AbstractAdvisorAutoProxyCreator#findEligibleAdvisors
   ```text
   protected List<Advisor> findEligibleAdvisors(Class<?> beanClass, String beanName) {
        List<Advisor> candidateAdvisors = findCandidateAdvisors();
        List<Advisor> eligibleAdvisors = findAdvisorsThatCanApply(candidateAdvisors, beanClass, beanName);
        extendAdvisors(eligibleAdvisors);
        if (!eligibleAdvisors.isEmpty()) {
            eligibleAdvisors = sortAdvisors(eligibleAdvisors);
        }
        return eligibleAdvisors;
   }
   ```
3. AbstractAdvisorAutoProxyCreator#findAdvisorsThatCanApply
   ```text
   protected List<Advisor> findAdvisorsThatCanApply(
            List<Advisor> candidateAdvisors, Class<?> beanClass, String beanName) {
       ProxyCreationContext.setCurrentProxiedBeanName(beanName);
       try {
           return AopUtils.findAdvisorsThatCanApply(candidateAdvisors, beanClass);
       }finally {
           ProxyCreationContext.setCurrentProxiedBeanName(null);
       }
   }
   ```
#### 创建代理对象 
1. AbstractAdvisorAutoProxyCreator#createProxy