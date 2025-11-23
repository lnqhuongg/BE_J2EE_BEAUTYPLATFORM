package com.beautyplatform.beauty_service.Service.Interface;


import com.beautyplatform.beauty_service.DTO.LoaiHinhKinhDoanhDTO.LoaiHinhKinhDoanhDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ILoaiHinhKinhDoanhService {

    // Thêm mới loại dịch vụ
    Optional<LoaiHinhKinhDoanhDTO> add(LoaiHinhKinhDoanhDTO loaiHinhKinhDoanhDTO);

    // Cập nhật loại dịch vụ
    Optional<LoaiHinhKinhDoanhDTO> update(LoaiHinhKinhDoanhDTO loaiHinhKinhDoanhDTO);

    // Lấy toàn bộ danh sách loại dịch vụ
    Page<LoaiHinhKinhDoanhDTO> getAll(Pageable pageable);

    // tìm kiếm có phân trang
    Page<LoaiHinhKinhDoanhDTO> searchWithPage(String keyword, Pageable pageable);

    // Lấy loại dịch vụ theo mã
    Optional<LoaiHinhKinhDoanhDTO> getById(int maLH);

    boolean isNameExist (String tenLH);

    boolean isNameExistUpdate(String tenLH, int maLH);
}
