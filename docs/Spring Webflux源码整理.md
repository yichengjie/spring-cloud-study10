### HttpHandlerAutoConfiguration
#### HttpHandlerAutoConfiguration。AnnotationConfig#httpHandler构造HttpHandler
##### WebHttpHandlerBuilder.applicationContext
1. ApplicationContext.applicationContext
    ```text
    获取spring容器中的WebHandler并保存到WebHttpHandlerBuilder中
    获取spring容器中的WebFilter并保存到WebHttpHandlerBuilder中
    获取spring容器中的WebExceptionHandler并保存到WebHttpHandlerBuilder中
    获取spring容器中的HttpHandlerDecoratorFactory并保存到WebHttpHandlerBuilder中
    获取spring容器中WebSessionManager并保存到WebHttpHandlerBuilder中
    ...
    ```
2. WebHttpHandlerBuilder#build构建HttpHandler
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
### FilteringWebHandler构造
1. 实例化FilteringWebHandler
   ```text
   public FilteringWebHandler(WebHandler handler, List<WebFilter> filters) {
       this.delegate = handler;
       this.chain = new DefaultWebFilterChain(handler, filters);
   }
   ```
2. 实例化DefaultWebFilterChain
   ```text
   public DefaultWebFilterChain(WebHandler handler, List<WebFilter> filters) {
        this.allFilters = Collections.unmodifiableList(filters);
        this.handler = handler;
        DefaultWebFilterChain chain = initChain(filters, handler);
        this.currentFilter = chain.currentFilter;
        this.chain = chain.chain;
    }
   ```



### 问题记录
1. WebHandler是什么时候构建并放入Spring容器的？
