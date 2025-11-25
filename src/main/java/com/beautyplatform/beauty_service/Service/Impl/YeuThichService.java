package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.DTO.YeuThichDTO.CreateYeuThichCommandDTO;
import com.beautyplatform.beauty_service.DTO.YeuThichDTO.CreateYeuThichResponseDTO;
import com.beautyplatform.beauty_service.Mapper.YeuThichMapper;
import com.beautyplatform.beauty_service.Model.KhachHang;
import com.beautyplatform.beauty_service.Model.NhaCungCap;
import com.beautyplatform.beauty_service.Model.YeuThich;
import com.beautyplatform.beauty_service.Repository.KhachHangRepository;
import com.beautyplatform.beauty_service.Repository.NhaCungCapRepository;
import com.beautyplatform.beauty_service.Repository.YeuThichRepository;
import com.beautyplatform.beauty_service.Service.Interface.IYeuThichService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class YeuThichService implements IYeuThichService {

    @Autowired
    private YeuThichRepository repository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private NhaCungCapRepository nhaCungCaprepository;

    @Override
    public Optional<CreateYeuThichResponseDTO> add(CreateYeuThichCommandDTO request) {
        KhachHang khachHang = khachHangRepository.findById(request.getMaKH())
                .orElseThrow(()-> new RuntimeException(("Không tìm thấy khách hàng" + request.getMaKH())));
        NhaCungCap nhaCungCap = nhaCungCaprepository.findById(request.getMaNCC())
                .orElseThrow(()-> new RuntimeException(("Không tìm thấy nhà cung cấp" + request.getMaNCC())));

        YeuThich entity = YeuThichMapper.toEntity(request, khachHang , nhaCungCap);

        YeuThich saved = repository.save(entity);

        return Optional.of(YeuThichMapper.toDTO(saved));
    }

    @Override
    public List<CreateYeuThichResponseDTO> getAllWithIdKH(int maKH){
        return repository.findByKhachHang_MaKH(maKH)
                .stream()
                .map(YeuThichMapper::toDTO)
                .toList();
    }
}
