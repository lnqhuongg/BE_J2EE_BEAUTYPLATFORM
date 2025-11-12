package com.beautyplatform.beauty_service.Service.Interface;

import com.beautyplatform.beauty_service.DTO.DichVuDTO.DichVuDTO;
import com.beautyplatform.beauty_service.DTO.DichVuDTO.DichVuFilterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IDichVuService {
    // thêm
    public Optional<DichVuDTO> add(DichVuDTO dichVuDTO);
    // sửa
    public Optional<DichVuDTO> update(DichVuDTO dichVuDTO);
    // xóa
    public Optional<DichVuDTO> delete(DichVuDTO dichVuDTO);
    // lấy tất cả (hiển thị trên danh sách)
    public Page<DichVuDTO> getAll(Pageable pageable);
    // lấy theo id (dùng khi nhấn vào modal chỉnh sửa trên giao diện)
    public Optional<DichVuDTO> getByDichVuId(int id);
    // tìm kiếm và bộ lọc
    public Page<DichVuDTO> filter(DichVuFilterDTO dichVuFilterDTO, Pageable pageable);
}
