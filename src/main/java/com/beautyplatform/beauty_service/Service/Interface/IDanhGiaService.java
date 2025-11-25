package com.beautyplatform.beauty_service.Service.Interface;

import com.beautyplatform.beauty_service.DTO.DanhGiaDTO.CreateDanhGiaCommandDTO;
import com.beautyplatform.beauty_service.DTO.DanhGiaDTO.CreateDanhGiaResponseDTO;

import java.util.Optional;
import java.util.List;
public interface IDanhGiaService {

    Optional<CreateDanhGiaResponseDTO> add(CreateDanhGiaCommandDTO request);
    List<CreateDanhGiaResponseDTO> getAllWithIdNCC(int diemDanhGia, int maNCC);
}
