package com.beautyplatform.beauty_service.Service.Interface;

import com.beautyplatform.beauty_service.DTO.LoaiDichVuDTO.LoaiDichVuDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ILoaiDichVuService {

    // Thêm mới loại dịch vụ
    Optional<LoaiDichVuDTO> add(LoaiDichVuDTO loaiDichVuDTO);

    // Cập nhật loại dịch vụ
    Optional<LoaiDichVuDTO> update(LoaiDichVuDTO loaiDichVuDTO);

    // Xóa (hoặc cập nhật trạng thái) loại dịch vụ
    Optional<LoaiDichVuDTO> delete(LoaiDichVuDTO loaiDichVuDTO);

    // Lấy toàn bộ danh sách loại dịch vụ
    Page<LoaiDichVuDTO> getAll(Pageable pageable);

    // tìm kiếm + phân trang cho loại dịch vụ
    Page<LoaiDichVuDTO> searchWithPage(String keyword, Pageable pageable);

    // Lấy loại dịch vụ theo mã
    Optional<LoaiDichVuDTO> getById(int maLDV);
}
