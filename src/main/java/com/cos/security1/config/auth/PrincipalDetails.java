package com.cos.security1.config.auth;

import com.cos.security1.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/*
시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
로그인 진행이 완료되면 시큐리티 session을 만들어준다.(Security ContextHolder)
오브젝트 타입 -> Authentication 타입의 객체
Authentication 안에는 User 정보가 있어야 함. -> User 오브젝트 타입 => UserDetails 타입 객체

Security Session => Authentication => UserDetails(PrincipalDetails)
 */

public class PrincipalDetails implements UserDetails {

    private User user;  // composition

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 유저의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정 만료여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 비밀번호 오래되었는지 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }
}
