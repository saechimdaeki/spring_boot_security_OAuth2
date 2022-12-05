# OAUth 2.0 Resource Servcer

## AuthenticationEntryPoint

<img width="1104" alt="image" src="https://user-images.githubusercontent.com/40031858/205415697-abae4fac-462d-4e60-86a8-a3afa0ab77bd.png">


## 자동설정에 의한 초기화 과정

<img width="991" alt="image" src="https://user-images.githubusercontent.com/40031858/205415773-c8ddf35f-2c8f-4d25-aea4-aeb20e3a016b.png">

<img width="996" alt="image" src="https://user-images.githubusercontent.com/40031858/205415788-f03b4a4d-9b7e-4d1b-898b-4ff9d66d9c06.png">

## JWT API 설정 및 검증 프로세스 이해

```java
@Configuration(proxyBeanMethods = false )
public class OAuth2ResourceServerConfig {

    @Bean
    SecurityFilterChain jwtSecurityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt); // jwt 토큰을 검증하는 빈들과 클래스를 생성하고 초기화 함

        return http.build();

    }

}
```

<img width="1096" alt="image" src="https://user-images.githubusercontent.com/40031858/205541196-ebf57538-e501-473a-8d57-cd42713dcfbb.png">

<img width="1076" alt="image" src="https://user-images.githubusercontent.com/40031858/205541253-308f7b04-e52b-4949-8c12-a366c5f82131.png">

<img width="1114" alt="image" src="https://user-images.githubusercontent.com/40031858/205541282-b4f830a6-b2b2-4435-b6f7-e99aafb093dd.png">

## JwtDecoder 소개 및 세부 흐름

<img width="937" alt="image" src="https://user-images.githubusercontent.com/40031858/205553765-d768ea9f-283d-4784-9f18-0e4be5329761.png">

<img width="1042" alt="image" src="https://user-images.githubusercontent.com/40031858/205553788-d344ef9b-0ac0-43c8-8d6c-e373f7f42a13.png">

<img width="1095" alt="image" src="https://user-images.githubusercontent.com/40031858/205553833-90ee5c24-c60e-4829-a2b1-d815d49dfbb0.png">


## JwtDecoder 생성 방법

<img width="1064" alt="image" src="https://user-images.githubusercontent.com/40031858/205578054-b73a8002-49a6-4430-88f1-cef67fd38010.png">


