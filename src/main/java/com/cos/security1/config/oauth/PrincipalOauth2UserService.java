package com.cos.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    // 구글에서 받은 userRequest 데이터에 대한 후처리 되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration : " + userRequest.getClientRegistration());
        System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());
        System.out.println("getAccessToken : " + userRequest.getAccessToken());
        // 구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 -> code 리턴(OAuth-Client 라이브러리) -> AccessToken 요청
        // 여기까지가 userRequest 정보 -> 구글로부터 회원 프로필을 받아야 함.(loadUser 함수)
        System.out.println("getAttributes : " + super.loadUser(userRequest).getAttributes());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        return super.loadUser(userRequest);
    }
}
