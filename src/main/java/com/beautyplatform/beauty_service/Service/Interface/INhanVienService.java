package com.beautyplatform.beauty_service.Service.Interface;

import com.beautyplatform.beauty_service.DTO.NhanVienDTO.NhanVienDTO;
import com.beautyplatform.beauty_service.DTO.NhanVienDTO.TimKiemNhanVienDTO;

import java.util.List;
import java.util.Optional;

public interface INhanVienService {
    // Thêm mới nhân viên
    public Optional<NhanVienDTO> add(NhanVienDTO nhanVienDTO);

    // Cập nhật thông tin nhân viên
    public Optional<NhanVienDTO> update(NhanVienDTO nhanVienDTO);

    // Xóa (hoặc cập nhật trạng thái nhân viên)
    public Optional<NhanVienDTO> delete(NhanVienDTO nhanVienDTO);

    // Lấy toàn bộ danh sách nhân viên
    public Optional<List<NhanVienDTO>> getAll();

    // Lấy thông tin nhân viên theo mã (dùng khi mở modal chỉnh sửa)
    public Optional<NhanVienDTO> getByNhanVienId(int id);

    // Tìm kiếm hoặc lọc nhân viên (VD: theo tên, chức vụ, nhà cung cấp,…)
    public Optional<List<NhanVienDTO>> search(TimKiemNhanVienDTO timKiemNhanVienDTO);
}
