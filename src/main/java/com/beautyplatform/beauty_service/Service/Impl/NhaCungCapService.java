package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.DTO.NhaCungCapDTO.*;
import com.beautyplatform.beauty_service.Mapper.NhaCungCapGioLamViecMapper;
import com.beautyplatform.beauty_service.Mapper.NhaCungCapHinhAnhMapper;
import com.beautyplatform.beauty_service.Mapper.NhaCungCapMapper;
import com.beautyplatform.beauty_service.Model.*;
import com.beautyplatform.beauty_service.Repository.*;
import com.beautyplatform.beauty_service.Service.Interface.INhaCungCapService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NhaCungCapService implements INhaCungCapService {

    @Autowired
    private NhaCungCapRepository repository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private LoaiHinhKinhDoanhRepository loaiHinhRepository;

    @Autowired
    private NhaCungCapGioLamViecRepository gioLamViecRepository;

    @Autowired
    private NhaCungCapHinhAnhRepository hinhAnhRepository;

    // ==================== CRUD NHÀ CUNG CẤP ====================

    @Override
    public Page<NhaCungCapResponseDTO> getAllAndSearchWithPage(
            TimKiemNhaCungCapDTO timKiemDTO,
            Pageable pageable) {
        try {
            // Gọi repository search
            Page<NhaCungCap> pageEntity = repository.searchWithPage(
                    timKiemDTO.getMaNCC(),
                    timKiemDTO.getMaTK(),
                    timKiemDTO.getMaLH(),
                    timKiemDTO.getTenNCC(),
                    timKiemDTO.getDiaChi(),
                    pageable
            );

            // Map sang ResponseDTO (có đầy đủ thông tin)
            return pageEntity.map(NhaCungCapMapper::toResponseDTO);

        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách NCC: " + e.getMessage());
            return Page.empty(pageable);
        }
    }

    @Override
    public Optional<NhaCungCapResponseDTO> getById(int maNCC) {
        try {
            NhaCungCap entity = repository.findById(maNCC)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy NCC: " + maNCC));

            return Optional.of(NhaCungCapMapper.toResponseDTO(entity));

        } catch (Exception e) {
            System.err.println("Lỗi khi tìm NCC: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<NhaCungCapDTO> add(NhaCungCapDTO dto) {
        try {
            // Validate TaiKhoan
            TaiKhoan taiKhoan =  taiKhoanRepository.findById(dto.getMaTK())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tài khoản"));

            // Validate LoaiHinh
            LoaiHinhKinhDoanh loaiHinh = loaiHinhRepository.findById(dto.getMaLH())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy loại hình kinh doanh"));

            NhaCungCap entity = NhaCungCapMapper.toEntity(dto, taiKhoan, loaiHinh);
            NhaCungCap saved = repository.save(entity);

            return Optional.of(NhaCungCapMapper.toDTO(saved));
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm nhà cung cấp: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<NhaCungCapDTO> update(NhaCungCapDTO dto) {
        try {
            // Lấy nhà cung cấp hiện có
            NhaCungCap existing = repository.findById(dto.getMaNCC())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhà cung cấp"));

            // Cập nhật tài khoản nếu DTO có maTK
            if (dto.getMaTK() > 0) {
                TaiKhoan taiKhoan = taiKhoanRepository.findById(dto.getMaTK())
                        .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tài khoản"));
                existing.setTaiKhoan(taiKhoan);
            }

            // Cập nhật loại hình kinh doanh nếu DTO có maLH
            if (dto.getMaLH() > 0) {
                LoaiHinhKinhDoanh loaiHinh = loaiHinhRepository.findById(dto.getMaLH())
                        .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy loại hình kinh doanh"));
                existing.setLoaiHinhKinhDoanh(loaiHinh);
            }

            // Cập nhật các trường thông tin khác
            if (dto.getTenNCC() != null) existing.setTenNCC(dto.getTenNCC());
            if (dto.getGioiThieu() != null) existing.setGioiThieu(dto.getGioiThieu());
            if (dto.getDiaChi() != null) existing.setDiaChi(dto.getDiaChi());

            // Lưu và trả về DTO
            NhaCungCap updated = repository.save(existing);
            return Optional.of(NhaCungCapMapper.toDTO(updated));

        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật nhà cung cấp: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ==================== GIỜ LÀM VIỆC ====================

    @Override
    public Optional<List<NhaCungCapGioLamViecDTO>> getGioLamViecByNCC(int maNCC) {
        try {
            List<NhaCungCapGioLamViec> entities = gioLamViecRepository.findByNhaCungCap_MaNCC(maNCC);
            List<NhaCungCapGioLamViecDTO> dtoList = entities.stream()
                    .map(NhaCungCapGioLamViecMapper::toDTO)
                    .toList();
            return Optional.of(dtoList);
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy giờ làm việc: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<NhaCungCapGioLamViecDTO> addGioLamViec(NhaCungCapGioLamViecDTO dto) {
        try {
            NhaCungCap ncc = repository.findById(dto.getMaNCC())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhà cung cấp"));

            NhaCungCapGioLamViec entity = NhaCungCapGioLamViecMapper.toEntity(dto, ncc);
            NhaCungCapGioLamViec saved = gioLamViecRepository.save(entity);

            return Optional.of(NhaCungCapGioLamViecMapper.toDTO(saved));
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm giờ làm việc: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<NhaCungCapGioLamViecDTO> updateGioLamViec(NhaCungCapGioLamViecDTO dto) {
        try {
            NhaCungCapGioLamViec existing = gioLamViecRepository.findById(dto.getMaGioLamViec())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy giờ làm việc"));

            existing.setNgayTrongTuan(dto.getNgayTrongTuan());
            existing.setGioMoCua(dto.getGioMoCua());
            existing.setGioDongCua(dto.getGioDongCua());

            NhaCungCapGioLamViec updated = gioLamViecRepository.save(existing);
            return Optional.of(NhaCungCapGioLamViecMapper.toDTO(updated));
        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật giờ làm việc: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<NhaCungCapGioLamViecDTO> deleteGioLamViec(int maGioLamViec) {
        try {
            NhaCungCapGioLamViec entity = gioLamViecRepository.findById(maGioLamViec)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy giờ làm việc"));

            NhaCungCapGioLamViecDTO dto = NhaCungCapGioLamViecMapper.toDTO(entity);
            gioLamViecRepository.deleteById(maGioLamViec);

            return Optional.of(dto);
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa giờ làm việc: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ==================== HÌNH ẢNH ====================

    @Override
    public Optional<List<NhaCungCapHinhAnhDTO>> getHinhAnhByNCC(int maNCC) {
        try {
            List<NhaCungCapHinhAnh> entities = hinhAnhRepository.findByNhaCungCap_MaNCC(maNCC);
            List<NhaCungCapHinhAnhDTO> dtoList = entities.stream()
                    .map(NhaCungCapHinhAnhMapper::toDTO)
                    .toList();
            return Optional.of(dtoList);
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy hình ảnh: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<NhaCungCapHinhAnhDTO> addHinhAnh(NhaCungCapHinhAnhDTO dto) {
        try {
            NhaCungCap ncc = repository.findById(dto.getMaNCC())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhà cung cấp"));

            NhaCungCapHinhAnh entity = NhaCungCapHinhAnhMapper.toEntity(dto, ncc);
            NhaCungCapHinhAnh saved = hinhAnhRepository.save(entity);

            return Optional.of(NhaCungCapHinhAnhMapper.toDTO(saved));
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm hình ảnh: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<NhaCungCapHinhAnhDTO> updateHinhAnh(NhaCungCapHinhAnhDTO dto) {
        try {
            NhaCungCapHinhAnh existing = hinhAnhRepository.findById(dto.getMaHinhAnh())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy hình ảnh"));

            existing.setImageUrl(dto.getImageUrl());
            existing.setImageMain(dto.getImageMain());

            NhaCungCapHinhAnh updated = hinhAnhRepository.save(existing);
            return Optional.of(NhaCungCapHinhAnhMapper.toDTO(updated));
        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật hình ảnh: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<NhaCungCapHinhAnhDTO> deleteHinhAnh(int maHinhAnh) {
        try {
            NhaCungCapHinhAnh entity = hinhAnhRepository.findById(maHinhAnh)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy hình ảnh"));

            NhaCungCapHinhAnhDTO dto = NhaCungCapHinhAnhMapper.toDTO(entity);
            hinhAnhRepository.deleteById(maHinhAnh);

            return Optional.of(dto);
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa hình ảnh: " + e.getMessage());
            return Optional.empty();
        }
    }
}