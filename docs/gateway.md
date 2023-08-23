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