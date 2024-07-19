# Getting Started

### Let's build and run our application.

1. First, let's pull our customized couchbase and todo app images that we pushed to Docker Hub on our machine with Docker installed.. Let's run the following 2 commands in the command prompt one by one.

``` dockerfile
    docker pull mertaksoyy/todoapp:latest
    docker pull mertaksoyy/couchbase-custom:latest
```

2. Let's pull the application codes from Github.

```  
https://github.com/MertAksoy95/ToDo-App.git
```

3. Then, at the command prompt, let's go to the directory where the “docker-compose.yml” file in the project is located and run the following command. In this way, our application will stand up within 35-40 seconds.

``` 
docker-compose up -d
```

4. [You can access the Couchbase Admin UI here and log in to the UI with the credentials below.](http://localhost:8091/ui/index.html)
``` 
url: http://localhost:8091/ui/index.html
username: admin
password: admin123
```

5. [You can access Swagger documentation here.](http://localhost:9100/purchasing/swagger-ui/index.html)
``` 
http://localhost:9100/purchasing/swagger-ui/index.html
```

6. Swagger arayüzünde, register olduğunuz kullanıcı ile veya initial olarak aşağıdaki credentials'a sahip hazır olan kullanıcı ile  oturum açıp test edebilirsiniz.
```
username: maksoy
password: mert123
```