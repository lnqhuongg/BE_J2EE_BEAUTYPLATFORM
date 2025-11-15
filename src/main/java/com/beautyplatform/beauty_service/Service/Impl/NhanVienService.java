package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.DTO.NhanVienDTO.NhanVienDTO;
import com.beautyplatform.beauty_service.DTO.NhanVienDTO.TimKiemNhanVienDTO;
import com.beautyplatform.beauty_service.Mapper.NhanVienMapper;
import com.beautyplatform.beauty_service.Model.NhaCungCap;
import com.beautyplatform.beauty_service.Model.NhanVien;
import com.beautyplatform.beauty_service.Repository.NhaCungCapRepository;
import com.beautyplatform.beauty_service.Repository.NhanVienRepository;
import com.beautyplatform.beauty_service.Service.Interface.INhanVienService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class NhanVienService implements INhanVienService {

    @Autowired
    private NhanVienRepository repository;

    @Autowired
    private NhaCungCapRepository nhaCungCapRepository;

    @Override
    public Page<NhanVienDTO> getAllAndSearchWithPage(TimKiemNhanVienDTO timKiemNhanVienDTO,
                                                     Pageable pageable) {
        try {
            Page<NhanVien> pageEntity = repository.searchWithPage(
                    timKiemNhanVienDTO.getMaNV(),
                    timKiemNhanVienDTO.getMaNCC(),
                    timKiemNhanVienDTO.getHoTen(),
                    timKiemNhanVienDTO.getGioiTinh(),
                    timKiemNhanVienDTO.getTrangThai(),
                    pageable
            );

            return pageEntity.map(NhanVienMapper::toDTO);

        } catch (Exception e) {
            System.err.println("Lỗi khi tìm kiếm nhân viên có phân trang: " + e.getMessage());
            return Page.empty(pageable);
        }
    }

    // Lấy theo mã NV
    @Override
    public Optional<NhanVienDTO> getByNhanVienId(int maNV) {
        try {
            NhanVien nhanVien = repository.findById(maNV)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhân viên: " + maNV));
            return Optional.of(NhanVienMapper.toDTO(nhanVien));
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm nhân viên: " + e.getMessage());
            return Optional.empty();
        }
    }


    // Thêm nhân viên mới
    @Override
    public Optional<NhanVienDTO> add(NhanVienDTO nhanVienDTO) {
        NhaCungCap nhaCungCap = nhaCungCapRepository.findById(nhanVienDTO.getMaNCC())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhà cung cấp"));

        NhanVien nv = NhanVienMapper.toEntity(nhanVienDTO, nhaCungCap);
        nv.setTrangThai(1); // Mặc định đang hoạt động

        NhanVien saveNV = repository.save(nv);
        return Optional.of(NhanVienMapper.toDTO(saveNV));
    }

    // Cập nhật nhân viên
    @Override
    public Optional<NhanVienDTO> update(NhanVienDTO nhanVienDTO) {
        NhanVien existing = repository.findById(nhanVienDTO.getMaNV())
                .orElse(null);
        if (existing == null) {
            return Optional.empty();
        }

        NhaCungCap nhaCungCap = nhaCungCapRepository.findById(nhanVienDTO.getMaNCC())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhà cung cấp"));

        existing.setHoTen(nhanVienDTO.getHoTen());
        existing.setSdt(nhanVienDTO.getSdt());
        existing.setTrangThai(nhanVienDTO.getTrangThai());
        existing.setGioiTinh(nhanVienDTO.getGioiTinh());
        existing.setHinhAnh(nhanVienDTO.getHinhAnh());
        existing.setNhaCungCap(nhaCungCap);

        NhanVien saveNV = repository.save(existing);
        return Optional.of(NhanVienMapper.toDTO(saveNV));
    }

    // Xóa (soft delete nếu bạn muốn)
    @Override
    public Optional<NhanVienDTO> delete(NhanVienDTO nhanVienDTO) {
        try {
            NhanVien existing = repository.findById(nhanVienDTO.getMaNV())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhân viên"));
            existing.setTrangThai(0); // 0 = ngưng hoạt động
            repository.save(existing);
            return Optional.of(NhanVienMapper.toDTO(existing));
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa nhân viên: " + e.getMessage());
            return Optional.empty();
        }
    }
}
