package com.beautyplatform.beauty_service.DTO.NhaCungCapDTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NhaCungCapHinhAnhDTO {
    private int maHinhAnh;
    private int maNCC;
    private String imageUrl;
    private String imageMain; // "1" = ảnh chính, "0" = ảnh phụ
}