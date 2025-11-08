package com.beautyplatform.beauty_service.DTO.KhachHangDTO;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TimKiemKhachHangDTO {
//    private int maKH;
//    private int maTK;
    private Integer maKH;   // cho phép null
    private Integer maTK;   // cho phép null tìm gần đúng
    private String hoTen;
    private String sdt;
}
