package com.beautyplatform.beauty_service.Mapper;

import com.beautyplatform.beauty_service.DTO.DichVuDTO.DichVuDTO;
import com.beautyplatform.beauty_service.Model.DichVu;
import com.beautyplatform.beauty_service.Model.LoaiDichVu;
import com.beautyplatform.beauty_service.Model.NhaCungCap;

public class DichVuMapper {
    public static DichVu toEntity(DichVuDTO dto, LoaiDichVu ldv, NhaCungCap ncc) {
        DichVu entity = new DichVu();
        entity.setMaDV(dto.getMaDV());
        entity.setTenDV(dto.getTenDV());
        entity.setMoTa(dto.getMoTa());
        entity.setGia(dto.getGia());
        entity.setThoiLuong(dto.getThoiLuong());
        entity.setTrangThai(dto.getTrangThai());
        entity.setLoaiDichVu(ldv);
        entity.setNhaCungCap(ncc);
        return entity;
    }

    public static DichVuDTO toDTO(DichVu dichVu) {
        return DichVuDTO.builder()
                .maDV(dichVu.getMaDV())
                .maLDV(dichVu.getLoaiDichVu().getMaLDV())
                .maNCC(dichVu.getNhaCungCap().getMaNCC())
                .tenDV(dichVu.getTenDV())
                .moTa(dichVu.getMoTa())
                .gia(dichVu.getGia())
                .thoiLuong(dichVu.getThoiLuong())
                .trangThai(dichVu.getTrangThai())
                .build();
    }
}
