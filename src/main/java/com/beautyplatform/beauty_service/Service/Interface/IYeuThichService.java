package com.beautyplatform.beauty_service.Service.Interface;

import com.beautyplatform.beauty_service.DTO.YeuThichDTO.CreateYeuThichCommandDTO;
import com.beautyplatform.beauty_service.DTO.YeuThichDTO.CreateYeuThichResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IYeuThichService {
    Optional<CreateYeuThichResponseDTO> add(CreateYeuThichCommandDTO request);
    List<CreateYeuThichResponseDTO> getAllWithIdKH(int maKH);
}
