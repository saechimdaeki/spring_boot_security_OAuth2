# Spring Security Fundamentals

## 초기화 과정 이해 - SecurityBuilder / SecurityConfigurer

### 개념 및 구조 이해

- SecurityBuilder는 빌더 클래스로서 웹 보안을 구성하는 빈 객체와 설정클래스들을 생성하는 역할을 하며 WebSecurity, HttpSecurity가 있다
- SecurityConfigurer는 Http 요청과 관련된 보안처리를 담당하는 필터들을 생성하고 여러 초기화 설정에 관여한다
- SecurityBuilder는 SecurityConfigurer를 포함하고 있으며 인증 및 인가 초기화 작업은 SecurityConfigurer에 의해 진행된다

![image](https://user-images.githubusercontent.com/40031858/198452664-ae8ec922-d480-46d9-b960-5037ff346c7e.png)

<img width="966" alt="image" src="https://user-images.githubusercontent.com/40031858/198453922-c0f92d04-a214-46a7-9858-f2bf05be10e6.png">

<img width="1114" alt="image" src="https://user-images.githubusercontent.com/40031858/198456172-69f87b6e-62bd-4f0e-b37b-9bd4e2111e07.png">


---


## 자동설정에 의한 초기화 과정 이해

<img width="1417" alt="image" src="https://user-images.githubusercontent.com/40031858/198754227-0345d383-d51e-4b9a-b949-576c51f94aec.png">

<img width="1395" alt="image" src="https://user-images.githubusercontent.com/40031858/198754436-b38b572a-a867-47db-83d8-c3f68fb6b99f.png">

- WebSecurity는 설정클래스에서 정의한 SecurityFilterChain 빈을 SecurityBuilder에 저장한다
- WebSecurity가 build()를 실행하면 SecurityBuilder에서 SecurityFilterChain을 꺼내어 FilterChainProxy 생성자에게 전달한다

---

## AuthenticationEntryPoint 이해

<img width="1351" alt="image" src="https://user-images.githubusercontent.com/40031858/198834459-58cbf614-ee75-490c-968c-ec78a3fc0e55.png">


---

## 시큐리티 인증 및 인가 흐름 요약

<img width="1133" alt="image" src="https://user-images.githubusercontent.com/40031858/198861843-005a0a99-ffa8-4fd0-8900-92c9d7946ad9.png">


---

## Http Basic 인증

<img width="1246" alt="image" src="https://user-images.githubusercontent.com/40031858/198864639-1f2b4ff7-1122-4642-b673-5708a5a07975.png">

### HttpBasicConfigurer

- HTTP Basic 인증에 대한 초기화를 진행하며 속성들에 대한 기본값들을 설정한다
- 기본 AuthenticationEntryPoint는 BasicAuthenticationEntryPoint 이다
- 필터는 BasicAuthenticationFilter를 사용한다

### BasicAuthenticationFilter

- 이 필터는 기본 인증 서비슬르 제공하는데 사용된다
- BasicAuthenticationConverter를 사용해서 요청 헤더에 기술된 인증정보의 유효성을 체크하며 Base64 인코딩된 username과 password를 추출한다
- 인증이 성공하면 Authentication이 SecurityContext에 저장되고 인증이 실패하면 Basic인증을 통해 다시 인증하라는 메시지를

    표시하는 BasicAuthenticationEntryPoint가 호출된다
- 인증 이후 세션을 사용하는 경우와 사용하지 않는 경우에 따라 처리되는 흐름에 차이가 있다

    세션을 사용하는 경우 매 요청마다 인증 과정을 거치지 않으나 세션을 사용하지 않는 경우 매 요청마다 인증과정을 거쳐야 한다

<img width="588" alt="image" src="https://user-images.githubusercontent.com/40031858/198864749-4325fe7f-a9e7-4a9d-b8f2-33f403a007c9.png">

<img width="1639" alt="image" src="https://user-images.githubusercontent.com/40031858/198864766-141c619e-0334-4e6f-9e0e-ab3267453f71.png">


---

## Cors 이해

### CORS(Cross-Origin Resource Sharing, 교차 출처 리소스 공유)

- HTTP 헤더를 사용하여, 한 출처에서 실행 중인 웹 애플리케이션이 다른 출처의 선택한 자원에 접근할 수 있는 권한을 부여하도록 브라우저에 알려주는 체제
- 웹 애플리케이션이 리소스가 자신의 출처와 다를 때 브라우저는 요청 헤더에 Origin 필드에 요청 출처를 함께 담아 교차 출처 HTTP 요청을  실행한다. 
- 출처를 비교하는 로직은 서버에 구현된 스펙이 아닌 브라우저에 구현된 스펙 기준으로 처리되며 브라우저는 클라이언트의 요청 헤더와 서버의 응답헤더를 비교해서 최종 응답을 결정한다
- 두개의 출처를 비교하는 방법은 URL의 구성요소 중 Protocol, Host, Port 이 세가지가 동일한지 확인하면 되고 나머지는 틀려도 상관없다

<img width="1373" alt="image" src="https://user-images.githubusercontent.com/40031858/198867651-ba8d0e10-4aed-43dd-b82d-ceea616ab9dd.png">


### CORWS 요청의 종류

#### Simple Request

- Simple Request는 예비 요청(Preflight)을 과정 없이 바로 서버에 본 요청을 한 후, 서버가 응답의 헤더에 Access-Control-Allow-Origin과 같은 값을 전송하면

    브라우저가 서로 비교 후 CORS 정책 위반여부를 검사하는 방식이다


- 제약 사항
  - GET, POST, HEAD 중의 한가지 Method를 사용해야 한다
  - 헤더는 Accept, Accept-Language, Content-Language, Content-Type, DPR, Downlink, Save-Data, Viewport-Width Width만 가능하고 Custom Header는 허용되지 않는다
  - Content-type은 applciation/x-www-form-urlencoded, multipart/form-data, text/plain만 가능하다

<img width="1384" alt="image" src="https://user-images.githubusercontent.com/40031858/198868253-e82ecd24-15c9-4648-b101-c8e492aa85cb.png">

#### Preflight Request(예비 요청)

- 브라우저는 요청을 한 번에 보내지 않고, 예비요청과 본요청으로 나누어 서버에 전달하는데 브라우저가 예비요청에을 보내는 것을 Preflight라고 하며
    
    이 예비 요청의 메소드에는 OPTIONS가 사용된다

- 예비요청의 역할은 본 요청을 보내기 전에 브라우저 스스로 안전한 요청인지 확인하는 것으로 요청 사양이 Simple Request 에 해당하지 않을 경우 브라우저가 Preflight Request 을 실행한다

<img width="1357" alt="image" src="https://user-images.githubusercontent.com/40031858/198868518-4b2fcd7e-63c5-46b7-9c6c-fa500e0b8a53.png">

<img width="1371" alt="image" src="https://user-images.githubusercontent.com/40031858/198868537-2c5b2f7a-5798-44fc-a9e7-4dc1a775613f.png">

<img width="1383" alt="image" src="https://user-images.githubusercontent.com/40031858/198868569-1e84a734-efb0-4596-b259-13eacf64fd90.png">

<img width="1364" alt="image" src="https://user-images.githubusercontent.com/40031858/198868558-2225e408-12ac-4b76-90ae-ab684f89fa3d.png">

#### API

```java
@Override
protected void configure(final HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .anyRequest().authenticated()
        .and();

    http.cors().configurationSource(corsConfigurationSource()); // CorsConfigurer 설정을 초기화 한다
}

@Bean
public CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration configuration = new Configuration();

    configuration.addAllowedOrigin("*");
    configuration.addAllowedMethod("*");
    configuration.addAllowedHeader("*");
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3660L);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    soruce.registerCorsConfiguration("/**", configuration);

    return source;
}

```