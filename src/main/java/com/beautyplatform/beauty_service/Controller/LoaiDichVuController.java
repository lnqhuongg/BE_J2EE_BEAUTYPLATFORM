package com.beautyplatform.beauty_service.Controller;

import com.beautyplatform.beauty_service.DTO.LoaiDichVuDTO.LoaiDichVuDTO;
import com.beautyplatform.beauty_service.DTO.LoaiDichVuDTO.LoaiDichVuFilterDTO;
import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Service.Interface.ILoaiDichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    public ResponseEntity<Page<LoaiDichVuDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(loaiDichVuService.getAll(pageable));
    }

    // 2️⃣ Bộ lọc loại dịch vụ
    @PostMapping("/filter")
    public ResponseEntity<Page<LoaiDichVuDTO>> filter(
            @RequestBody LoaiDichVuFilterDTO filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(loaiDichVuService.filter(filter, pageable));
    }

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
}
