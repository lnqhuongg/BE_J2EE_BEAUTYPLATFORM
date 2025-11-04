package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.DTO.LoaiDichVuDTO.LoaiDichVuDTO;
import com.beautyplatform.beauty_service.Mapper.LoaiDichVuMapper;
import com.beautyplatform.beauty_service.Model.LoaiDichVu;
import com.beautyplatform.beauty_service.Repository.LoaiDichVuRepository;
import com.beautyplatform.beauty_service.Service.Interface.ILoaiDichVuService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoaiDichVuService implements ILoaiDichVuService {

    @Autowired
    private LoaiDichVuRepository repository;

    // ✅ Lấy toàn bộ loại dịch vụ
    @Override
    public Optional<List<LoaiDichVuDTO>> getAll() {
        try {
            List<LoaiDichVu> entities = repository.findAll();
            List<LoaiDichVuDTO> dtoList = entities.stream()
                    .map(LoaiDichVuMapper::toDTO)
                    .toList();
            return Optional.of(dtoList);
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách loại dịch vụ: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ✅ Lấy loại dịch vụ theo mã
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

    // ✅ Thêm mới loại dịch vụ
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

    // ✅ Cập nhật loại dịch vụ
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

    // ✅ Xóa (soft delete)
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

    // ✅ Tìm kiếm loại dịch vụ theo tên hoặc trạng thái
    @Override
    public Optional<List<LoaiDichVuDTO>> search(String keyword, Integer trangThai) {
        try {
            // Giả sử bạn đã có custom query trong repository
            List<LoaiDichVu> results = repository.search(keyword, trangThai);
            List<LoaiDichVuDTO> dtoList = results.stream()
                    .map(LoaiDichVuMapper::toDTO)
                    .toList();
            return Optional.of(dtoList);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm kiếm loại dịch vụ: " + e.getMessage());
            return Optional.empty();
        }
    }
}
