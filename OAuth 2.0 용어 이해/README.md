# OAuth 2.0 용어 이해

## OAuth 2.0 소개

### 1. 개요

#### 1) Oauth = Open + Authorization

#### 2) The OAuth 2.0 Authorization Framework (RFC 6749 : https://ko.wikipedia.org/wiki/RFC)

- https://datatracker.ietf.org/doc/rfc6749/
- OAuth 2.0 인가 프레임워크는 애플리케이션이 사용자 대신하여 사용자의 자원에 대한 제한된 액세스를 얻기 위해 승인 상호 작용을 함으로써 

    애플리케이션이 자체적으로 액세스 권한을 얻도록 한다. 

- 즉 사용자가 속한 사이트의 보호된 자원(Protected Resource) 에 대하여 어플리케이션의 접근을 허용하도록 승인하는 것을 의미한다

#### 3) Delegated authorization framework – 위임 인가 프레임워크

- 어플리케이션이 사용자의 데이터에 접근하도록 권한을 부여한다

### 2. 등장 배경

<img width="895" alt="image" src="https://user-images.githubusercontent.com/40031858/198933240-57765aca-e5cf-4440-9e80-8080977df711.png">

<img width="1100" alt="image" src="https://user-images.githubusercontent.com/40031858/198933343-33a90f43-436c-492e-9ef8-f3b6c1fbe519.png">

<img width="1114" alt="image" src="https://user-images.githubusercontent.com/40031858/198933406-7ac42263-fb41-4196-83ac-fb8da5f69b64.png">


---

## Oauth 2.0 Roles 이해

### Oauth 2.0 Roles

- Oauth 2.0 메커니즘은 다음 네 가지 종류의 역할을 담당하는 주체들에 의해 이루어지는 권한부여 체계이다

#### 1. Resource Owner (자원 소유자)

- 보호된 자원에 대한 접근 권한을 부여할 수 있는 주체, 사용자로서 계정의 일부에 대한 접근 권한을 부여하는 사람
- 사용자를 대신하여 작동하려는 모든 클라이언트는 먼저 사용자의 허가를 받아야 한다

#### 2. Resource Server (보호자원서버)

- 타사 애플리케이션에서 접근하는 사용자의 자원이 포함된 서버를 의미한다
- 액세스 토큰을 수락 및 검증할 수 있어야 하며 권한 체계에 따라 요청을 승인할 수 있어야 한다

#### 3. Authorization Server (인가서버)

- 클라이언트가 사용자 계정에 대한 동의 및 접근을 요청할 때 상호 작용하는 서버로서 클라이언트의 권한 부여 요청을 승인하거나 거부하는 서버
- 사용자가 클라이언트에게 권한 부여 요청을 승인한 후 access token을 클라이언트에게 부여하는 역할

#### 4. Client(클라이언트)

- 사용자를 대신하여 권한을 부여받아 사용자의 리소스에 접근하려는 어플리케이션
- 사용자를 권한 부여 서버로 안내하거나 사용자의 상호 작용 없이 권한 부여 서버로부터 직접 권한을 얻을 수 있다.


<img width="1101" alt="image" src="https://user-images.githubusercontent.com/40031858/199006483-d3db2f80-6e0b-424a-b4e8-5691420161b0.png">


--- 

## Oauth 2.0 Client Types 이해

### Oauth 2.0 Client Types

- 개요
  - RFC 6749 - https://datatracker.ietf.org/doc/html/rfc6749#section-2.1
  - 인증 서버에 클라이언트를 등록할 때 클라이언트 자격 증명인 클라이언트 아이디와 클라이언트 암호를 받는다
  - 클라이언트 암호는 비밀이고 그대로 유지되어야 하는 반면 클라이언트 아이디는 공개이다
  - 이 자격 증명은 인증 서버에 대한 클라이언트 ID를 증명한다
- 기밀 클라이언트(Confidential Clients)
  - 기밀 클라이언트는 client-secret의 기밀성을 유지할 수 있는 클라이언트를 의미한다
  - 일반적으로 사용자가 소스코드에 액세스할 수 없는 서버에서 실행되는 응용프로그램으로 NET, Java, PHP 및 Node JS와 같은 서버측 언어로 작성된다
  - 이러한 유형의 애플리케이션은 대부분 웹 서버에서 실행되기 때문에 일반적으로 "웹 앱" 이라고 부른다
- 공개 클라이언트(Public Clients)
  - 공개 클라이언트는 client_secret의 기밀을 유지할 수 없으므로 이러한 앱에는 secret이 사용되지 않는다
  - 브라우저(SPA)에서 실행되는 JavaScript 애플리케이션, Android 또는 iOS 모바일 앱, 데스크톱에서 실행되는 기본 앱 뿐 아니라 IoT/임베디드 장치에서 실행되는 애플리케이션등이 있다
  - Chrome 개발자 콘솔이나 디스어셈블러 같은 디버깅 도구를 사용하여 바이너리/실행 코드에서 기밀 정보를 추출할 수 있기 때문에 공개로 간주된다
  - 서버측이 아닌 리소스 소유자가 사용하는 장치에서 실행되는 모든 클라이언트는 공개 클라이언트로 간주되어야 한다


<img width="1126" alt="image" src="https://user-images.githubusercontent.com/40031858/199011026-c2d3b71f-3429-4d73-852f-57f6d5f66cf6.png">

---

## Oauth 2.0 Token Types 이해

### Oauth 2.0 Token Types

#### 1. Access Token

- 클라이언트에서 사용자의 보호된 리소스에 접근하기 위해 사용하는 일종의 자격 증명으로서 역할을 하며 리소스 소유자가 클라이언트에게 부여한 권한 부여의 표현
- 일반적으로 JWT(JSON Web Tokens) 형식을 취하지만 사양에 따라 그럴 필요는 없다
- 토큰에는 해당 액세스 기간, 범위 및 서버에 필요한 기타 정보가 있다
- 타입에는 식별자 타입(Identifier Type)과 자체 포함타입(Self-contained Type)이 있다

#### 2. Refresh Token

- 액세스 토큰이 만료된 후 새 액세스 토큰을 얻기 위해 클라이언트 응용 프로그램에서 사용하는 자격 증명
- 액세스 토큰이 만료되는 경우 클라이언트는 권한 부여 서버로 인증하고 Refresh Token을 전달한다
- 인증 서버는 Refresh Token의 유효성을 검사하고 새 액세스 토큰을 발급한다
- Refresh Token은 액세스 토큰과 달리 권한 서버 토큰 엔드포인트에만 보내지고 리소스 서버에는 보내지 않는다

#### 3. ID Token

- openID Connect 챕터에서 정리

#### 4. Authorization Code

- 권한 부여 코드 흐름에서 사용되며 이 코드는 클라이언트가 액세스 토큰과 교환할 임시 코드임.
- 사용자가 클라이언트가 요청하는 정보를 확인하고 인가 서버로부터 리다이렉트 되어 받아온다

<img width="1118" alt="image" src="https://user-images.githubusercontent.com/40031858/199014964-722075f4-125c-46ef-83b5-085e442ac4dc.png">

<img width="1104" alt="image" src="https://user-images.githubusercontent.com/40031858/199015009-9ba56ecc-6091-45c6-8b9d-9573ce9b4784.png">

