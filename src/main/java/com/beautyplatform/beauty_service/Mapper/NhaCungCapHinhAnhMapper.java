package com.beautyplatform.beauty_service.Mapper;

import com.beautyplatform.beauty_service.DTO.NhaCungCapDTO.NhaCungCapHinhAnhDTO;
import com.beautyplatform.beauty_service.Model.NhaCungCap;
import com.beautyplatform.beauty_service.Model.NhaCungCapHinhAnh;

public class NhaCungCapHinhAnhMapper {

    public static NhaCungCapHinhAnhDTO toDTO(NhaCungCapHinhAnh entity) {
        if (entity == null) return null;

        return NhaCungCapHinhAnhDTO.builder()
                .maHinhAnh(entity.getMaHinhAnh())
                .maNCC(entity.getNhaCungCap().getMaNCC())
                .imageUrl(entity.getImageUrl())
                .imageMain(entity.getImageMain())
                .build();
    }

    public static NhaCungCapHinhAnh toEntity(NhaCungCapHinhAnhDTO dto, NhaCungCap nhaCungCap) {
        if (dto == null) return null;

        return NhaCungCapHinhAnh.builder()
                .maHinhAnh(dto.getMaHinhAnh())
                .nhaCungCap(nhaCungCap)
                .imageUrl(dto.getImageUrl())
                .imageMain(dto.getImageMain())
                .build();
    }
}