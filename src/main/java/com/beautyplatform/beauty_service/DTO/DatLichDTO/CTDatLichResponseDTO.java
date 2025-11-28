package com.beautyplatform.beauty_service.DTO.DatLichDTO;

import lombok.*;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CTDatLichResponseDTO {
    private int maCTDL;
    private int maDV;
    private int maNCC;
    private String tenDichVu;
    private int maNV;
    private String tenNhanVien;
    private LocalTime thoiGianBatDau;
    private LocalTime thoiGianKetThuc;
    private double gia;
}