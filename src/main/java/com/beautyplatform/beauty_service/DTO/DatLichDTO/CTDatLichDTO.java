package com.beautyplatform.beauty_service.DTO.DatLichDTO;

import lombok.*;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CTDatLichDTO {
    private int maCTDL;
    private int maDL;
    private int maDV;
    private int maNV;
    private LocalTime thoiGianBatDau;
    private LocalTime thoiGianKetThuc;
    private double gia;
}
