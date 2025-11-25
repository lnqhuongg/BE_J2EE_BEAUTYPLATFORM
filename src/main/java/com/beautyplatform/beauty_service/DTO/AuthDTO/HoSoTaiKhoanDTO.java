package com.beautyplatform.beauty_service.DTO.AuthDTO;

import java.time.LocalDate;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class HoSoTaiKhoanDTO {
    private Integer maTK;
    private String email;
    private Integer loaiTK;
    private Integer trangThai;
    private String provider;

    // Thông tin bổ sung cho NCC
    private Integer maNCC;
    private String tenNCC;
    private Integer maLH;
    private String gioiThieu;
    private String diaChi;

    // Thông tin bổ sung cho KhachHang
    private Integer maKH;
    private String hoTen;
    private Integer gioiTinh;
    private LocalDate ngaySinh;
    private String sdt;
    private String hinhAnh;
}
