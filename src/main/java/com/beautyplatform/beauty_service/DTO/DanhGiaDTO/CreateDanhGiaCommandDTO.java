package com.beautyplatform.beauty_service.DTO.DanhGiaDTO;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class CreateDanhGiaCommandDTO {
    private int maDL;
    private int diemDanhGia;
    private String noiDung;
}
