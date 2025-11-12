package com.beautyplatform.beauty_service.Controller;

import com.beautyplatform.beauty_service.DTO.LoaiHinhKinhDoanhDTO.LoaiHinhKinhDoanhDTO;
import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Service.Interface.ILoaiHinhKinhDoanhService;
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
@RequestMapping("/loaihinh")
public class LoaiHinhKinhDoanhController {

    @Autowired
    private ILoaiHinhKinhDoanhService loaiHinhKinhDoanhService;

    @Autowired
    private ApiResponse apiResponse;

    // LẤY TẤT CẢ
    @GetMapping
    public ResponseEntity<ApiResponse> getAllLoaiHinh(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "5") int size,
                                                      @RequestParam(required = false) String keyword) {
        try {
            // xử lý lấy số bản ghi lên từ database
            Pageable pageable = PageRequest.of(page, size);
            Page<LoaiHinhKinhDoanhDTO> pageResult;

            if (keyword != null && !keyword.trim().isEmpty()) {
                pageResult = loaiHinhKinhDoanhService.searchWithPage(keyword.trim(), pageable);
            } else {
                pageResult = loaiHinhKinhDoanhService.getAll(pageable);
            }

            if (pageResult != null && pageResult.hasContent()) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Lấy danh sách loại hình kinh doanh thành công!");
                apiResponse.setData(pageResult);
                return ResponseEntity.ok(apiResponse);
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không có dữ liệu loại hình kinh doanh nào!");
                apiResponse.setData(Page.empty(pageable));
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
            }
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // LẤY THEO ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getLoaiHinhById(@PathVariable("id") int maLH) {
        try {
            Optional<LoaiHinhKinhDoanhDTO> dto = loaiHinhKinhDoanhService.getById(maLH);
            if (dto.isPresent()) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Lấy loại hình kinh doanh thành công!");
                apiResponse.setData(dto);
                return ResponseEntity.ok(apiResponse);
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không có dữ liệu loại hình kinh doanh nào!");
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

    // THÊM
    @PostMapping
    public ResponseEntity<ApiResponse> addLoaiHinh(@RequestBody LoaiHinhKinhDoanhDTO dto) {
        try {
            if(loaiHinhKinhDoanhService.isNameExist(dto.getTenLH())) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Tên loại hình đã tồn tại trong hệ thống");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
            }

            Optional<LoaiHinhKinhDoanhDTO> addDTO = loaiHinhKinhDoanhService.add(dto);

            if (addDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Thêm loại hình kinh doanh không thành công");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Thêm loại hình kinh doanh mới thành công!");
            apiResponse.setData(addDTO.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // SỬA
    @PutMapping("/{maLH}")
    public ResponseEntity<ApiResponse> updateLoaiHinh(@PathVariable("maLH") int maLH, @RequestBody LoaiHinhKinhDoanhDTO dto) {
        try {
            if(loaiHinhKinhDoanhService.isNameExistUpdate(dto.getTenLH(), maLH)) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Tên loại hình đã tồn tại trong hệ thống");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
            }

            dto.setMaLH(maLH);

            Optional<LoaiHinhKinhDoanhDTO> updatedDTO = loaiHinhKinhDoanhService.update(dto);

            if (updatedDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy loại hình kinh doanh có mã: " + maLH);
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Cập nhật loại hình kinh doanh thành công!");
            apiResponse.setData(updatedDTO.get());
            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }
}