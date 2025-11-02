package com.beautyplatform.beauty_service.Mapper;

import com.beautyplatform.beauty_service.DTO.KhuyenMaiDTO.KhuyenMaiDTO;
import com.beautyplatform.beauty_service.Model.KhuyenMai;
import com.beautyplatform.beauty_service.Model.NhaCungCap;

public class KhuyenMaiMapper {
    public static KhuyenMai toEntity(KhuyenMaiDTO dto, NhaCungCap ncc){
        KhuyenMai entity = new KhuyenMai();
        entity.setMaKM(dto.getMaKM());
        entity.setMoTa(dto.getMoTa());
        entity.setPhanTram(dto.getPhanTram());
        entity.setNgayBatDau(dto.getNgayBatDau());
        entity.setNgayKetThuc(dto.getNgayKetThuc());
        entity.setTrangThai(dto.getTrangThai());
        entity.setNhaCungCap(ncc);
        return entity;
    }

    public static KhuyenMaiDTO toDTO(KhuyenMai khuyenMai){
        return KhuyenMaiDTO.builder()
                .maKM(khuyenMai.getMaKM())
                .maNCC(khuyenMai.getNhaCungCap().getMaNCC())
                .moTa(khuyenMai.getMoTa())
                .phanTram(khuyenMai.getPhanTram())
                .ngayBatDau(khuyenMai.getNgayBatDau())
                .ngayKetThuc(khuyenMai.getNgayKetThuc())
                .trangThai(khuyenMai.getTrangThai())
                .build();
    }
}
