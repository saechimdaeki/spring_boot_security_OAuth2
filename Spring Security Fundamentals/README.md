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