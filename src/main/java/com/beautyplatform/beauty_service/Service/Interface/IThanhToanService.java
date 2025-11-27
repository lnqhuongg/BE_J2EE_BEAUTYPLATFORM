package com.beautyplatform.beauty_service.Service.Interface;

import com.beautyplatform.beauty_service.DTO.ThanhToanDTO.ThanhToanDTO;

import java.util.Optional;

public interface IThanhToanService {
    Optional<ThanhToanDTO> add(ThanhToanDTO dto);
}
