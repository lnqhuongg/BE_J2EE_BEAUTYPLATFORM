package com.beautyplatform.beauty_service.Controller;

import com.beautyplatform.beauty_service.DTO.LoaiDichVuDTO.LoaiDichVuDTO;
import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Service.Interface.ILoaiDichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/loaidichvu")
public class LoaiDichVuController {

    @Autowired
    private ILoaiDichVuService loaiDichVuService;

    @Autowired
    private ApiResponse apiResponse;

    // ✅ Lấy tất cả loại dịch vụ
    @GetMapping
    public ResponseEntity<ApiResponse> getAllLoaiDichVu() {
        try {
            Optional<List<LoaiDichVuDTO>> listLoaiDichVu = loaiDichVuService.getAll();

            if (listLoaiDichVu.isPresent() && !listLoaiDichVu.get().isEmpty()) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Lấy danh sách loại dịch vụ thành công!");
                apiResponse.setData(listLoaiDichVu.get());
                return ResponseEntity.ok(apiResponse);
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không có loại dịch vụ nào trong hệ thống!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
            }
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Lỗi khi lấy danh sách loại dịch vụ: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // ✅ Lấy loại dịch vụ theo mã
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getLoaiDichVuById(@PathVariable("id") int maLDV) {
        try {
            Optional<LoaiDichVuDTO> loaiDichVuDTO = loaiDichVuService.getById(maLDV);

            if (loaiDichVuDTO.isPresent()) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Lấy loại dịch vụ thành công!");
                apiResponse.setData(loaiDichVuDTO.get());
                return ResponseEntity.ok(apiResponse);
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy loại dịch vụ có mã: " + maLDV);
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Lỗi khi lấy loại dịch vụ: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // ✅ Thêm mới loại dịch vụ
    @PostMapping
    public ResponseEntity<ApiResponse> addLoaiDichVu(@RequestBody LoaiDichVuDTO loaiDichVuDTO) {
        try {
            Optional<LoaiDichVuDTO> addedDTO = loaiDichVuService.add(loaiDichVuDTO);

            if (addedDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Thêm loại dịch vụ không thành công!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Thêm loại dịch vụ mới thành công!");
            apiResponse.setData(addedDTO.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi khi thêm loại dịch vụ: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // ✅ Cập nhật loại dịch vụ
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateLoaiDichVu(
            @PathVariable("id") int maLDV,
            @RequestBody LoaiDichVuDTO loaiDichVuDTO) {
        try {
            loaiDichVuDTO.setMaLDV(maLDV); // gán lại mã để entity biết cần cập nhật

            Optional<LoaiDichVuDTO> updatedDTO = loaiDichVuService.update(loaiDichVuDTO);

            if (updatedDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy loại dịch vụ có mã: " + maLDV);
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Cập nhật loại dịch vụ thành công!");
            apiResponse.setData(updatedDTO.get());
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Lỗi khi cập nhật loại dịch vụ: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // ✅ Xóa (soft delete) loại dịch vụ
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteLoaiDichVu(@PathVariable("id") int maLDV) {
        try {
            LoaiDichVuDTO dto = new LoaiDichVuDTO();
            dto.setMaLDV(maLDV);

            Optional<LoaiDichVuDTO> deletedDTO = loaiDichVuService.delete(dto);

            if (deletedDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy loại dịch vụ có mã: " + maLDV);
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Xóa loại dịch vụ thành công (đã chuyển sang trạng thái ngưng hoạt động)!");
            apiResponse.setData(deletedDTO.get());
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Lỗi khi xóa loại dịch vụ: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // ✅ Tìm kiếm loại dịch vụ
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchLoaiDichVu(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer trangThai) {
        try {
            Optional<List<LoaiDichVuDTO>> result = loaiDichVuService.search(keyword, trangThai);

            if (result.isPresent() && !result.get().isEmpty()) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Tìm kiếm loại dịch vụ thành công!");
                apiResponse.setData(result.get());
                return ResponseEntity.ok(apiResponse);
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy loại dịch vụ phù hợp!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
            }
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Lỗi khi tìm kiếm loại dịch vụ: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }
}
