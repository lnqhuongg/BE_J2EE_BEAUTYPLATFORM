package com.beautyplatform.beauty_service.Controller;

import com.beautyplatform.beauty_service.DTO.NhanVienDTO.NhanVienDTO;
import com.beautyplatform.beauty_service.DTO.NhanVienDTO.TimKiemNhanVienDTO;
import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Service.Interface.INhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/nhanvien")
public class NhanVienController {

    @Autowired
    private INhanVienService nhanVienService;

    @Autowired
    private ApiResponse apiResponse;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllNhanVien(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @ModelAttribute TimKiemNhanVienDTO timKiemNhanVienDTO
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<NhanVienDTO> pageResult =
                    nhanVienService.getAllAndSearchWithPage(timKiemNhanVienDTO, pageable);

            if (pageResult != null && pageResult.hasContent()) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Lấy danh sách nhân viên thành công!");
                apiResponse.setData(pageResult);
                return ResponseEntity.ok(apiResponse); // 200
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không có dữ liệu nhân viên nào!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse); // 204
            }

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse); // 500
        }
    }

    // Lấy nhân viên theo mã
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getNhanVienById(@PathVariable("id") int maNV) {
        try {
            Optional<NhanVienDTO> nhanVienDTO = nhanVienService.getByNhanVienId(maNV);

            if (nhanVienDTO.isPresent()) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Lấy thông tin nhân viên thành công!");
                apiResponse.setData(nhanVienDTO.get());
                return ResponseEntity.ok(apiResponse); // 200
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy nhân viên có mã: " + maNV);
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse); // 404
            }
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse); // 500
        }
    }

    // Thêm nhân viên mới
    @PostMapping
    public ResponseEntity<ApiResponse> addNhanVien(@RequestBody NhanVienDTO nhanVienDTO) {
        try {
            Optional<NhanVienDTO> addDTO = nhanVienService.add(nhanVienDTO);

            if (addDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Thêm nhân viên không thành công!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Thêm nhân viên mới thành công!");
            apiResponse.setData(addDTO.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse); // 201
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse); // 500
        }
    }

    // Sửa nhân viên
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateNhanVien(@PathVariable("id") int maNV, @RequestBody NhanVienDTO nhanVienDTO) {
        try {
            nhanVienDTO.setMaNV(maNV); // gán lại id để entity hiểu đang update đối tượng nào

            Optional<NhanVienDTO> updatedDTO = nhanVienService.update(nhanVienDTO);

            if (updatedDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy nhân viên có mã: " + maNV);
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse); // 404
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Cập nhật nhân viên thành công!");
            apiResponse.setData(updatedDTO.get());
            return ResponseEntity.ok(apiResponse); // 200
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse); // 500
        }
    }
    // Xóa (soft delete) nhân viên
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteNhanVien(@PathVariable("id") int maNV) {
        try {
            // Tạo DTO tạm để truyền vào service
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setMaNV(maNV);

            Optional<NhanVienDTO> deletedDTO = nhanVienService.delete(nhanVienDTO);

            if (deletedDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy nhân viên có mã: " + maNV);
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse); // 404
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Xóa nhân viên thành công (đã chuyển sang trạng thái ngưng hoạt động)!");
            apiResponse.setData(deletedDTO.get());
            return ResponseEntity.ok(apiResponse); // 200
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi khi xóa: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse); // 500
        }
    }
}
