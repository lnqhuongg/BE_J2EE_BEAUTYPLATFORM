package com.beautyplatform.beauty_service.DTO.NhaCungCapDTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NhaCungCapDTO {
    private int maNCC;
    private int maTK;
    private int maLH;
    private String tenNCC;
    private String gioiThieu;
    private String diaChi;
}