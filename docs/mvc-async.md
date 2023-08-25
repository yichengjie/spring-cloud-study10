1. RequestMappingHandlerAdapter#handleInternal -> invokeHandlerMethod(request, response, handlerMethod)
2. 构造ServletInvocableHandlerMethod, AsyncWebRequest, WebAsyncManager 等对象
3. ServletInvocableHandlerMethod#invokeAndHandle(webRequest, mavContainer)
4. HandlerMethodReturnValueHandlerComposite#handleReturnValue(returnValue, getReturnValueType(returnValue), mavContainer, webRequest)
5. DeferredResultMethodReturnValueHandler#handleReturnValue(returnValue, returnType, mavContainer, webRequest)
6. WebAsyncUtils.getAsyncManager(webRequest).startDeferredResultProcessing(result, mavContainer);