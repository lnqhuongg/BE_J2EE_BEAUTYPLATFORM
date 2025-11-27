package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.DTO.ThanhToanDTO.ThanhToanDTO;
import com.beautyplatform.beauty_service.Mapper.ThanhToanMapper;
import com.beautyplatform.beauty_service.Model.DatLich;
import com.beautyplatform.beauty_service.Model.ThanhToan;
import com.beautyplatform.beauty_service.Repository.DatLichRepository;
import com.beautyplatform.beauty_service.Repository.ThanhToanRepository;
import com.beautyplatform.beauty_service.Service.Interface.IThanhToanService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThanhToanService implements IThanhToanService {
    @Autowired
    private final ThanhToanRepository thanhToanRepository;
    @Autowired
    private final DatLichRepository datLichRepository;

    @Override
    public Optional<ThanhToanDTO> add(ThanhToanDTO dto) {
        try {
            // Kiểm tra DatLich tồn tại
            DatLich datLich = datLichRepository.findById(dto.getMaDL())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đặt lịch"));

            ThanhToan entity = ThanhToanMapper.toEntity(dto, datLich);
            ThanhToan saved = thanhToanRepository.save(entity);

            return Optional.of(ThanhToanMapper.toDTO(saved));
        } catch (Exception e) {
            System.err.println("Lỗi thêm thanh toán: " + e.getMessage());
            return Optional.empty();
        }
    }
}
