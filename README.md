# yalooStore-front

# front Server에서 하고 있는 일들
- 클라이언트의 요청을 받아서 API 서버에 통신해서 해당 요청을 처리하는 역할을 한다.
- 해당 서버에 로그인, 로그아웃과 같이 인증 처리 요청이 들어오면 해당 요청을 auth server로 넘겨 진행한다.
  - 이때 auth 서버에서는 해당 회원의 정보를 불러오기 위해서 API 서버와 통신한다.
  - 이때 front server내에서 로그인 관련 처리는 로그인 폼을 이용해 진행하기 때문에 usernamePasswordAuthenticationFilter를 사용해서 로직 처리를 한다.
  - 이때 auth 서버에서 넘겨받은 JWT 토큰 정보를 이용해서 로그인 유지를 하는데 이는 redis를 통해서 관리한다.
- 화면 구성은 SSR(서버사이드렌더링)으로 진행하고 이때 사용하는 템플릿은 thymeleaf를 사용한다.
- 장바구니에 저장한 상품을 지속하기 위해서 redis를 사용한다.


#
