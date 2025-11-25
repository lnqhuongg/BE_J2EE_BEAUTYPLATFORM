package com.beautyplatform.beauty_service.Mapper;

import com.beautyplatform.beauty_service.DTO.NhaCungCapDTO.NhaCungCapDTO;
import com.beautyplatform.beauty_service.DTO.NhaCungCapDTO.NhaCungCapResponseDTO;
import com.beautyplatform.beauty_service.Model.LoaiHinhKinhDoanh;
import com.beautyplatform.beauty_service.Model.NhaCungCap;
import com.beautyplatform.beauty_service.Model.TaiKhoan;

public class NhaCungCapMapper {

    // Entity → DTO
    public static NhaCungCapDTO toDTO(NhaCungCap entity) {
        if (entity == null) return null;

        return NhaCungCapDTO.builder()
                .maNCC(entity.getMaNCC())
                .maTK(entity.getTaiKhoan().getMaTK())
                .maLH(entity.getLoaiHinhKinhDoanh().getMaLH())
                .tenNCC(entity.getTenNCC())
                .gioiThieu(entity.getGioiThieu())
                .diaChi(entity.getDiaChi())
                .build();
    }

    // DTO → Entity
    public static NhaCungCap toEntity(NhaCungCapDTO dto, TaiKhoan taiKhoan, LoaiHinhKinhDoanh loaiHinh) {
        if (dto == null) return null;

        return NhaCungCap.builder()
                .maNCC(dto.getMaNCC())
                .taiKhoan(taiKhoan)
                .loaiHinhKinhDoanh(loaiHinh)
                .tenNCC(dto.getTenNCC())
                .gioiThieu(dto.getGioiThieu())
                .diaChi(dto.getDiaChi())
                .build();
    }

    public static NhaCungCapResponseDTO toResponseDTO(NhaCungCap entity) {
        if (entity == null) return null;

        return NhaCungCapResponseDTO.builder()
                // Thông tin NCC
                .maNCC(entity.getMaNCC())
                .tenNCC(entity.getTenNCC())
                .gioiThieu(entity.getGioiThieu())
                .diaChi(entity.getDiaChi())

                // Thông tin Tài Khoản
                .maTK(entity.getTaiKhoan().getMaTK())
                .email(entity.getTaiKhoan().getEmail())
                .trangThai(entity.getTaiKhoan().getTrangThai())

                // Thông tin Loại Hình
                .maLH(entity.getLoaiHinhKinhDoanh().getMaLH())
                .tenLH(entity.getLoaiHinhKinhDoanh().getTenLH())
                .build();
    }
}