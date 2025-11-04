package com.beautyplatform.beauty_service.DTO.NhanVienDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimKiemNhanVienDTO {
    private Integer maNV;
    private Integer maNCC;
    private String hoTen;
    private Integer gioiTinh;
    private Integer trangThai;
}
