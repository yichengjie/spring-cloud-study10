1. 模仿form表单POST请求
    ```text
    POST http://127.0.0.1:9085/login
    Content-Type: application/x-www-form-urlencoded
    
    username=test&password=123
    ```
2. 带body体的POST请求
    ```text
    POST http://127.0.0.1:9085/login
    Content-Type: application/json
    
    {
      "username":"test",
      "password":"123456"
    }
    ```
3. 带状态的GET请求
    ```text
    GET http://127.0.0.1:9085/api/item/list
    Cookie: JessionId=TG4OKFVOZP6A9ML4
    Authorization: Bearer TG4OKFVOZP6A9ML4
    ```