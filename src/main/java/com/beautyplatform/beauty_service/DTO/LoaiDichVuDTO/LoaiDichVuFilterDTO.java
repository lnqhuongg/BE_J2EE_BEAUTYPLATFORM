package com.beautyplatform.beauty_service.DTO.LoaiDichVuDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoaiDichVuFilterDTO {
    private String tenLDV;   // lọc theo tên loại dịch vụ
    private Integer trangThai; // lọc theo trạng thái (1: active, 0: inactive)
}
