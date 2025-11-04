package com.beautyplatform.beauty_service.DTO.LoaiDichVuDTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoaiDichVuDTO {
    private int maLDV;
    private String tenLDV;
    private int trangThai;
}
