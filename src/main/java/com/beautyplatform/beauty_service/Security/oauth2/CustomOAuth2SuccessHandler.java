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
import java.util.Map;
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
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        String provider = oauthToken.getAuthorizedClientRegistrationId(); // google | facebook
        String email = oauth2User.getAttribute("email");

        // Provider ID
        String providerId = provider.equals("google")
                ? oauth2User.getAttribute("sub")
                : oauth2User.getAttribute("id");

        // Loại tài khoản (2 = NCC, 3 = KH)
        String loaiTKParam = request.getParameter("loaiTK");
        int loaiTK = (loaiTKParam != null) ? Integer.parseInt(loaiTKParam) : 3;

        // ⭐ Lấy họ tên
        String fullName = oauth2User.getAttribute("name");

        // ⭐ Lấy avatar (Google & Facebook)
        String avatar = null;

        if (provider.equals("google")) {
            avatar = oauth2User.getAttribute("picture");
        } else if (provider.equals("facebook")) {
            try {
                Map<String, Object> picture = oauth2User.getAttribute("picture");
                if (picture != null) {
                    Map<String, Object> data = (Map<String, Object>) picture.get("data");
                    if (data != null) {
                        avatar = (String) data.get("url");
                    }
                }
            } catch (Exception ignored) {}
        }

        Optional<AuthResponseDTO> result = authService.oauth2Login(
                email, loaiTK, provider, providerId, fullName, avatar
        );

        if (result.isEmpty()) {
            response.sendRedirect("http://127.0.0.1:5500/oauth2/error");
            return;
        }

        String jwt = result.get().getToken();

        response.sendRedirect("http://127.0.0.1:5500/client/pages/Auth.html?token=" + jwt);
    }
}

