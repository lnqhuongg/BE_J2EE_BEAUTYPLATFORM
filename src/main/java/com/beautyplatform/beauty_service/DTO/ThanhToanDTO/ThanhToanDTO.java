package com.beautyplatform.beauty_service.DTO.ThanhToanDTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ThanhToanDTO {
    private int maTT;
    private int maDL;
    private int phuongThuc;
    private BigDecimal soTien;
    private int trangThai;
    private LocalDateTime ngayThanhToan;
}
