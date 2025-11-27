package com.beautyplatform.beauty_service.DTO.DatLichDTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DatLichDTO {
    private int maDL;
    private int maKH;
    private int tongThoiGian;
    private BigDecimal tongTien;
    private int trangThai;
    private LocalDate ngayTao;
}
