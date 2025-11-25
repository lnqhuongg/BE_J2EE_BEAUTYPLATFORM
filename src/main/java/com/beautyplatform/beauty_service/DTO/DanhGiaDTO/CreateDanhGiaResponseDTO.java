package com.beautyplatform.beauty_service.DTO.DanhGiaDTO;

import com.beautyplatform.beauty_service.Model.DatLich;
import com.beautyplatform.beauty_service.Model.KhachHang;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreateDanhGiaResponseDTO {
    private int maDG;
    private DatLich datLich;
    private int diemDanhGia;
    private String noiDung;
    private LocalDate ngayDanhGia;
}
