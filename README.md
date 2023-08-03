# yalooStore-front

## [Project architecture]
![image](https://github.com/yalooStore/yalooStore-front/assets/81970382/4065dbdb-791d-44b3-a088-921afe50ebdd)

## Features
### 회원 관리
- 회원 가입
- 회원 수정
- 회원 삭제
- 회원 로그인
- 회원 로그아웃

### 인증, 인가
- 인증/인가 서버에게 발급 받은 JWT 토큰 재발급 요청
- RestTemplate 사용 시 Header에 회원 인증정보를 추가(Authorization: Bearer JWT AccessToken)
- 권한별 접근 페이지 구분

### 장바구니
- 레디스, 쿠키를 사용
- 장바구니 저장
  - 회원, 비회원을 구분하여 장바구니 저장 
- 장바구니 수정
- 장바구니 삭제
- 장바구니 내의 상품 만료기간 설정으로 자동 삭제
  - 회원, 비회원의 장바구니 만료 시간을 다르게 설정 

### 상품
- 전체 상품 출력
- 타입별 상품 출력
- 상품 검색
- 최근 본 상품
- 최근 본 상품 삭제

## Tech Stack
### Languages
![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=CSS3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=JavaScript&logoColor=white)

### Frameworks
![SpringBoot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=SpringBoot&logoColor=white)
![Spring Security](https://img.shields.io/static/v1?style=flat-square&message=Spring+Security&color=6DB33F&logo=Spring+Security&logoColor=FFFFFF&label=)

### Template Engine
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=flat&logo=Thymeleaf&logoColor=white)

### Database
![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=Redis&logoColor=white)

### Build Tool
![ApacheMaven](https://img.shields.io/badge/Maven-C71A36?style=flat&logo=ApacheMaven&logoColor=white)

### 형상 관리 전략
![Git](https://img.shields.io/badge/Git-F05032?style=flat&logo=Git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white)


