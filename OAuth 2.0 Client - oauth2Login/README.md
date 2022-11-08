# OAuth 2.0 Client - oauth2Login()

## OAuth2LoginConfigurer 초기화 이해

### 설정 클래스 생성

```java
@Configuration(proxyBeanMethods = false)
public class CustomOAuth2ClientConfig {

    @Bean
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
        http.oauth2Login(Customizer.withDefaults());
        http.oauth2Client();
        return http.build();
    }
}

```

- API 설정
  - SecurityFilterChain 타입의 빈을 생성해서 보안 필터를 구성한다
  - HttpSecurity에 있는 oauth2Login()과 oauth2Client() API를 정의하고 빌드한다

<img width="1326" alt="image" src="https://user-images.githubusercontent.com/40031858/200452979-720a787c-f49a-49f0-aa1b-3412cfcbe2fa.png">

<img width="1333" alt="image" src="https://user-images.githubusercontent.com/40031858/200453015-6886e64e-6152-474c-90ba-e73c80331816.png">

<img width="1376" alt="image" src="https://user-images.githubusercontent.com/40031858/200453075-c21ffe79-f84c-4dfb-afc5-fe0666067ea7.png">

---

## OAuth2 로그인 구현 - OAuth 2.0 Login Page 생성

<img width="1403" alt="image" src="https://user-images.githubusercontent.com/40031858/200476475-40b9cf31-116c-4548-9bf9-f666a2ff8154.png">

