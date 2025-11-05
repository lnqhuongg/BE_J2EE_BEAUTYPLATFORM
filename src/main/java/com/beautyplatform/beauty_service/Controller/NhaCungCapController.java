package com.beautyplatform.beauty_service.Controller;

import com.beautyplatform.beauty_service.DTO.NhaCungCapDTO.NhaCungCapDTO;
import com.beautyplatform.beauty_service.DTO.NhaCungCapDTO.NhaCungCapGioLamViecDTO;
import com.beautyplatform.beauty_service.DTO.NhaCungCapDTO.NhaCungCapHinhAnhDTO;
import com.beautyplatform.beauty_service.DTO.NhaCungCapDTO.TimKiemNhaCungCapDTO;
import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Service.Interface.INhaCungCapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5500")
@RestController
@RequestMapping("/nhacungcap")
public class NhaCungCapController {

    @Autowired
    private INhaCungCapService nhaCungCapService;

    @Autowired
    private ApiResponse apiResponse;

    // ==================== CRUD NHÀ CUNG CẤP ====================

    // Lấy tất cả nhà cung cấp
    @GetMapping
    public ResponseEntity<ApiResponse> getAllNhaCungCap() {
        try {
            Optional<List<NhaCungCapDTO>> listDTO = nhaCungCapService.getAll();

            if (listDTO.isPresent() && !listDTO.get().isEmpty()) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Lấy danh sách nhà cung cấp thành công!");
                apiResponse.setData(listDTO.get());
                return ResponseEntity.ok(apiResponse);
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không có nhà cung cấp nào!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
            }
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // Lấy nhà cung cấp theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getNhaCungCapById(@PathVariable("id") int maNCC) {
        try {
            Optional<NhaCungCapDTO> dto = nhaCungCapService.getById(maNCC);

            if (dto.isPresent()) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Lấy thông tin nhà cung cấp thành công!");
                apiResponse.setData(dto.get());
                return ResponseEntity.ok(apiResponse);
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy nhà cung cấp có mã: " + maNCC);
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // Tìm kiếm nhà cung cấp
    @PostMapping("/search")
    public ResponseEntity<ApiResponse> searchNhaCungCap(@RequestBody TimKiemNhaCungCapDTO timKiemDTO) {
        try {
            Optional<List<NhaCungCapDTO>> result = nhaCungCapService.search(timKiemDTO);

            if (result.isPresent() && !result.get().isEmpty()) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Tìm kiếm nhà cung cấp thành công!");
                apiResponse.setData(result.get());
                return ResponseEntity.ok(apiResponse);
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy nhà cung cấp phù hợp!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
            }
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // Thêm nhà cung cấp mới
    @PostMapping
    public ResponseEntity<ApiResponse> addNhaCungCap(@RequestBody NhaCungCapDTO nhaCungCapDTO) {
        try {
            Optional<NhaCungCapDTO> addDTO = nhaCungCapService.add(nhaCungCapDTO);

            if (addDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Thêm nhà cung cấp không thành công!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Thêm nhà cung cấp mới thành công!");
            apiResponse.setData(addDTO.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // Cập nhật nhà cung cấp
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateNhaCungCap(
            @PathVariable("id") int maNCC,
            @RequestBody NhaCungCapDTO nhaCungCapDTO) {
        try {
            nhaCungCapDTO.setMaNCC(maNCC);

            Optional<NhaCungCapDTO> updatedDTO = nhaCungCapService.update(nhaCungCapDTO);

            if (updatedDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy nhà cung cấp có mã: " + maNCC);
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Cập nhật nhà cung cấp thành công!");
            apiResponse.setData(updatedDTO.get());
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

// ==================== GIỜ LÀM VIỆC ====================

    // Lấy giờ làm việc của nhà cung cấp
    @GetMapping("/{id}/giolamviec")
    public ResponseEntity<ApiResponse> getGioLamViecByNCC(@PathVariable("id") int maNCC) {
        try {
            Optional<List<NhaCungCapGioLamViecDTO>> listDTO = nhaCungCapService.getGioLamViecByNCC(maNCC);

            if (listDTO.isPresent() && !listDTO.get().isEmpty()) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Lấy giờ làm việc thành công!");
                apiResponse.setData(listDTO.get());
                return ResponseEntity.ok(apiResponse);
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không có giờ làm việc nào!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
            }
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // Thêm giờ làm việc
    @PostMapping("/giolamviec")
    public ResponseEntity<ApiResponse> addGioLamViec(@RequestBody NhaCungCapGioLamViecDTO dto) {
        try {
            Optional<NhaCungCapGioLamViecDTO> addDTO = nhaCungCapService.addGioLamViec(dto);

            if (addDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Thêm giờ làm việc không thành công!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Thêm giờ làm việc thành công!");
            apiResponse.setData(addDTO.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // Cập nhật giờ làm việc
    @PutMapping("/giolamviec/{id}")
    public ResponseEntity<ApiResponse> updateGioLamViec(
            @PathVariable("id") int maGioLamViec,
            @RequestBody NhaCungCapGioLamViecDTO dto) {
        try {
            dto.setMaGioLamViec(maGioLamViec);

            Optional<NhaCungCapGioLamViecDTO> updatedDTO = nhaCungCapService.updateGioLamViec(dto);

            if (updatedDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy giờ làm việc có mã: " + maGioLamViec);
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Cập nhật giờ làm việc thành công!");
            apiResponse.setData(updatedDTO.get());
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // Xóa giờ làm việc
    @DeleteMapping("/giolamviec/{id}")
    public ResponseEntity<ApiResponse> deleteGioLamViec(@PathVariable("id") int maGioLamViec) {
        try {
            Optional<NhaCungCapGioLamViecDTO> deletedDTO = nhaCungCapService.deleteGioLamViec(maGioLamViec);

            if (deletedDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy giờ làm việc có mã: " + maGioLamViec);
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Xóa giờ làm việc thành công!");
            apiResponse.setData(deletedDTO.get());
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

// ==================== HÌNH ẢNH ====================

    // Lấy hình ảnh của nhà cung cấp
    @GetMapping("/{id}/hinhanh")
    public ResponseEntity<ApiResponse> getHinhAnhByNCC(@PathVariable("id") int maNCC) {
        try {
            Optional<List<NhaCungCapHinhAnhDTO>> listDTO = nhaCungCapService.getHinhAnhByNCC(maNCC);

            if (listDTO.isPresent() && !listDTO.get().isEmpty()) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Lấy hình ảnh thành công!");
                apiResponse.setData(listDTO.get());
                return ResponseEntity.ok(apiResponse);
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không có hình ảnh nào!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
            }
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // Thêm hình ảnh
    @PostMapping("/hinhanh")
    public ResponseEntity<ApiResponse> addHinhAnh(@RequestBody NhaCungCapHinhAnhDTO dto) {
        try {
            Optional<NhaCungCapHinhAnhDTO> addDTO = nhaCungCapService.addHinhAnh(dto);

            if (addDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Thêm hình ảnh không thành công!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Thêm hình ảnh thành công!");
            apiResponse.setData(addDTO.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // Cập nhật hình ảnh
    @PutMapping("/hinhanh/{id}")
    public ResponseEntity<ApiResponse> updateHinhAnh(
            @PathVariable("id") int maHinhAnh,
            @RequestBody NhaCungCapHinhAnhDTO dto) {
        try {
            dto.setMaHinhAnh(maHinhAnh);

            Optional<NhaCungCapHinhAnhDTO> updatedDTO = nhaCungCapService.updateHinhAnh(dto);

            if (updatedDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy hình ảnh có mã: " + maHinhAnh);
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Cập nhật hình ảnh thành công!");
            apiResponse.setData(updatedDTO.get());
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // Xóa hình ảnh
    @DeleteMapping("/hinhanh/{id}")
    public ResponseEntity<ApiResponse> deleteHinhAnh(@PathVariable("id") int maHinhAnh) {
        try {
            Optional<NhaCungCapHinhAnhDTO> deletedDTO = nhaCungCapService.deleteHinhAnh(maHinhAnh);

            if (deletedDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy hình ảnh có mã: " + maHinhAnh);
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Xóa hình ảnh thành công!");
            apiResponse.setData(deletedDTO.get());
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }
}