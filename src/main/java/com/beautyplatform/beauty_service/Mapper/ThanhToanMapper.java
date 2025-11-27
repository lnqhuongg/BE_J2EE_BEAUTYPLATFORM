package com.beautyplatform.beauty_service.Mapper;

import com.beautyplatform.beauty_service.DTO.ThanhToanDTO.ThanhToanDTO;
import com.beautyplatform.beauty_service.Model.DatLich;
import com.beautyplatform.beauty_service.Model.ThanhToan;

public class ThanhToanMapper {

    public static ThanhToan toEntity(ThanhToanDTO dto, DatLich datLich) {
        ThanhToan entity = new ThanhToan();
        entity.setMaTT(dto.getMaTT());
        entity.setDatLich(datLich);
        entity.setPhuongThuc(dto.getPhuongThuc());
        entity.setSoTien(dto.getSoTien());
        entity.setTrangThai(dto.getTrangThai());
        entity.setNgayThanhToan(dto.getNgayThanhToan());
        return entity;
    }

    public static ThanhToanDTO toDTO(ThanhToan entity) {
        return ThanhToanDTO.builder()
                .maTT(entity.getMaTT())
                .maDL(entity.getDatLich().getMaDL())
                .phuongThuc(entity.getPhuongThuc())
                .soTien(entity.getSoTien())
                .trangThai(entity.getTrangThai())
                .ngayThanhToan(entity.getNgayThanhToan())
                .build();
    }
}

