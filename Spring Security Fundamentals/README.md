# Spring Security Fundamentals

## 초기화 과정 이해 - SecurityBuilder / SecurityConfigurer

### 개념 및 구조 이해

- SecurityBuilder는 빌더 클래스로서 웹 보안을 구성하는 빈 객체와 설정클래스들을 생성하는 역할을 하며 WebSecurity, HttpSecurity가 있다
- SecurityConfigurer는 Http 요청과 관련된 보안처리를 담당하는 필터들을 생성하고 여러 초기화 설정에 관여한다
- SecurityBuilder는 SecurityConfigurer를 포함하고 있으며 인증 및 인가 초기화 작업은 SecurityConfigurer에 의해 진행된다

![image](https://user-images.githubusercontent.com/40031858/198452664-ae8ec922-d480-46d9-b960-5037ff346c7e.png)

<img width="966" alt="image" src="https://user-images.githubusercontent.com/40031858/198453922-c0f92d04-a214-46a7-9858-f2bf05be10e6.png">

<img width="1114" alt="image" src="https://user-images.githubusercontent.com/40031858/198456172-69f87b6e-62bd-4f0e-b37b-9bd4e2111e07.png">
