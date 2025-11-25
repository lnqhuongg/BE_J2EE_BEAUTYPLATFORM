package com.beautyplatform.beauty_service.Security.oauth2;

import com.beautyplatform.beauty_service.DTO.AuthDTO.AuthResponseDTO;
import com.beautyplatform.beauty_service.Service.Interface.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private IAuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String provider = token.getAuthorizedClientRegistrationId(); // google | facebook

        String email = oauth2User.getAttribute("email");
        String providerId = provider.equals("google") ? oauth2User.getAttribute("sub") : oauth2User.getAttribute("id");

        Optional<AuthResponseDTO> result = authService.oauth2Login(email, provider, providerId);

        if (result.isEmpty()) {
            response.sendRedirect("http://127.0.0.1:5500/oauth2/error");
            return;
        }

        String jwt = result.get().getToken();

        // Redirect về front-end (Live Server 5500)
        response.sendRedirect("http://127.0.0.1:5500/client/pages/DangNhap.html?token=" + jwt);
    }
}
