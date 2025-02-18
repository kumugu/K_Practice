# 블로그 빨리 만들기



## 1. Spring Boot 프로젝트 생성

   - Spring Web, Security, JWT, JDBC API, MySQL
    
-----

## 2. DB 설정 (`application.properties`)
    
    ```sql
    
    # MySQL 설정
    spring.datasource.url=jdbc:mysql://localhost:3306/myblog_db?serverTimezone=
    UTC&characterEncoding=UTF-8
    spring.datasource.username=rmarn
    spring.datasource.password=1234
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    
    # DB Connection Pool 설정
    spring.datasource.hikari.maximum-pool-size=10
    spring.datasource.hikari.minimum-idle=2
    spring.datasource.hikari.idle-timeout=30000
    spring.datasource.hikari.connection-timeout=30000
    ```

-----

## 3. DB테이블 설계

   ```sql
   CREATE DATABASE myblog_db 
   CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

   USE myblog_db;

   -- users 테이블 생성(회원정보)
   CREATE TABLE USERS (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      username VARCHAR(50) NOT NULL UNIQUE,	
      password VARCHAR(255) NOT NULL,
      email VARCHAR(100) UNIQUE,
      role ENUM('ADMIN', 'USER') NOT NULL DEFAULT 'USER',
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      deleted_at TIMESTAMP NULL DEFAULT NULL
   );
      
   CREATE TABLE POSTS (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      title VARCHAR(255) NOT NULL,
      content TEXT NOT NULL,
      author_id BIGINT NOT NULL,	-- 작성자 ID (users 테이블과 연결)
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE
   );

   CREATE TABLE COMMENTS (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      post_id BIGINT NOT NULL,	-- 어느 게시글에 대한 댓글인지
      user_id BIGINT NOT NULL, 	-- 댓글 작성자
      content TEXT NOT NULL, 		-- 댓글 내용
      FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
      FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
   );
   ```

-----

## 4. **JWT 설정 및 인증 과정**

   1. **로그인**:
      - 사용자가 로그인하면 서버에서 사용자 정보를 기반으로 JWT 토큰을 생성.
      - 생성된 토큰은 클라이언트에 전달.
   2. **요청 시 토큰 포함**:
      - 클라이언트는 서버에 요청을 보낼 때마다 이 토큰을 HTTP 헤더에 포함.
   3. **토큰 확인**:
      - 서버는 요청을 받을 때마다 `JwtAuthenticationFilter`에서 토큰을 확인.
      - 유효한 경우, 토큰에서 사용자 정보를 추출하여 인증 정보를 `SecurityContext`에 저장.
   4. **권한 검사**:
      - `SecurityContext`에 저장된 인증 정보를 기반으로 Spring Security가 요청에 대해 권한을 검사.
      - 적절한 리소스에 접근할 수 있도록 처리.
   5. **토큰 만료**:
      - 토큰은 일정 시간이 지나면 만료.
      - 만료된 토큰은 더 이상 유효하지 않음.

   ```java
   package com.kumugu.blog.myblog.config;

   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;

   /**
   * JwtConfig 클래스는 JWT 유틸리티 빈을 구성하는 설정 클래스.
   * 이 클래스는 JwtTokenUtil 인스턴스를 스프링 컨텍스트에 빈으로 등록.
   */
   @Configuration
   public class JwtConfig {

      /**
      * jwtTokenUtil 메서드는 JwtTokenUtil 객체를 생성하여 반환.
      * @return JwtTokenUtil 인스턴스
      */
      public JwtTokenUtil jwtTokenUtil() {
         return new JwtTokenUtil();
      }
   }
   ```

   ```java
   package com.kumugu.blog.myblog.config;

   import io.jsonwebtoken.*;
   import io.jsonwebtoken.security.Keys;
   import org.springframework.stereotype.Component;
   import java.security.Key;
   import java.util.Date;

   /**
   * JwtTokenUtil 클래스는 JWT 토큰을 생성하고 검증하는 유틸리티 클래스.
   * 비밀 키와 만료 시간을 설정하여 JWT를 생성하고, 토큰의 유효성을 검증.
   */
   @Component
   public class JwtTokenUtil {

      private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512); // 비밀 키 설정
      private static final long EXPIRATION_TIME = 864_000_000L; // 10일

      /**
      * JWT 토큰 생성.
      * 사용자의 이름과 역할을 포함한 JWT 토큰을 생성.
      *
      * @param username 사용자 이름
      * @param role 사용자 역할
      * @return 생성된 JWT 토큰
      */
      public static String generateToken(String username, String role) {
         return Jwts.builder()
                  .setSubject(username)
                  .claim("role", role)
                  .setIssuedAt(new Date())
                  .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                  .signWith(SECRET_KEY)
                  .compact();
      }

      /**
      * JWT 토큰에서 사용자 이름 추출.
      * 토큰의 서명을 검증하고, 사용자 이름을 추출.
      *
      * @param token JWT 토큰
      * @return 사용자 이름
      */
      public static String getUsernameFromToken(String token) {
         return Jwts.parser()
                  .setSigningKey(SECRET_KEY)
                  .build()
                  .parseClaimsJws(token)
                  .getBody()
                  .getSubject();
      }

      /**
      * JWT 토큰에서 역할 추출.
      * 토큰의 서명을 검증하고, 사용자의 역할을 추출.
      *
      * @param token JWT 토큰
      * @return 사용자 역할
      */
      public static String getRoleFromToken(String token) {
         return (String) Jwts.parser()
                  .setSigningKey(SECRET_KEY)
                  .build()
                  .parseClaimsJws(token)
                  .getBody()
                  .get("role");
      }

      /**
      * JWT 토큰 검증.
      * 토큰의 사용자 이름과 만료 여부를 검증.
      *
      * @param token JWT 토큰
      * @param username 사용자 이름
      * @return 검증 결과 (true: 유효, false: 무효)
      */
      public static boolean validateToken(String token, String username) {
         String tokenUsername = getUsernameFromToken(token);
         return (tokenUsername.equals(username) && !isTokenExpired(token));
      }

      /**
      * 토큰 만료 여부 확인.
      * JWT 토큰이 만료되었는지 확인.
      *
      * @param token JWT 토큰
      * @return 만료 여부 (true: 만료됨, false: 만료되지 않음)
      */
      private static boolean isTokenExpired(String token) {
         Date expiration = Jwts.parser()
                  .setSigningKey(SECRET_KEY)
                  .build()
                  .parseClaimsJws(token)
                  .getBody()
                  .getExpiration();
         return expiration.before(new Date());
      }
   }

   ```

   ```java
   package com.kumugu.blog.myblog.config;

   import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
   import org.springframework.security.core.Authentication;
   import org.springframework.security.core.context.SecurityContextHolder;
   import org.springframework.web.filter.OncePerRequestFilter;

   import javax.servlet.FilterChain;
   import javax.servlet.ServletException;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   import java.io.IOException;
   import java.util.ArrayList;

   /**
   * JWT 인증 필터 클래스.
   * HTTP 요청마다 JWT 토큰을 확인하여 인증을 처리.
   */
   public class JwtAuthenticationFilter extends OncePerRequestFilter {

      private final JwtTokenUtil jwtTokenUtil;

      /**
      * JwtAuthenticationFilter 생성자.
      *
      * @param jwtTokenUtil JWT 유틸리티 클래스 인스턴스
      */
      public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
         this.jwtTokenUtil = jwtTokenUtil;
      }

      /**
      * JWT 토큰을 확인하고 인증을 처리.
      * HTTP 요청 헤더의 'Authorization' 필드에서 JWT 토큰을 추출하고,
      * 토큰이 유효한 경우 인증을 설정.
      *
      * @param request  HTTP 요청 객체
      * @param response HTTP 응답 객체
      * @param chain    필터 체인 객체
      * @throws ServletException
      * @throws IOException
      */
      @Override
      protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
               throws ServletException, IOException {
         // HTTP 요청 헤더에서 'Authorization' 필드 값 추출
         String token = request.getHeader("Authorization");

         // 토큰이 존재하고 'Bearer '로 시작하는지 확인
         if (token != null && token.startsWith("Bearer ")) {
               token = token.substring(7); // 'Bearer ' 제거
               String username = jwtTokenUtil.getUsernameFromToken(token); // JWT 토큰에서 사용자 이름 추출
               // SecurityContext에 인증 정보가 없는 경우 인증 처리
               if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                  // 사용자 이름을 기반으로 UsernamePasswordAuthenticationToken 생성
                  Authentication authentication = new UsernamePasswordAuthenticationToken(
                           username, null, new ArrayList<>());
                  // SecurityContext에 인증 정보 설정
                  SecurityContextHolder.getContext().setAuthentication(authentication);
               }
         }
         // 다음 필터로 요청과 응답 객체 전달
         chain.doFilter(request, response);
      }
   }

   ```

   ```java
   package com.kumugu.blog.myblog.config;

   import org.springframework.context.annotation.Configuration;
   import org.springframework.security.config.annotation.web.builders.HttpSecurity;
   import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
   import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
   import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

   /**
   * SecurityConfig 클래스는 Spring Security 설정을 구성.
   * JWT 인증 필터를 포함하여 보안 설정을 정의.
   */
   @Configuration
   @EnableWebSecurity
   public class SecurityConfig extends WebSecurityConfigurerAdapter {

      private final JwtTokenUtil jwtTokenUtil;
      private final JwtAuthenticationFilter jwtAuthenticationFilter;

      /**
      * SecurityConfig 생성자.
      *
      * @param jwtTokenUtil JWT 유틸리티 클래스 인스턴스
      */
      public SecurityConfig(JwtTokenUtil jwtTokenUtil) {
         this.jwtTokenUtil = jwtTokenUtil;
         this.jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenUtil);
      }

      /**
      * HttpSecurity 설정을 구성.
      * CSRF 비활성화, 특정 경로의 접근 권한 설정, JWT 인증 필터 추가.
      *
      * @param http HttpSecurity 객체
      * @throws Exception
      */
      @Override
      protected void configure(HttpSecurity http) throws Exception {
         http.csrf().disable() // CSRF 보호 비활성화
                  .authorizeRequests()
                  .antMatchers("/signup", "/login", "/logout").permitAll() // 로그인, 회원가입, 로그아웃은 모두 접근 허용
                  .antMatchers("/posts/**").authenticated() // /posts/** 경로는 인증된 사용자만 접근 허용
                  .anyRequest().authenticated() // 나머지 모든 요청은 인증된 사용자만 접근 허용
                  .and()
                  .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
      }
   }
   ```
   ```java
   package com.kumugu.blog.myblog.config;

   import org.springframework.context.annotation.Configuration;
   import org.springframework.web.servlet.config.annotation.CorsRegistry;
   import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

   @Configuration
   public class WebConfig implements WebMvcConfigurer {

      /**
      * CORS 설정
      * 특정 도메인에 대해 CORS를 허용.
      */
      @Override
      public void addCorsMappings(CorsRegistry registry) {
         registry.addMapping("/**") // 모든 경로에 대해 CORS 설정
                  .allowedOrigins("http://localhost:8081") // 허용할 출처 (여기서 localhost:8081은 Postman 혹은 클라이언트 앱)
                  .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
                  .allowedHeaders("*") // 모든 헤더 허용
                  .allowCredentials(true); // 자격 증명(쿠키 등) 전송 허용
      }
   }
   ```

-----

## 5. Entity 및 DTO 설정
   - **User Entity**
      - `User`는 데이터베이스와 매핑되는 클래스로, 실제 SQL 쿼리를 작성하여 CRUD 작업을 수동으로 처리.
      - 예를 들어, `User`는 `UserRequestDTO`를 통해 입력받고, `UserResponseDTO`를 통해 반환할 데이터를 처리함함.

   ```java
   public class User {
      private Long id;
      private String username;
      private String password;
      private String email;
      private String role;
      private Timestamp createdAt;
      private Timestamp updatedAt;

      // Getter, Setter
      }
   ```
   - **UserDTO (Request/Response)**
      - `DTO`는 클라이언트와 서버 간의 데이터 전송을 위해 사용하는 객체. 
      - `UserRequestDTO`는 사용자 가입 시 필요한 정보를 받고, `UserResponseDTO`는 클라이언트에게 반환할 데이터를 포함.
   ```java
   public class UserRequestDTO {
      private String username;
      private String password;
      private String email;

      // Getter, Setter
   }
   ```
   ```java
   public class UserResponseDTO {
      private long id;
      private String username;
      private String email;
      private String role;
      private Timestamp created;

      // Getter, Setter
   }
   ```

-----

## 6. Repository 작성
   - `UserRepository` 클래스는 애플리케이션 내에서 사용자 정보를 데이터베이스에 저장, 조회, 업데이트 및 삭제하는 역할을 합니다. 이 클래스는 주로 데이터베이스와의 상호작용을 처리하며, 사용자의 정보 관리를 담당합니다. 다음은 각 메서드의 설명입니다:

   1. **save(UserRequestDTO userRequestDTO)**:
      - 사용자 정보를 데이터베이스에 저장합니다.
      - 사용자가 입력한 비밀번호를 `BCryptPasswordEncoder`를 사용하여 암호화한 후 저장합니다.
      - 기본 역할(Role)은 "USER"로 설정됩니다.
   2. **findByUsername(String username)**:
      - 사용자 이름을 기반으로 데이터베이스에서 사용자 정보를 조회합니다.
      - 조회된 정보는 `UserRequestDTO` 객체로 반환됩니다.
   3. **update(UserRequestDTO userRequestDTO)**:
      - 사용자 이름을 기반으로 비밀번호와 이메일 정보를 업데이트합니다.
      - 업데이트할 사용자 정보는 `UserRequestDTO` 객체에 담겨 전달됩니다.
   4. **delete(String username)**:
      - 사용자 이름을 기반으로 데이터베이스에서 사용자 정보를 삭제합니다.

   `UserRepository` 클래스는 `JdbcTemplate`을 사용하여 데이터베이스와 상호작용하며, SQL 쿼리를 사용해 CRUD 작업을 수행합니다.

   ```java
   package com.kumugu.blog.myblog.repository;

   import com.kumugu.blog.myblog.dto.UserRequestDTO;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.jdbc.core.BeanPropertyRowMapper;
   import org.springframework.jdbc.core.JdbcTemplate;
   import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
   import org.springframework.stereotype.Repository;

   /**
    * UserRepository 클래스는 사용자 정보를 데이터베이스에 저장, 조회, 업데이트 및 삭제하는 기능을 제공.
    */
   @Repository
   public class UserRepository {

      private final JdbcTemplate jdbcTemplate;

      /**
       * UserRepository 생성자.
       *
       * @param jdbcTemplate JdbcTemplate 인스턴스
       */
      @Autowired
      public UserRepository(JdbcTemplate jdbcTemplate) {
         this.jdbcTemplate = jdbcTemplate;
      }

      /**
       * 사용자 추가.
       * 사용자 정보를 받아 데이터베이스에 저장.
       * 비밀번호는 BCryptPasswordEncoder를 사용하여 암호화.
       * 기본 역할은 USER로 설정.
       *
       * @param userRequestDTO 사용자 요청 정보를 담은 DTO
       */
      public void save(UserRequestDTO userRequestDTO) {
         String sql = "INSERT INTO user (username, password, email, role) VALUES (?, ?, ?, ?)";
         jdbcTemplate.update(sql, userRequestDTO.getUsername(),
                  new BCryptPasswordEncoder().encode(userRequestDTO.getPassword()),
                  userRequestDTO.getEmail(),
                  "USER");    // 기본 역할 USER로 설정
      }

      /**
       * 사용자 조회.
       * 사용자 이름을 기반으로 사용자 정보를 데이터베이스에서 조회.
       *
       * @param username 조회할 사용자 이름
       * @return 조회된 사용자 정보가 담긴 DTO
       */
      public UserRequestDTO findByUsername(String username) {
         String sql = "SELECT * FROM user WHERE username = ?";
         return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserRequestDTO.class), username);
      }

      /**
       * 사용자 정보 업데이트.
       * 사용자 이름을 기반으로 비밀번호와 이메일을 업데이트.
       *
       * @param userRequestDTO 업데이트할 사용자 정보를 담은 DTO
       */
      public void update(UserRequestDTO userRequestDTO) {
         String sql = "UPDATE user SET password = ?, email = ? WHERE username = ?";
         jdbcTemplate.update(sql, userRequestDTO.getPassword(), userRequestDTO.getEmail(), userRequestDTO.getUsername());
      }

      /**
       * 사용자 삭제.
       * 사용자 이름을 기반으로 사용자 정보를 데이터베이스에서 삭제.
       *
       * @param username 삭제할 사용자 이름
       */
      public void delete(String username) {
         String sql = "DELETE FROM user WHERE username = ?";
         jdbcTemplate.update(sql, username);
      }
   }

   ```

-----

## 7. Service
UserService는 UserRepository를 호출하여 직접 DB 작업을 처리합니다. 이때 DTO와 Entity 간의 변환은 수동으로 처리해야 합니다.
`UserService` 클래스는 애플리케이션의 비즈니스 로직을 처리하는 서비스 계층입니다. 이 클래스는 사용자 회원가입과 로그인 기능을 담당합니다. `UserRepository`를 통해 데이터베이스와 상호작용하며, 사용자 정보를 저장하고 조회합니다.
   ```java
   package com.kumugu.blog.myblog.service;

   import com.kumugu.blog.myblog.dto.UserRequestDTO;
   import com.kumugu.blog.myblog.repository.UserRepository;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;

   /**
   * UserService 클래스는 비즈니스 로직을 담당.
   * 회원가입 및 로그인을 처리.
   */
   @Service
   public class UserService {

      private final UserRepository userRepository;

      /**
      * UserService 생성자.
      *
      * @param userRepository UserRepository 인스턴스
      */
      @Autowired
      public UserService(UserRepository userRepository) {
         this.userRepository = userRepository;
      }

      /**
      * 회원가입.
      * 사용자 정보를 받아 저장.
      *
      * @param userRequestDTO 사용자 요청 정보를 담은 DTO
      */
      public void signup(UserRequestDTO userRequestDTO) {
         userRepository.save(userRequestDTO);
      }

      /**
      * 로그인.
      * 사용자 이름을 기반으로 사용자 정보 조회.
      *
      * @param username 사용자 이름
      * @return 조회된 사용자 정보가 담긴 DTO
      */
      public UserRequestDTO login(String username) {
         return userRepository.findByUsername(username);
      }
   }
   ```


## 8.  Controller
UserController에서 UserService를 호출하여 요청을 처리하고 응답을 반환합니다.

`UserController` 클래스는 사용자 관련 API 엔드포인트를 처리하는 컨트롤러 계층입니다. 이 클래스는 회원가입과 로그인 요청을 처리하며, 이를 위해 `UserService`를 호출합니다.

1. **signUp(@RequestBody UserRequestDTO userRequestDTO)**:
   - 회원가입 엔드포인트입니다.
   - 클라이언트로부터 `UserRequestDTO` 객체를 받아 `UserService`의 `signup` 메서드를 호출하여 사용자 정보를 저장합니다.
   - 회원가입 성공 메시지를 반환합니다.
2. **login(@RequestBody String username)**:
   - 로그인 엔드포인트입니다.
   - 클라이언트로부터 사용자 이름을 받아 `UserService`의 `login` 메서드를 호출하여 사용자 정보를 조회합니다.
   - 조회된 사용자 정보를 반환합니다.

   ```java
   package com.kumugu.blog.myblog.controller;

   import com.kumugu.blog.myblog.dto.UserRequestDTO;
   import com.kumugu.blog.myblog.service.UserService;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.http.HttpStatus;
   import org.springframework.http.ResponseEntity;
   import org.springframework.web.bind.annotation.PostMapping;
   import org.springframework.web.bind.annotation.RequestBody;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.RestController;

   /**
    * UserController 클래스는 사용자 관련 API 엔드포인트를 처리.
    * 회원가입 및 로그인 요청을 처리하고, 해당 서비스 로직을 호출.
    */
   @RestController
   @RequestMapping("/users")
   public class UserController {

      private final UserService userService;

      /**
       * UserController 생성자.
       *
       * @param userService UserService 인스턴스
       */
      @Autowired
      public UserController(UserService userService, UserService userService1) {
         this.userService = userService1;
      }

      /**
       * 회원가입 엔드포인트.
       * 사용자 요청 정보를 받아 회원가입 처리.
       *
       * @param userRequestDTO 사용자 요청 정보를 담은 DTO
       * @return 회원가입 성공 메시지를 담은 ResponseEntity
       */
      @PostMapping("/signup")
      public ResponseEntity<String> signUp(@RequestBody UserRequestDTO userRequestDTO) {
         userService.signup(userRequestDTO);
         return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공!");
      }

      /**
       * 로그인 엔드포인트.
       * 사용자 이름을 받아 로그인 처리.
       *
       * @param username 사용자 이름
       * @return 사용자 정보를 담은 ResponseEntity
       */
      @PostMapping("/login")
      public ResponseEntity<UserRequestDTO> login(@RequestBody String username) {
         UserRequestDTO user = userService.login(username);
         return ResponseEntity.ok(user);
      }
   }
   ```


## 9. API 테스트
### 1. **Postman 설정**

Postman을 사용하여 요청을 보내는 방법은 다음과 같습니다.

#### 1.1. **회원가입 요청**

1. **URL**: `http://localhost:8080/users/signup`

2. **HTTP 메서드**: `POST`

3. **Body**:

   - JSON 형식으로 회원가입에 필요한 정보를 전달합니다.

   ```
   json복사편집{
       "username": "testuser",
       "password": "password123",
       "email": "testuser@example.com"
   }
   ```

4. **헤더**:

   - `Content-Type`: `application/json` (JSON 형식으로 데이터를 보내기 때문에 헤더에 반드시 포함시켜야 합니다.)

#### 1.2. **로그인 요청**

로그인 시에는 사용자 이름을 요청 본문으로 보내서 인증을 확인할 수 있습니다.

1. **URL**: `http://localhost:8080/users/login`

2. **HTTP 메서드**: `POST`

3. Body

   :

   ```json
   {
       "username": "testuser"
   }
   ```


   
