package com.beautyplatform.beauty_service.Mapper;

import com.beautyplatform.beauty_service.DTO.LoaiDichVuDTO.LoaiDichVuDTO;
import com.beautyplatform.beauty_service.Model.LoaiDichVu;

public class LoaiDichVuMapper {

    // Entity → DTO
    public static LoaiDichVuDTO toDTO(LoaiDichVu entity) {
        if (entity == null) return null;

        return LoaiDichVuDTO.builder()
                .maLDV(entity.getMaLDV())
                .tenLDV(entity.getTenLDV())
                .trangThai(entity.getTrangThai())
                .build();
    }

    // DTO → Entity
    public static LoaiDichVu toEntity(LoaiDichVuDTO dto) {
        if (dto == null) return null;

        return LoaiDichVu.builder()
                .maLDV(dto.getMaLDV())
                .tenLDV(dto.getTenLDV())
                .trangThai(dto.getTrangThai())
                .build();
    }
}
