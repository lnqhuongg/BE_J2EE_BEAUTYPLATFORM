package com.beautyplatform.beauty_service.Service.Interface;

import com.beautyplatform.beauty_service.DTO.AuthDTO.*;

import java.util.Optional;

public interface IAuthService {
    // Đăng ký qua form
    Optional<AuthResponseDTO> dangKy(DangKyDTO dangKyDTO);

    // Đăng nhập qua form
    Optional<AuthResponseDTO> dangNhap(DangNhapDTO dangNhapDTO);

    // Đăng nhập/đăng ký qua OAuth2
    Optional<AuthResponseDTO> oauth2Login(String email,
                                          int loaiTK,
                                          String provider,
                                          String providerId,
                                          String fullName,
                                          String avatarUrl);

    // Quên mật khẩu
    Optional<String> quenMatKhau(QuenMatKhauDTO dto);

    // Đổi mật khẩu
    Optional<String> doiMatKhau(int maTK, DoiMatKhauDTO dto);

    // Validate token
    boolean validateToken(String token);
    // Lấy hồ sơ tài khoản
    Optional<HoSoTaiKhoanDTO> getHoSoTaiKhoan(Integer maTK);
}