package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.DTO.DanhGiaDTO.CreateDanhGiaCommandDTO;
import com.beautyplatform.beauty_service.DTO.DanhGiaDTO.CreateDanhGiaResponseDTO;
import com.beautyplatform.beauty_service.Mapper.DanhGiaMapper;
import com.beautyplatform.beauty_service.Model.DanhGia;
import com.beautyplatform.beauty_service.Model.DatLich;
import com.beautyplatform.beauty_service.Repository.DanhGiaRepository;
import com.beautyplatform.beauty_service.Repository.DatLichRepository;
import com.beautyplatform.beauty_service.Repository.KhachHangRepository;
import com.beautyplatform.beauty_service.Service.Interface.IDanhGiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DanhGiaService implements IDanhGiaService {

    private final DanhGiaRepository repository;
    private final DatLichRepository datLichRepository;
    private final KhachHangRepository khachHangRepository;

    @Override
    public Optional<CreateDanhGiaResponseDTO> add(CreateDanhGiaCommandDTO request){

        DatLich datLich = datLichRepository.findById(request.getMaDL())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đặt lịch: " + request.getMaDL()));


        DanhGia entity = DanhGiaMapper.toEntity(request, datLich);

        DanhGia saved = repository.save(entity);

        return Optional.of(DanhGiaMapper.toDTO(saved));
    }

    @Override
    public List<CreateDanhGiaResponseDTO> getAllWithIdNCC(int diemDanhGia, int maNCC) {
        return repository.findAllByNccAndDiemDanhGia(maNCC, diemDanhGia)
                .stream()
                .map(DanhGiaMapper::toDTO)
                .toList();
    }
}
