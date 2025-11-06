package com.beautyplatform.beauty_service.DTO.NhaCungCapDTO;

import lombok.*;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NhaCungCapGioLamViecDTO {
    private int maGioLamViec;
    private int maNCC;
    private int ngayTrongTuan; // 2=T2, 3=T3,..., 8=CN
    private LocalTime gioMoCua;
    private LocalTime gioDongCua;
}