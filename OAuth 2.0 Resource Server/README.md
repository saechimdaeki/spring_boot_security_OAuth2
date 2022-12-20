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


## JCA & JCE - 대칭키 & 비대칭키

### 대칭키 암호(symmetric-key algorithm)

- 암호화 알고리즘의 한 종류로, 암호화와 복호화에 같은 암호 키를 쓰는 알고리즘을 의미한다.
- 대칭 키 암호에서는 암호화를 하는 측과 복호화를 하는 측이 같은 암호 키를 공유해야 한다. 
- 비대칭 키 암호에서 공개 키와 비밀 키를 별도로 가지는 것과 구별되며 대부분의 대칭 키 암호는 비대칭 키 암호와 비교하여 계산 속도가 빠르다는 장점을 가진다. 

- `MAC(Message Authentication Code)`
  - 메시지 인증 코드는 데이터가 변조(수정, 삭제, 삽입 등) 되었는지를 검증할 수 있도록 데이터에 덧붙이는 코드
  - 해시 값을 생성한다는 점에서 메시지 다이제스트와 비슷하지만, 초기화 시 비밀키(SecretKey, 대칭키)를 요구한다는 점에서 다르다. 
  - 메시지 다이제스트는 받은 측이 누구든 무결성 검사가 가능하지만, MAC은 오직 동일한 비밀 키를 가진 쪽에서만 전달받은 메시지의 무결성을 검사 할 수 있다. 
  - 암호화 해시 함수(MD5, SHA256 등)를 기반으로 하는 MAC이 잘 알려진 HMAC이다. 
  - HMAC은 메시지 다이제스트 알고리즘과 공유된 비밀 키의 조합으로 가능하며 데이터의 무결성과 기밀성, 송.수신자간 인증을 보증하기 위한 암호화 기법이다

<img width="901" alt="image" src="https://user-images.githubusercontent.com/40031858/208587796-95080208-8e33-4ca4-86d5-9b83857f4400.png">

### 비 대칭키 암호 (asymmetric-key algorithm)

- 암호화 알고리즘의 한 종류로, 암호화와 복호화에 다른 암호 키를 쓰는 알고리즘을 의미한다.
- 외부에 절대 노출되어서는 안되는 개인키(Private key)와 공개적으로 개방되어 있는 공개키(Private key)를 쌍으로 이룬 형태이다
  - A의 공개키를 이용하여 암호화된 데이터는 A의 개인키로만 복호화가 가능하다.
  - A의 개인키를 이용하여 암호화된 데이터는 A의 공개키로만 복호화가 가능하다
- 비대칭키를 사용하여 두가지 암호학적 문제를 해결할 수 있다
  1. 데이터 보안 : 송신자 공개키로 암호화 -> 송신자 개인키로 복호화를 통해 데이터를 안전하게 전송할 수 있는 보안 관점
  2. 인증 : 송신자 개인키로 암호화 -> 송신자 공개키로 복호화를 통해 메시지를 인증(부인방지)하는 것이 목적

- RSA (Ron Rivest, Adi Shamir, Leonard Adleman 세 사람의 성을 따서 RSA 라고 이름이 붙은 암호 방식) 
  - 현재 SSL/TLS에 가장 많이 사용되는 공개키 암호화 알고리즘으로 전세계 대부분의 인터넷 뱅킹(대한민국 포함)이 이 RSA-2048 암호화를 사용한다.

<img width="924" alt="image" src="https://user-images.githubusercontent.com/40031858/208588332-9bba5374-858a-4950-a4fe-d8e48c2dbd29.png">


### Key 생성 모델

- Key
  - JCA에서 지원하는 모든 종류의 키에 대한 최상위 인터페이스
  - Key 인터페이스는 getAlgorithm(), getEncoded(), getFormat() 의 세 가지 메서드를 제공한다
    - getAlgorithm() : 키 알고리즘은 보통 대칭키 암호 방식(AES,DSA 등) 또는 비대칭키 연산 알고리즘(RSA)이다. 
    - getEncoded() : 기본 인코딩된 형식의 키를 반환
    - getFormat() : 이 키의 기본 인코딩 형식의 이름을 반환(표준형식인 X509 또는 PKCS8)
- KeyPair
  - 키 쌍(공개 키와 개인 키)을 보관하고 접근할 수 있는 기능만 제공한다
- KeyPairGenerator
  - 공개 및 개인 키 쌍을 생성하는 데 완전히 새로운 객체를 생성한다 
- KeyFactory
  -  어떤 키 데이터 객체를 다른 타입의 키 데이터 객체로 전환하는데 사용한다

### JCA & JCE 구조
- Cipher
  - 암호화 및 복호화에 사용되는 암호화 암호의 기능을 제공한다. 
  - 암호화는 일반 텍스트와 키 를 가져와 암호화된 데이터를 생성하는 프로세스임. 
  - 복호화는 암호화된 데이터와 키를 가져와서 일반 텍스트를 생성하는 역 과정이다.
  - `Cipher 객체 인스턴스화하기`
    - 인스턴스 생성 시 변환을 지정하는데 변환은 [암호화 알고리즘/피드백 모드/패딩] or [암호화 알고리즘] 으로 지정한다
      - Cipher c1 = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
      - Cipher c1 = Cipher.getInstance("RSA")

  - `Cipher 초기화하기`
    - Cipher 객체를 초기화하기 위해서 Cipher의 작동 모드를 나타내는 opmode 와 Key 또는 증명서(Certificate) 를 인자로 전달하고 init() 메서드를 실행한다
    - opmode
      - ENCRYPT_MODE: cipher 객체를 암호화 모드로 초기화한다. 
      - DECRYPT_MODE: cipher 객체를 복호화 모드로 초기화한다. 
    - Cipher cipher = Cipher.getInstance(“RSA"); 
    - cipher.init(Cipher.ENCRYPT_MODE, PrivateKey);

<img width="861" alt="image" src="https://user-images.githubusercontent.com/40031858/208588948-ec67eb6d-80e7-4551-a330-94a8f8c48992.png">







