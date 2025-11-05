package com.beautyplatform.beauty_service.Mapper;

import com.beautyplatform.beauty_service.DTO.NhaCungCapDTO.NhaCungCapGioLamViecDTO;
import com.beautyplatform.beauty_service.Model.NhaCungCap;
import com.beautyplatform.beauty_service.Model.NhaCungCapGioLamViec;

public class NhaCungCapGioLamViecMapper {

    public static NhaCungCapGioLamViecDTO toDTO(NhaCungCapGioLamViec entity) {
        if (entity == null) return null;

        return NhaCungCapGioLamViecDTO.builder()
                .maGioLamViec(entity.getMaGioLamViec())
                .maNCC(entity.getNhaCungCap().getMaNCC())
                .ngayTrongTuan(entity.getNgayTrongTuan())
                .gioMoCua(entity.getGioMoCua())
                .gioDongCua(entity.getGioDongCua())
                .build();
    }

    public static NhaCungCapGioLamViec toEntity(NhaCungCapGioLamViecDTO dto, NhaCungCap nhaCungCap) {
        if (dto == null) return null;

        return NhaCungCapGioLamViec.builder()
                .maGioLamViec(dto.getMaGioLamViec())
                .nhaCungCap(nhaCungCap)
                .ngayTrongTuan(dto.getNgayTrongTuan())
                .gioMoCua(dto.getGioMoCua())
                .gioDongCua(dto.getGioDongCua())
                .build();
    }
}