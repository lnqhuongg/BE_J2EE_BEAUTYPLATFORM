package com.beautyplatform.beauty_service.DTO.DichVuDTO;

import com.beautyplatform.beauty_service.Model.LoaiDichVu;
import com.beautyplatform.beauty_service.Model.NhaCungCap;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TimKiemDichVuDTO {
    private int maDV;
    // dto truyền vào chỉ nhận id của loại dich vụ với ncc thôi ko cần cả object
    private int maLDV;
    private int maNCC;
    private String tenDV;
}
