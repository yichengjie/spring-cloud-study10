1. curl --dump-header - --header 'Host: www.circuitbreaker.com' http://localhost:8080/delay/3
### 加载filter
1. RouteDefinitionRouteLocator
```convertToRoute -> getFilters -> loadGatewayFilters```
2. ConfigurationService
```with(GatewayFilterFactory factory) ->  ```
3. RouteToRequestUrlFilter
#### 流程
1. DispatcherHandler -> mapping.getHandler
2. RoutePredicateHandlerMapping#getHandler(exchange)-> getHandlerInternal(exchange)
3. RouteLocator-> getRoutes -> Mono.just(route).filterWhen
4. AsyncPredicate -> apply(exchange) => Route (匹配到配置的一个Route信息)
5. FilteringWebHandler => SimpleHandlerAdapter#handle(exchange, handler) -> FilteringWebHandler#handle(exchange)
6. DefaultGatewayFilterChain(combined).filter(exchange)
#### 初始化过程
1. HttpHandlerAutoConfiguration => HttpHandler => WebHttpHandlerBuilder
2. 从Application 容器中获取webHandler:DispatcherHandler
3. 构建HttpHandler: HttpWebHandlerAdapter(decorated)
4. ReactiveWebServerApplicationContext#onRefresh -> createWebServer() => WebServerManager#start -> NettyWebServer#start
5. NettyReactiveWebServerFactory#getWebServer(this.handler) => NettyWebServer -> start
6. HttpWebHandlerAdapter#handle(request, response)
#### 执行流程
1. FilteringWebHandler
2. RouteToRequestUrlFilter
3. ReactiveLoadBalancerClientFilter
4. NettyRoutingFilter


