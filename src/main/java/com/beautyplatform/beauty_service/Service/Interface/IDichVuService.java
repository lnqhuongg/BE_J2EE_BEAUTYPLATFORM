package com.beautyplatform.beauty_service.Service.Interface;

import com.beautyplatform.beauty_service.DTO.DichVuDTO.DichVuDTO;
import com.beautyplatform.beauty_service.DTO.DichVuDTO.TimKiemDichVuDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IDichVuService {
    // thêm
    Optional<DichVuDTO> add(DichVuDTO dichVuDTO);
    // sửa
    Optional<DichVuDTO> update(DichVuDTO dichVuDTO);
    // xóa
    Optional<DichVuDTO> delete(DichVuDTO dichVuDTO);
    // lấy tất cả (hiển thị trên danh sách)
    Page<DichVuDTO> getAllAndSearchWithPage(TimKiemDichVuDTO timKiemDichVuDTO, Pageable pageable);
    //tìm kiếm & bộ lọc
    // lấy theo id (dùng khi nhấn vào modal chỉnh sửa trên giao diện)
    Optional<DichVuDTO> getByDichVuId(int id);
}
