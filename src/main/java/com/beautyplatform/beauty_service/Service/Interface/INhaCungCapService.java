package com.beautyplatform.beauty_service.Service.Interface;

import com.beautyplatform.beauty_service.DTO.NhaCungCapDTO.NhaCungCapDTO;
import com.beautyplatform.beauty_service.DTO.NhaCungCapDTO.NhaCungCapGioLamViecDTO;
import com.beautyplatform.beauty_service.DTO.NhaCungCapDTO.NhaCungCapHinhAnhDTO;
import com.beautyplatform.beauty_service.DTO.NhaCungCapDTO.TimKiemNhaCungCapDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface INhaCungCapService {
    // CRUD cơ bản
    Page<NhaCungCapDTO> getAllAndSearchWithPage(TimKiemNhaCungCapDTO timKiemNhaCungCapDTO,
                                                Pageable pageable);
    Optional<NhaCungCapDTO> add(NhaCungCapDTO nhaCungCapDTO);
    Optional<NhaCungCapDTO> update(NhaCungCapDTO nhaCungCapDTO);

    Optional<NhaCungCapDTO> getById(int maNCC);


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
}