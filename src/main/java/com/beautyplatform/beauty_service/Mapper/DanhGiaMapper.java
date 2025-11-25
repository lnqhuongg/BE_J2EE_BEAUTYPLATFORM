package com.beautyplatform.beauty_service.Mapper;

import com.beautyplatform.beauty_service.DTO.DanhGiaDTO.CreateDanhGiaCommandDTO;
import com.beautyplatform.beauty_service.DTO.DanhGiaDTO.CreateDanhGiaResponseDTO;
import com.beautyplatform.beauty_service.DTO.DanhGiaDTO.DanhGiaDTO;
import com.beautyplatform.beauty_service.Model.DanhGia;
import com.beautyplatform.beauty_service.Model.DatLich;
import com.beautyplatform.beauty_service.Model.KhachHang;

import java.time.LocalDate;

public class DanhGiaMapper {
    public static DanhGia toEntity(CreateDanhGiaCommandDTO dto,
                                   DatLich datLich) {
        DanhGia entity = new DanhGia();

        entity.setDatLich(datLich);
        entity.setDiemDanhGia(dto.getDiemDanhGia());
        entity.setNoiDung(dto.getNoiDung());
        entity.setNgayDanhGia(LocalDate.now());

        return entity;
    }

    public static CreateDanhGiaResponseDTO toDTO(DanhGia danhGia){
        return CreateDanhGiaResponseDTO.builder()
                .maDG(danhGia.getMaDG())
                .datLich(danhGia.getDatLich())
                .diemDanhGia(danhGia.getDiemDanhGia())
                .noiDung(danhGia.getNoiDung())
                .ngayDanhGia(danhGia.getNgayDanhGia())
                .build();
    }

}
