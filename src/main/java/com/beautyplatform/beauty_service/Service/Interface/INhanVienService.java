package com.beautyplatform.beauty_service.Service.Interface;

import com.beautyplatform.beauty_service.DTO.NhanVienDTO.NhanVienDTO;
import com.beautyplatform.beauty_service.DTO.NhanVienDTO.TimKiemNhanVienDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface INhanVienService {
    // Thêm mới nhân viên
    public Optional<NhanVienDTO> add(NhanVienDTO nhanVienDTO);

    // Cập nhật thông tin nhân viên
    public Optional<NhanVienDTO> update(NhanVienDTO nhanVienDTO);

    // Xóa (hoặc cập nhật trạng thái nhân viên)
    public Optional<NhanVienDTO> delete(NhanVienDTO nhanVienDTO);


    // Lấy thông tin nhân viên theo mã (dùng khi mở modal chỉnh sửa)
    public Optional<NhanVienDTO> getByNhanVienId(int id);


    Page<NhanVienDTO> getAllAndSearchWithPage(TimKiemNhanVienDTO timKiemNhanVienDTO,
                                              Pageable pageable);
}
