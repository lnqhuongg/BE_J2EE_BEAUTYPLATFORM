package com.beautyplatform.beauty_service.DTO.DichVuDTO;

import lombok.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class DichVuDTO {
    private Integer maDV;
    private int maLDV;
    private int maNCC;
    private Integer maKM;
    private String tenDV;
    private String moTa;
    private BigDecimal gia;
    private int thoiLuong;
    private int trangThai;
}
