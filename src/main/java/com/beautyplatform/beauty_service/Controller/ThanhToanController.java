package com.beautyplatform.beauty_service.Controller;

import com.beautyplatform.beauty_service.DTO.ThanhToanDTO.ThanhToanDTO;
import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Service.Impl.ThanhToanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/thanhtoan")
@RequiredArgsConstructor
public class ThanhToanController {

    private final ThanhToanService thanhToanService;

    @Autowired
    private ApiResponse apiResponse;

    @PostMapping
    public ResponseEntity<ApiResponse> addThanhToan(@RequestBody ThanhToanDTO dto) {
        ApiResponse api = new ApiResponse();
        try {
            Optional<ThanhToanDTO> saved = thanhToanService.add(dto);

            if (saved.isEmpty()) {
                api.setSuccess(false);
                api.setMessage("Thêm thanh toán thất bại!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(api);
            }

            api.setSuccess(true);
            api.setMessage("Tạo thanh toán thành công!");
            api.setData(saved.get());

            return ResponseEntity.status(HttpStatus.CREATED).body(api);
        } catch (Exception e) {
            api.setSuccess(false);
            api.setMessage("Lỗi: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);
        }
    }
}

