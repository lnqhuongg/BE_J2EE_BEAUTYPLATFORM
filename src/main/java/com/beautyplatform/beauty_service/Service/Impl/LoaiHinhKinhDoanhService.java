package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.DTO.LoaiHinhKinhDoanhDTO.LoaiHinhKinhDoanhDTO;
import com.beautyplatform.beauty_service.Mapper.LoaiHinhKinhDoanhMapper;
import com.beautyplatform.beauty_service.Model.LoaiHinhKinhDoanh;
import com.beautyplatform.beauty_service.Repository.LoaiHinhKinhDoanhRepository;
import com.beautyplatform.beauty_service.Service.Interface.ILoaiHinhKinhDoanhService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoaiHinhKinhDoanhService implements ILoaiHinhKinhDoanhService {
    @Autowired
    private LoaiHinhKinhDoanhRepository repository;

    @Override
    public Page<LoaiHinhKinhDoanhDTO> getAll(Pageable pageable){
        try {
            Page<LoaiHinhKinhDoanh> pageEntity = repository.findAll(pageable);
            return pageEntity.map(LoaiHinhKinhDoanhMapper::toDTO);
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách loại hình kinh doanh: " + e.getMessage());
            return Page.empty(pageable);
        }
    }

    @Override
    public Page<LoaiHinhKinhDoanhDTO> searchWithPage(String keyword, Pageable pageable) {
        try {
            Page<LoaiHinhKinhDoanh> pageEntity = repository.searchWithPage(keyword, pageable);
            return pageEntity.map(LoaiHinhKinhDoanhMapper::toDTO);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm kiếm có phân trang: " + e.getMessage());
            return Page.empty(pageable);
        }
    }

    @Override
    public Optional<LoaiHinhKinhDoanhDTO> getById(int maLH) {
        try {
            LoaiHinhKinhDoanh entity = repository.findById(maLH)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy loại hình kinh doanh: " + maLH));
            return Optional.of(LoaiHinhKinhDoanhMapper.toDTO(entity));
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm loại hình kinh doanh có mã: " + maLH + " - " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<LoaiHinhKinhDoanhDTO> add(LoaiHinhKinhDoanhDTO dto) {
        LoaiHinhKinhDoanh entity = LoaiHinhKinhDoanhMapper.toEntity(dto);
        entity.setTrangThai(1); // Mặc định hoạt động
        LoaiHinhKinhDoanh saved = repository.save(entity);
        return Optional.of(LoaiHinhKinhDoanhMapper.toDTO(saved));
    }

    @Override
    public Optional<LoaiHinhKinhDoanhDTO> update(LoaiHinhKinhDoanhDTO dto) {
        LoaiHinhKinhDoanh existing = repository.findById(dto.getMaLH())
                .orElse(null);
        if (existing == null) {
            return Optional.empty();
        }

        existing.setTenLH(dto.getTenLH());
        existing.setTrangThai(dto.getTrangThai());

        LoaiHinhKinhDoanh saved = repository.save(existing);
        return Optional.of(LoaiHinhKinhDoanhMapper.toDTO(saved));
    }

    public boolean isNameExist (String tenLH) {
        return repository.existsByTenLHIgnoreCase(tenLH);
    }

    public boolean isNameExistUpdate(String tenLH, int maLH) {
        return repository.existsByTenLHIgnoreCaseAndMaLHNot(tenLH,maLH);
    }
}
