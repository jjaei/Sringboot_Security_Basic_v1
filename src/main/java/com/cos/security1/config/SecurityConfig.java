package com.cos.security1.config;

import com.cos.security1.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// secured 애노테이션 활성화, preAuthorize 애노테이션 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principleOauth2UserService;
    private final CustomBCryptPasswordEncoder customBCryptPasswordEncoder;

    /*
    @Bean // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해줌.
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }
    */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.authorizeHttpRequests()
                .requestMatchers("/user/**").authenticated()
                .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm") // 로그인 페이지로 이동
                .loginProcessingUrl("/login") // /login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 진행함.
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm")  // 구글 로그인이 완료된 뒤의 후처리가 필요함.
                // 1. 코드 받기(인증) 2. 액세스 토큰(권한) 3. 사용자 프로필 정보 받기 4. 회원가입 자동 진행
                //
                .userInfoEndpoint()
                .userService(principleOauth2UserService);
        return http.build();

    }

    /*
    지금은 WebSecurityConfigurerAdapter가 deprecated 되어 사용할 수 없다.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .andMatchers("/user/**").authenticated()
                .andMatchers("/manager/**").access("hasRole('ROLE_ADMIN) or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and
                .formLogin()
                .loginPage("/login") // 로그인 페이지로 이동

    }

     */
}
