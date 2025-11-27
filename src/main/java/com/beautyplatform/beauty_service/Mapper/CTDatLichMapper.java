package com.beautyplatform.beauty_service.Mapper;

import com.beautyplatform.beauty_service.DTO.DatLichDTO.CTDatLichDTO;
import com.beautyplatform.beauty_service.Model.CTDatLich;
import com.beautyplatform.beauty_service.Model.DatLich;
import com.beautyplatform.beauty_service.Model.DichVu;
import com.beautyplatform.beauty_service.Model.NhanVien;

public class CTDatLichMapper {

    public static CTDatLich toEntity(CTDatLichDTO dto, DatLich dl, DichVu dv, NhanVien nv) {
        CTDatLich entity = new CTDatLich();
        entity.setMaCTDL(dto.getMaCTDL());
        entity.setDatLich(dl);
        entity.setDichVu(dv);
        entity.setNhanVien(nv);
        entity.setThoiGianBatDau(dto.getThoiGianBatDau());
        entity.setThoiGianKetThuc(dto.getThoiGianKetThuc());
        entity.setGia(dto.getGia());

        return entity;
    }

    public static CTDatLichDTO toDTO(CTDatLich e) {
        return CTDatLichDTO.builder()
                .maCTDL(e.getMaCTDL())
                .maDL(e.getDatLich().getMaDL())
                .maDV(e.getDichVu().getMaDV())
                .maNV(e.getNhanVien().getMaNV())
                .thoiGianBatDau(e.getThoiGianBatDau())
                .thoiGianKetThuc(e.getThoiGianKetThuc())
                .gia(e.getGia())
                .build();
    }
}


