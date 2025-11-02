package com.beautyplatform.beauty_service.DTO.KhuyenMaiDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class KhuyenMaiDTO {
    private int maKM;
    private int maNCC;
    private String moTa;
    private BigDecimal phanTram;
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate ngayBatDau;
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate ngayKetThuc;
    private int trangThai;
}
