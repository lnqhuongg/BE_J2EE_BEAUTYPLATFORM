package com.beautyplatform.beauty_service.Mapper;

import com.beautyplatform.beauty_service.DTO.YeuThichDTO.CreateYeuThichCommandDTO;
import com.beautyplatform.beauty_service.DTO.YeuThichDTO.CreateYeuThichResponseDTO;
import com.beautyplatform.beauty_service.Model.KhachHang;
import com.beautyplatform.beauty_service.Model.NhaCungCap;
import com.beautyplatform.beauty_service.Model.YeuThich;

public class YeuThichMapper {
    public static YeuThich toEntity(CreateYeuThichCommandDTO dto,
                                    KhachHang khachHang,
                                    NhaCungCap nhaCungCap){
        YeuThich entity = new YeuThich();

        entity.setKhachHang(khachHang);
        entity.setNhaCungCap(nhaCungCap);

        return entity;
    }

    public static CreateYeuThichResponseDTO toDTO(YeuThich yeuThich) {
        return CreateYeuThichResponseDTO.builder()
                .maYT(yeuThich.getMaYT())
                .khachHang(yeuThich.getKhachHang())
                .nhaCungCap(yeuThich.getNhaCungCap())
                .build();
    }
}
