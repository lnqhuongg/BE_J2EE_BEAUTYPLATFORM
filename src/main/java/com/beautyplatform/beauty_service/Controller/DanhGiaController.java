package com.beautyplatform.beauty_service.Controller;

import com.beautyplatform.beauty_service.DTO.DanhGiaDTO.CreateDanhGiaCommandDTO;
import com.beautyplatform.beauty_service.DTO.DanhGiaDTO.CreateDanhGiaResponseDTO;
import com.beautyplatform.beauty_service.DTO.DanhGiaDTO.DanhGiaDTO;
import com.beautyplatform.beauty_service.DTO.DichVuDTO.DichVuResponseDTO;
import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Model.DanhGia;
import com.beautyplatform.beauty_service.Service.Impl.DanhGiaService;
import com.beautyplatform.beauty_service.Service.Interface.IDanhGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/danhgia")
public class DanhGiaController {
    @Autowired
    private IDanhGiaService danhGiaService;

    @Autowired
    private ApiResponse apiResponse;

    @PostMapping
    public ResponseEntity<ApiResponse> createDanhGia(
            @RequestBody CreateDanhGiaCommandDTO request) {
        try {
            var optionalRes = danhGiaService.add(request);

            if (optionalRes.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Tạo đánh giá thất bại!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            CreateDanhGiaResponseDTO res = optionalRes.get();
            apiResponse.setSuccess(true);
            apiResponse.setMessage("Tạo đánh giá thành công!");
            apiResponse.setData(res);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @GetMapping("/ncc/{idNCC}")
    public ResponseEntity<ApiResponse> getAllDanhGiaByNcc(
            @PathVariable("idNCC") int maNCC,
            @RequestParam(name = "diemDanhGia", defaultValue = "0") int diemDanhGia) {

        try {
            List<CreateDanhGiaResponseDTO> list = danhGiaService.getAllWithIdNCC(diemDanhGia, maNCC);

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Lấy danh sách đánh giá thành công!");
            apiResponse.setData(list); // nếu rỗng → FE tự xử lý
            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }
}
