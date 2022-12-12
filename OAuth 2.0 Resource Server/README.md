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


# OAuth 2.0 Resource Server - 검증 기초

## JCA & JCE 

<img width="1430" alt="image" src="https://user-images.githubusercontent.com/40031858/206937119-3b91c822-ddca-48c0-ad8e-bd53f5df7937.png">

### MessageDigest

- 메시지 다이제스트의 목적은 원본 파일이 그대로인지 파악하는 무결성 검사이다. 
- 메시지 다이제스트 알고리즘은 입력 값으로 전달된 다양한 길이의 원본 값을 고정 길이 해시 값으로 출력한다. 
- 이 알고리즘은 단방향이기 때문에 해시 값에서 거꾸로 원본 값을 도출할 수 없다. 

<img width="1029" alt="image" src="https://user-images.githubusercontent.com/40031858/206937207-1925e31d-94b2-40c6-a245-7840da8da971.png">

- 갑과 을의 교신 상황에서 갑은 을에게 전달하고자 하는 원본과 그 원본의 메시지 해시 값 그리고 메시지 다이제스트 알고리즘을 보낸다. 
- 을은 갑이 전달한 알고리즘과 원본을 가지고 메시지 해시 값을 계산한다. 
- 을이 계산한 메시지 해시 값과 갑이 전달한 메시지 해시 값이 일치하면, 갑이 전달한 원본이 네트워크를 통해 을에게 오기까지 변경되지 않았다는 것을 확인할 수 있다.


### Signature

- Signature는 초기화 시 제공받은 키를 사용해서 데이터를 서명하고 전자 서명의 유효성을 검증 하는데 사용된다.

<img width="781" alt="image" src="https://user-images.githubusercontent.com/40031858/206937288-2a02979b-0ee0-4840-a4a0-994a6cbec038.png">

- 서명
  - Signature 객체는 개인 키로 서명 하기 위해 초기화되고 서명할 원본 데이터가 제공된다
  - Signature 의 sign() 은 개인 키로 원본 데이터를 서명 하면 해시된 데이터를 암호화한 Signature Bytes 를 반환한다
- 검증
  - 검증이 필요한 경우 검증을 위해 Signature객체를 생성 및 초기화하고 개인키와 쌍을 이루는 해당 공개 키를 제공한다. 
  - 원본 데이터와 Signature Bytes 가 검증 Signature 객체에 전달되고 verify() 를 실행하면 공개키로 Signature Bytes 의 해시데이터를 추출하고 원본데이터를 해시한 값과 비교해서 일치하면 Signature 객체가 성공을 보고한다.

- 서명은 메시지 다이제스트와 비대칭키 암호화가 결합한 형태로서 "SHA256WithRSA” 처럼 메시지 다이제스트 알고리즘인 "SHA256"을 사용하여 초기에 대규모 데이터를 보다 관리하기 쉬운 고정길이의 형식으로 "압축"한 다음 비대칭키 암호화인 "RSA" 알고리즘으로 고정길이의 32바이트 메시지 다이제스트에 서명한다


