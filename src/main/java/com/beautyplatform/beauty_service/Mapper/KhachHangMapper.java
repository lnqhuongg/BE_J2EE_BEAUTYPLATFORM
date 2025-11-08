package com.beautyplatform.beauty_service.Mapper;

import com.beautyplatform.beauty_service.DTO.KhachHangDTO.KhachHangDTO;
import com.beautyplatform.beauty_service.Model.KhachHang;
import com.beautyplatform.beauty_service.Model.TaiKhoan;

public class KhachHangMapper {
    public static KhachHang toEntity(KhachHangDTO dto, TaiKhoan tk){
        KhachHang entity = new KhachHang();
        entity.setMaKH(dto.getMaKH());
        entity.setTaiKhoan(tk);
        entity.setHoTen(dto.getHoTen());
        entity.setGioiTinh(dto.getGioiTinh());
        entity.setNgaySinh(dto.getNgaySinh());
        entity.setSdt(dto.getSdt());
        entity.setHinhAnh(dto.getHinhAnh());
        return entity;
    }

    public static KhachHangDTO toDTO(KhachHang khachHang){
        return KhachHangDTO.builder()
                .maKH(khachHang.getMaKH())
                .maTK(khachHang.getTaiKhoan().getMaTK())
                .hoTen(khachHang.getHoTen())
                .gioiTinh(khachHang.getGioiTinh())
                .ngaySinh(khachHang.getNgaySinh())
                .sdt(khachHang.getSdt())
                .hinhAnh(khachHang.getHinhAnh())
                .build();
    }
}
