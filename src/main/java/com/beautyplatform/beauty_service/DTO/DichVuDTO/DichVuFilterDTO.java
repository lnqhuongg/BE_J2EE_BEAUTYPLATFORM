package com.beautyplatform.beauty_service.DTO.DichVuDTO;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DichVuFilterDTO {
    private Integer maLoaiDichVu;
    private Integer maNhaCungCap;
    private Integer maKhuyenMai;
    private String tenDV;
    private BigDecimal minGia;
    private BigDecimal maxGia;
    private Integer trangThai;
}
