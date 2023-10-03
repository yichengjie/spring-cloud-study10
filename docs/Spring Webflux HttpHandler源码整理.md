#### HttpHandler的构造
1. 自动启动配置类：HttpHandlerAutoConfiguration
   ```text
   @Bean
   public HttpHandler httpHandler(ObjectProvider<WebFluxProperties> propsProvider) {
       HttpHandler httpHandler = WebHttpHandlerBuilder.applicationContext(this.applicationContext).build();
       WebFluxProperties properties = propsProvider.getIfAvailable();
       if (properties != null && StringUtils.hasText(properties.getBasePath())) {
           Map<String, HttpHandler> handlersMap = Collections.singletonMap(properties.getBasePath(), httpHandler);
           return new ContextPathCompositeHandler(handlersMap);
       }
      return httpHandler;
   }
   ```
2. ApplicationContext.applicationContext
    ```text
    获取spring容器中的WebHandler并保存到WebHttpHandlerBuilder中 (这里传入的WebHandler为DispatcherHandler实例)
    获取spring容器中的WebFilter并保存到WebHttpHandlerBuilder中
    获取spring容器中的WebExceptionHandler并保存到WebHttpHandlerBuilder中
    获取spring容器中的HttpHandlerDecoratorFactory并保存到WebHttpHandlerBuilder中
    获取spring容器中WebSessionManager并保存到WebHttpHandlerBuilder中
    ...
    ```
3. WebHttpHandlerBuilder#build构建HttpHandler
   ```text
   public HttpHandler build() {
      WebHandler decorated = new FilteringWebHandler(this.webHandler, this.filters);
      decorated = new ExceptionHandlingWebHandler(decorated,  this.exceptionHandlers);
      HttpWebHandlerAdapter adapted = new HttpWebHandlerAdapter(decorated);
      adapted.setSessionManager(this.sessionManager);
      adapted.setCodecConfigurer(this.codecConfigurer);
      adapted.setLocaleContextResolver(this.localeContextResolver);
      adapted.setForwardedHeaderTransformer(this.forwardedHeaderTransformer);
      adapted.setApplicationContext(this.applicationContext);
      adapted.afterPropertiesSet();
      return (this.httpHandlerDecorator != null ? this.httpHandlerDecorator.apply(adapted) : adapted);
   }
   ```
4. 实例化FilteringWebHandler
   ```text
   public FilteringWebHandler(WebHandler handler, List<WebFilter> filters) {
       this.delegate = handler;
       this.chain = new DefaultWebFilterChain(handler, filters);
   }
   ```
5. 实例化DefaultWebFilterChain
   ```text
   public DefaultWebFilterChain(WebHandler handler, List<WebFilter> filters) {
        this.allFilters = Collections.unmodifiableList(filters);
        this.handler = handler;
        DefaultWebFilterChain chain = initChain(filters, handler);
        this.currentFilter = chain.currentFilter;
        this.chain = chain.chain;
    }
   ```
6. initChain代码片段
   ```text
    private static DefaultWebFilterChain initChain(List<WebFilter> filters, WebHandler handler) {
        DefaultWebFilterChain chain = new DefaultWebFilterChain(filters, handler, null, null);
        ListIterator<? extends WebFilter> iterator = filters.listIterator(filters.size());
        while (iterator.hasPrevious()) {
            chain = new DefaultWebFilterChain(filters, handler, iterator.previous(), chain);
        }
        return chain;
    }
   ```
#### HttpHandler的执行
1. HttpWebHandlerAdapter#handle执行
   ```text
   public Mono<Void> handle(ServerHttpRequest request, ServerHttpResponse response) {
      ServerWebExchange exchange = createExchange(request, response);
      return getDelegate().handle(exchange)
          .doOnSuccess(aVoid -> logResponse(exchange))
          .onErrorResume(ex -> handleUnresolvedError(exchange, ex))
          .then(Mono.defer(response::setComplete));
   }
   ```
2. ExceptionHandlingWebHandler#handle(exchange)执行
   ```text
   public Mono<Void> handle(ServerWebExchange exchange) {
        Mono<Void> completion;
        try {
            completion = super.handle(exchange);
        }catch (Throwable ex) {
            completion = Mono.error(ex);
        }
        for (WebExceptionHandler handler : this.exceptionHandlers) {
            completion = completion.onErrorResume(ex -> handler.handle(exchange, ex));
        }
        return completion;
   }
   ```
3. FilteringWebHandler#handle
   ```text
   public Mono<Void> handle(ServerWebExchange exchange) {
     return this.chain.filter(exchange);
   }
   ```
4. DefaultWebFilterChain#filter执行
   ```text
    public Mono<Void> filter(ServerWebExchange exchange) {
        return Mono.defer(() ->
             this.currentFilter != null && this.chain != null ?
                  invokeFilter(this.currentFilter, this.chain, exchange) :
                  this.handler.handle(exchange));
    }
    private Mono<Void> invokeFilter(WebFilter current, DefaultWebFilterChain chain, ServerWebExchange exchange) {
        String currentName = current.getClass().getName();
        return current.filter(exchange, chain).checkpoint(currentName + " [DefaultWebFilterChain]");
    }
   ```
5. DispatcherHandler#handle执行
