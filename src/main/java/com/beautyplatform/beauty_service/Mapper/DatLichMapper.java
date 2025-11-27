package com.beautyplatform.beauty_service.Mapper;

import com.beautyplatform.beauty_service.DTO.DatLichDTO.DatLichDTO;
import com.beautyplatform.beauty_service.Model.DatLich;
import com.beautyplatform.beauty_service.Model.KhachHang;

public class DatLichMapper {

    public static DatLich toEntity(DatLichDTO dto, KhachHang kh) {
        DatLich entity = new DatLich();
        entity.setMaDL(dto.getMaDL());
        entity.setKhachHang(kh);
        entity.setTongThoiGian(dto.getTongThoiGian());
        entity.setTongTien(dto.getTongTien());
        entity.setTrangThai(dto.getTrangThai());
        entity.setNgayTao(dto.getNgayTao());
        return entity;
    }

    public static DatLichDTO toDTO(DatLich entity) {
        return DatLichDTO.builder()
                .maDL(entity.getMaDL())
                .maKH(entity.getKhachHang().getMaKH())
                .tongThoiGian(entity.getTongThoiGian())
                .tongTien(entity.getTongTien())
                .trangThai(entity.getTrangThai())
                .ngayTao(entity.getNgayTao())
                .build();
    }
}

