package com.beautyplatform.beauty_service.DTO.AuthDTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AuthResponseDTO {
    private String token;
    private String tokenType = "Bearer";
    private int maTK;
    private String email;
    private int loaiTK;
    private String provider;

    // Thông tin NCC nếu là nhà cung cấp
    private Integer maNCC;
    private String tenNCC;

    // Thông tin KH nếu là khách hàng
    private Integer maKH;
    private String hoTen;
}