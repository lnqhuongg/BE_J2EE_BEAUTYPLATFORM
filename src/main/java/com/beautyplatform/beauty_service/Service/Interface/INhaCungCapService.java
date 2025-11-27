package com.beautyplatform.beauty_service.Service.Interface;

import com.beautyplatform.beauty_service.DTO.NhaCungCapDTO.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface INhaCungCapService {
    // CRUD cơ bản
    Page<NhaCungCapResponseDTO> getAllAndSearchWithPage(
            TimKiemNhaCungCapDTO timKiemDTO,
            Pageable pageable
    );
    Optional<NhaCungCapDTO> add(NhaCungCapDTO nhaCungCapDTO);
    Optional<NhaCungCapDTO> update(NhaCungCapDTO nhaCungCapDTO);

    Optional<NhaCungCapResponseDTO> getById(int maNCC);

    // Quản lý giờ làm việc
    Optional<NhaCungCapGioLamViecDTO> addGioLamViec(NhaCungCapGioLamViecDTO dto);
    Optional<NhaCungCapGioLamViecDTO> updateGioLamViec(NhaCungCapGioLamViecDTO dto);
    Optional<NhaCungCapGioLamViecDTO> deleteGioLamViec(int maGioLamViec);
    Optional<List<NhaCungCapGioLamViecDTO>> getGioLamViecByNCC(int maNCC);

    // Quản lý hình ảnh
    Optional<NhaCungCapHinhAnhDTO> addHinhAnh(NhaCungCapHinhAnhDTO dto);
    Optional<NhaCungCapHinhAnhDTO> updateHinhAnh(NhaCungCapHinhAnhDTO dto);
    Optional<NhaCungCapHinhAnhDTO> deleteHinhAnh(int maHinhAnh);
    Optional<List<NhaCungCapHinhAnhDTO>> getHinhAnhByNCC(int maNCC);
    List<LocalDate> getInvalidDates(int maNCC);
}