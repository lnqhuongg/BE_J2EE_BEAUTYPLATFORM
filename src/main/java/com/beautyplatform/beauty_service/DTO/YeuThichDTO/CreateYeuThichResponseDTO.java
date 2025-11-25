package com.beautyplatform.beauty_service.DTO.YeuThichDTO;


import com.beautyplatform.beauty_service.Model.KhachHang;
import com.beautyplatform.beauty_service.Model.NhaCungCap;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class CreateYeuThichResponseDTO {
    private int maYT;
    private KhachHang khachHang;
    private NhaCungCap nhaCungCap;
}
