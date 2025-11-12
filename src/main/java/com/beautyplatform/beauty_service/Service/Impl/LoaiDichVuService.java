package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.DTO.LoaiDichVuDTO.LoaiDichVuDTO;
import com.beautyplatform.beauty_service.DTO.LoaiDichVuDTO.LoaiDichVuFilterDTO;
import com.beautyplatform.beauty_service.Mapper.LoaiDichVuMapper;
import com.beautyplatform.beauty_service.Model.LoaiDichVu;
import com.beautyplatform.beauty_service.Repository.LoaiDichVuRepository;
import com.beautyplatform.beauty_service.Service.Interface.ILoaiDichVuService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoaiDichVuService implements ILoaiDichVuService {

    @Autowired
    private LoaiDichVuRepository repository;

    @Override
    public Page<LoaiDichVuDTO> getAll(Pageable pageable) {
        try {
            Page<LoaiDichVu> pageEntity = repository.findAll(pageable);
            return pageEntity.map(LoaiDichVuMapper::toDTO);
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách loại dịch vụ: " + e.getMessage());
            return Page.empty(pageable);
        }
    }

    @Override
    public Page<LoaiDichVuDTO> filter(LoaiDichVuFilterDTO filter, Pageable pageable) {
        try {
            Page<LoaiDichVu> pageEntity = repository.filterLoaiDichVu(
                    filter.getTenLDV(),
                    filter.getTrangThai(),
                    pageable
            );
            return pageEntity.map(LoaiDichVuMapper::toDTO);
        } catch (Exception e) {
            System.err.println("Lỗi khi lọc loại dịch vụ: " + e.getMessage());
            return Page.empty(pageable);
        }
    }

    @Override
    public Optional<LoaiDichVuDTO> getById(int maLDV) {
        try {
            LoaiDichVu entity = repository.findById(maLDV)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy loại dịch vụ có mã: " + maLDV));
            return Optional.of(LoaiDichVuMapper.toDTO(entity));
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm loại dịch vụ: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<LoaiDichVuDTO> add(LoaiDichVuDTO dto) {
        try {
            LoaiDichVu entity = LoaiDichVuMapper.toEntity(dto);
            entity.setTrangThai(1); // Mặc định đang hoạt động
            LoaiDichVu saved = repository.save(entity);
            return Optional.of(LoaiDichVuMapper.toDTO(saved));
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm loại dịch vụ: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<LoaiDichVuDTO> update(LoaiDichVuDTO dto) {
        try {
            LoaiDichVu existing = repository.findById(dto.getMaLDV())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy loại dịch vụ để cập nhật"));

            existing.setTenLDV(dto.getTenLDV());
            existing.setTrangThai(dto.getTrangThai());

            LoaiDichVu updated = repository.save(existing);
            return Optional.of(LoaiDichVuMapper.toDTO(updated));
        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật loại dịch vụ: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<LoaiDichVuDTO> delete(LoaiDichVuDTO dto) {
        try {
            LoaiDichVu existing = repository.findById(dto.getMaLDV())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy loại dịch vụ để xóa"));

            existing.setTrangThai(0); // 0 = Ngưng hoạt động
            repository.save(existing);

            return Optional.of(LoaiDichVuMapper.toDTO(existing));
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa loại dịch vụ: " + e.getMessage());
            return Optional.empty();
        }
    }
}
