package com.beautyplatform.beauty_service.DTO.NhaCungCapDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimKiemNhaCungCapDTO {
    private Integer maNCC;
    private Integer maLH;
    private String tenNCC;
    private String diaChi;
    private String email;
}