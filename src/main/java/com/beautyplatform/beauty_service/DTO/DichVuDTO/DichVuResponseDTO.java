package com.beautyplatform.beauty_service.DTO.DichVuDTO;

import com.beautyplatform.beauty_service.DTO.KhuyenMaiDTO.KhuyenMaiDTO;
import com.beautyplatform.beauty_service.DTO.LoaiDichVuDTO.LoaiDichVuDTO;
import com.beautyplatform.beauty_service.DTO.NhaCungCapDTO.NhaCungCapDTO;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DichVuResponseDTO {
    private int maDV;

    private LoaiDichVuDTO loaiDichVu;
    private NhaCungCapDTO nhaCungCap;
    private KhuyenMaiDTO khuyenMai;

    private String tenDV;
    private String moTa;
    private BigDecimal gia;
    private int thoiLuong;
    private int trangThai;
}
