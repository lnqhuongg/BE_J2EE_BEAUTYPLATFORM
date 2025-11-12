package com.beautyplatform.beauty_service.Mapper;

import com.beautyplatform.beauty_service.DTO.LoaiHinhKinhDoanhDTO.LoaiHinhKinhDoanhDTO;
import com.beautyplatform.beauty_service.Model.LoaiHinhKinhDoanh;

public class LoaiHinhKinhDoanhMapper {
    public static LoaiHinhKinhDoanh toEntity(LoaiHinhKinhDoanhDTO dto) {
        return LoaiHinhKinhDoanh.builder()
                .maLH(dto.getMaLH())
                .tenLH(dto.getTenLH())
                .trangThai(dto.getTrangThai())
                .build();
    }

    public static LoaiHinhKinhDoanhDTO toDTO(LoaiHinhKinhDoanh loaiHinhKinhDoanh) {
        return LoaiHinhKinhDoanhDTO.builder()
                .maLH(loaiHinhKinhDoanh.getMaLH())
                .tenLH(loaiHinhKinhDoanh.getTenLH())
                .trangThai(loaiHinhKinhDoanh.getTrangThai())
                .build();
    }
}
