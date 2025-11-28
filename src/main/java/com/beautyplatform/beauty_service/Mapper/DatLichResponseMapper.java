package com.beautyplatform.beauty_service.Mapper;

import com.beautyplatform.beauty_service.DTO.DatLichDTO.CTDatLichResponseDTO;
import com.beautyplatform.beauty_service.DTO.DatLichDTO.DatLichResponseDTO;
import com.beautyplatform.beauty_service.Model.CTDatLich;
import com.beautyplatform.beauty_service.Model.DatLich;

import java.util.List;

public class DatLichResponseMapper {

    public static DatLichResponseDTO toResponseDTO(DatLich entity, List<CTDatLich> chiTiet) {
        List<CTDatLichResponseDTO> chiTietDTO = chiTiet.stream()
                .map(DatLichResponseMapper::toCTResponseDTO)
                .toList();

        return DatLichResponseDTO.builder()
                .maDL(entity.getMaDL())
                .maKH(entity.getKhachHang().getMaKH())
                .tenKhachHang(entity.getKhachHang().getHoTen())
                .tongThoiGian(entity.getTongThoiGian())
                .tongTien(entity.getTongTien())
                .trangThai(entity.getTrangThai())
                .tenTrangThai(getTrangThaiText(entity.getTrangThai()))
                .ngayTao(entity.getNgayTao())
                .chiTiet(chiTietDTO)
                .build();
    }

    public static CTDatLichResponseDTO toCTResponseDTO(CTDatLich entity) {
        return CTDatLichResponseDTO.builder()
                .maCTDL(entity.getMaCTDL())
                .maDV(entity.getDichVu().getMaDV())
                .maNCC(entity.getDichVu().getNhaCungCap().getMaNCC())
                .tenDichVu(entity.getDichVu().getTenDV())
                .maNV(entity.getNhanVien().getMaNV())
                .tenNhanVien(entity.getNhanVien().getHoTen())
                .thoiGianBatDau(entity.getThoiGianBatDau())
                .thoiGianKetThuc(entity.getThoiGianKetThuc())
                .gia(entity.getGia())
                .build();
    }

    private static String getTrangThaiText(int trangThai) {
        return switch (trangThai) {
            case 0 -> "Chờ xác nhận";
            case 1 -> "Đã xác nhận";
            case 2 -> "Hoàn thành";
            case 3 -> "Đã hủy";
            default -> "Không xác định";
        };
    }
}