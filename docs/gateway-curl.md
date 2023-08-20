### predicates 
1. curl -X POST -H 'Content-Type: application/json' -I  http://localhost:8080/add_request_parameter_route
2. curl -X GET --header 'Host: www.somehost.org' -I http://localhost:8080/add_response_header_route
3. curl -X GET -I http://localhost:8080/red/1
4. curl -X GET -I http://localhost:8080?red=green
5. curl -X GET --header 'X-Forwarded-For: 192.168.1.1' -I http://localhost:8080
6. curl -X GET -H "X-Forwarded-For: 192.168.1.1" -H 'Content-Type: application/json;charset=UTF-8' -H 'Accept: application/json' -I -v http://localhost:8080
### filter
1. curl -X GET -I http://localhost:8080/red/1
   