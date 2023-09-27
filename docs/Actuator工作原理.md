1. 添加spring-boot-starter-actuator
2. 按需开始：management.endpoints.web.exposure.include=*
3. 执行 *EndpointAutoConfiguration (EnvironmentEndpointAutoConfiguration,WebEndpointAutoConfiguration,
   MappingsEndpointAutoConfiguration,ShutdownEndpointAutoConfiguration)
4. 构建WebMvcEndpointHandlerMapping
5. WebMvcEndpointManagementContextConfiguration
6. 