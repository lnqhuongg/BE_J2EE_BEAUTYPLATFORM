package com.beautyplatform.beauty_service.DTO.AuthDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DangKyDTO {

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String matKhau;

    @NotBlank(message = "Xác nhận mật khẩu không được để trống")
    private String xacNhanMatKhau;

    private int loaiTK; // 2=NCC, 3=KhachHang (không cho đăng ký Admin)

    // Thông tin bổ sung cho NCC
    private String tenNCC;
    private Integer maLH;
    private String gioiThieu;
    private String diaChi;

    // Thông tin bổ sung cho KhachHang
    private String hoTen;
    private Integer gioiTinh;
    private String ngaySinh;
    private String sdt;
}