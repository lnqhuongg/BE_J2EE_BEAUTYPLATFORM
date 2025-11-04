package com.beautyplatform.beauty_service.DTO.NhanVienDTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NhanVienDTO {
    private int maNV;
    private int maNCC;
    private String hoTen;
    private String sdt;
    private int gioiTinh;
    private String hinhAnh;
    private int trangThai;
}
