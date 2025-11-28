package com.beautyplatform.beauty_service.DTO.DatLichDTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DatLichResponseDTO {
    private int maDL;
    private int maKH;
    private String tenKhachHang;
    private int tongThoiGian;
    private BigDecimal tongTien;
    private int trangThai;
    private String tenTrangThai; // "Chờ xác nhận", "Đã xác nhận", "Hoàn thành", "Đã hủy"
    private LocalDate ngayTao;
    private List<CTDatLichResponseDTO> chiTiet;
}