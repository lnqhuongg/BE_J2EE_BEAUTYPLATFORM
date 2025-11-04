package com.beautyplatform.beauty_service.Mapper;

import com.beautyplatform.beauty_service.DTO.NhanVienDTO.NhanVienDTO;
import com.beautyplatform.beauty_service.Model.NhaCungCap;
import com.beautyplatform.beauty_service.Model.NhanVien;

public class NhanVienMapper {

    public static NhanVienDTO toDTO(NhanVien entity) {
        return NhanVienDTO.builder()
                .maNV(entity.getMaNV())
                .maNCC(entity.getNhaCungCap().getMaNCC())
                .hoTen(entity.getHoTen())
                .sdt(entity.getSdt())
                .gioiTinh(entity.getGioiTinh())
                .hinhAnh(entity.getHinhAnh())
                .trangThai(entity.getTrangThai())
                .build();
    }

    public static NhanVien toEntity(NhanVienDTO dto, NhaCungCap nhaCungCap) {
        return NhanVien.builder()
                .maNV(dto.getMaNV())
                .nhaCungCap(nhaCungCap)
                .hoTen(dto.getHoTen())
                .sdt(dto.getSdt())
                .gioiTinh(dto.getGioiTinh())
                .hinhAnh(dto.getHinhAnh())
                .trangThai(dto.getTrangThai())
                .build();
    }
}
