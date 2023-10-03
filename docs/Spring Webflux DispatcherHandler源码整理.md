#### DispatcherHandler的构造(以RequestMappingHandlerMapping为例)
1. WebFluxAutoConfiguration中EnableWebFluxConfiguration继承WebFluxConfigurationSupport
    ```text
    @Bean
    public DispatcherHandler webHandler() {
        return new DispatcherHandler();
    }
    ```
2. DispatcherHandler#setApplicationContext => initStrategies(applicationContext)
    ```text
    protected void initStrategies(ApplicationContext context) {
        Map<String, HandlerMapping> mappingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(
                context, HandlerMapping.class, true, false);
        ArrayList<HandlerMapping> mappings = new ArrayList<>(mappingBeans.values());
        AnnotationAwareOrderComparator.sort(mappings);
        this.handlerMappings = Collections.unmodifiableList(mappings);
        //
        Map<String, HandlerAdapter> adapterBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(
                context, HandlerAdapter.class, true, false);
        //
        this.handlerAdapters = new ArrayList<>(adapterBeans.values());
        AnnotationAwareOrderComparator.sort(this.handlerAdapters);
        //
        Map<String, HandlerResultHandler> beans = BeanFactoryUtils.beansOfTypeIncludingAncestors(
                context, HandlerResultHandler.class, true, false);
        this.resultHandlers = new ArrayList<>(beans.values());
        AnnotationAwareOrderComparator.sort(this.resultHandlers);
    }
    ```
3. RequestMappingHandlerMapping-> afterPropertiesSet -> AbstractHandlerMethodMapping.initHandlerMethods
   ```text
   protected void initHandlerMethods() {
        String[] beanNames = obtainApplicationContext().getBeanNamesForType(Object.class);
        for (String beanName : beanNames) {
            if (!beanName.startsWith(SCOPED_TARGET_NAME_PREFIX)) {
                Class<?> beanType = obtainApplicationContext().getType(beanName);
                // 当类被Controller或则RequestMapping标注时查找HandlerMethod
                if (beanType != null && isHandler(beanType)) {
                    detectHandlerMethods(beanName);
                }
            }
        }
    }
   ```
4. AbstractHandlerMethodMapping#detectHandlerMethods(beanName)
   ```text
    protected void detectHandlerMethods(final Object handler) {
        Class<?> handlerType = (handler instanceof String ?
                obtainApplicationContext().getType((String) handler) : handler.getClass());
        if (handlerType != null) {
            final Class<?> userType = ClassUtils.getUserClass(handlerType);
            Map<Method, T> methods = MethodIntrospector.selectMethods(userType,
                    (MethodIntrospector.MetadataLookup<T>) method -> getMappingForMethod(method, userType));
            methods.forEach((method, mapping) -> {
                Method invocableMethod = AopUtils.selectInvocableMethod(method, userType);
                // invocableMethod保存到MappingRegistry注册表中
                registerHandlerMethod(handler, invocableMethod, mapping);
            });
        }
    }
   ```
5. RequestMappingHandlerMapping#getMappingForMethod(method, handlerType)
   ```text
   protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = createRequestMappingInfo(method);
        if (info != null) {
            RequestMappingInfo typeInfo = createRequestMappingInfo(handlerType);
            if (typeInfo != null) {
                info = typeInfo.combine(info);
            }
            for (Map.Entry<String, Predicate<Class<?>>> entry : this.pathPrefixes.entrySet()) {
                if (entry.getValue().test(handlerType)) {
                    String prefix = entry.getKey();
                    if (this.embeddedValueResolver != null) {
                        prefix = this.embeddedValueResolver.resolveStringValue(prefix);
                    }
                    info = RequestMappingInfo.paths(prefix).options(this.config).build().combine(info);
                    break;
                }
            }
        }
        return info;
   }
   ```
6. RequestMappingHandlerMapping#createRequestMappingInfo(method)
   ```text
   private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
        RequestCondition<?> condition = (element instanceof Class ?
                getCustomTypeCondition((Class<?>) element) : getCustomMethodCondition((Method) element));
        return (requestMapping != null ? createRequestMappingInfo(requestMapping, condition) : null);
   }
   protected RequestMappingInfo createRequestMappingInfo(
			RequestMapping requestMapping, @Nullable RequestCondition<?> customCondition) {
		RequestMappingInfo.Builder builder = RequestMappingInfo
				.paths(resolveEmbeddedValuesInPatterns(requestMapping.path()))
				.methods(requestMapping.method())
				.params(requestMapping.params())
				.headers(requestMapping.headers())
				.consumes(requestMapping.consumes())
				.produces(requestMapping.produces())
				.mappingName(requestMapping.name());
		if (customCondition != null) {
			builder.customCondition(customCondition);
		}
		return builder.options(this.config).build();
	}
   ```
#### DispatcherHandler的执行(以RequestMappingHandlerMapping为例)
##### 查找handler
1. DispatcherHandler#handle(exchange)
    ```text
    public Mono<Void> handle(ServerWebExchange exchange) {
        if (CorsUtils.isPreFlightRequest(exchange.getRequest())) {
            return handlePreFlight(exchange);
        }
        return Flux.fromIterable(this.handlerMappings)
                .concatMap(mapping -> mapping.getHandler(exchange))
                .next()
                .switchIfEmpty(createNotFoundError())
                .flatMap(handler -> invokeHandler(exchange, handler))
                .flatMap(result -> handleResult(exchange, result));
    }
    ```
2. AbstractHandlerMapping#getHandler(exchange)
    ```text
    public Mono<Object> getHandler(ServerWebExchange exchange) {
        return getHandlerInternal(exchange) ;
    }
    ```
3. AbstractHandlerMethodMapping#getHandlerInternal
    ```text
    public Mono<HandlerMethod> getHandlerInternal(ServerWebExchange exchange) {
        this.mappingRegistry.acquireReadLock();
        try {
            HandlerMethod handlerMethod;
            try {
                handlerMethod = lookupHandlerMethod(exchange);
            }catch (Exception ex) {
                return Mono.error(ex);
            }
            if (handlerMethod != null) {
                handlerMethod = handlerMethod.createWithResolvedBean();
            }
            return Mono.justOrEmpty(handlerMethod);
        }finally {
            this.mappingRegistry.releaseReadLock();
        }
    }
    ```
4. AbstractHandlerMethodMapping#lookupHandlerMethod(exchange)
   ```text
   protected HandlerMethod lookupHandlerMethod(ServerWebExchange exchange) throws Exception {
        List<Match> matches = new ArrayList<>();
        List<T> directPathMatches = this.mappingRegistry.getMappingsByDirectPath(exchange);
        if (directPathMatches != null) {
            addMatchingMappings(directPathMatches, matches, exchange);
        }
        // 省略部分代码
        if (!matches.isEmpty()) {
            Comparator<Match> comparator = new MatchComparator(getMappingComparator(exchange));
            matches.sort(comparator);
            Match bestMatch = matches.get(0);
            handleMatch(bestMatch.mapping, bestMatch.getHandlerMethod(), exchange);
            return bestMatch.getHandlerMethod();
        }
        else {
            return handleNoMatch(this.mappingRegistry.getRegistrations().keySet(), exchange);
        }
   }
   ```
##### 执行handler
1. DispatcherHandler#invokeHandler
   ```text
   private Mono<HandlerResult> invokeHandler(ServerWebExchange exchange, Object handler) {
        for (HandlerAdapter handlerAdapter : this.handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter.handle(exchange, handler);
            }
        }
        return Mono.error(new IllegalStateException("No HandlerAdapter: " + handler));
   }
   ```
2. RequestMappingHandlerAdapter#handle(exchange, handler)
   ```text
   public Mono<HandlerResult> handle(ServerWebExchange exchange, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        InitBinderBindingContext bindingContext = new InitBinderBindingContext(
                getWebBindingInitializer(), this.methodResolver.getInitBinderMethods(handlerMethod));
        InvocableHandlerMethod invocableMethod = this.methodResolver.getRequestMappingMethod(handlerMethod);
        //
        Function<Throwable, Mono<HandlerResult>> exceptionHandler =
                ex -> handleException(ex, handlerMethod, bindingContext, exchange);
        //
        return this.modelInitializer
             .initModel(handlerMethod, bindingContext, exchange)
             .then(Mono.defer(() -> invocableMethod.invoke(exchange, bindingContext)))
             .doOnNext(result -> result.setExceptionHandler(exceptionHandler))
             .doOnNext(result -> bindingContext.saveModel())
             .onErrorResume(exceptionHandler);
   }
   ```
3. InvocableHandlerMethod#invoke
   ```text
   public Mono<HandlerResult> invoke(
            ServerWebExchange exchange, BindingContext bindingContext, Object... providedArgs) {
        return getMethodArgumentValues(exchange, bindingContext, providedArgs).flatMap(args -> {
            Object value;
            Method method = getBridgedMethod();
            if (KotlinDetector.isSuspendingFunction(method)) {
                value = CoroutinesUtils.invokeSuspendingFunction(method, getBean(), args);
            }else {
                value = method.invoke(getBean(), args);
            }
            // 省略部分代码
            HttpStatus status = getResponseStatus();
            if (status != null) {
                exchange.getResponse().setStatusCode(status);
            }
            MethodParameter returnType = getReturnType();
            ReactiveAdapter adapter = this.reactiveAdapterRegistry.getAdapter(returnType.getParameterType());
            boolean asyncVoid = isAsyncVoidReturnType(returnType, adapter);
            if ((value == null || asyncVoid) && isResponseHandled(args, exchange)) {
                return (asyncVoid ? Mono.from(adapter.toPublisher(value)) : Mono.empty());
            }
            HandlerResult result = new HandlerResult(this, value, returnType, bindingContext);
            return Mono.just(result);
        });
   }
   ```
##### 处理返回结果
1. DispatcherHandler#handleResult
   ```text
   private Mono<Void> handleResult(ServerWebExchange exchange, HandlerResult result) {
       return getResultHandler(result).handleResult(exchange, result) ;
   }
   ```
2. DispatcherHandler#getResultHandler => ResponseBodyResultHandler
3. ResponseBodyResultHandler#handleResult(exchange, result) 
   ```text
   public Mono<Void> handleResult(ServerWebExchange exchange, HandlerResult result) {
       Object body = result.getReturnValue();
       MethodParameter bodyTypeParameter = result.getReturnTypeSource();
       return writeBody(body, bodyTypeParameter, exchange);
   }
   ```
