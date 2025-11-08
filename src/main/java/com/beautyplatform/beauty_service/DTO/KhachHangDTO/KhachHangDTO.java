package com.beautyplatform.beauty_service.DTO.KhachHangDTO;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class KhachHangDTO {
    private int maKH;
    private int maTK;
    private String hoTen;
    private int gioiTinh;
    private LocalDate ngaySinh;
    private String sdt;
    private String hinhAnh;
}
