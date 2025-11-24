package com.beautyplatform.beauty_service.DTO.NhaCungCapDTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NhaCungCapResponseDTO {
    // Thông tin Nhà Cung Cấp
    private int maNCC;
    private String tenNCC;
    private String gioiThieu;
    private String diaChi;

    // Thông tin Tài Khoản
    private int maTK;
    private String email;
    private int trangThai; // 0=Ngừng, 1=Hoạt động, 2=Khóa

    // Thông tin Loại Hình
    private int maLH;
    private String tenLH; // Tên loại hình (Salon, Spa, Nail...)
}