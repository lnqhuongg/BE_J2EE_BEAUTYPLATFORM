package com.beautyplatform.beauty_service.Controller;

import com.beautyplatform.beauty_service.DTO.YeuThichDTO.CreateYeuThichCommandDTO;
import com.beautyplatform.beauty_service.DTO.YeuThichDTO.CreateYeuThichResponseDTO;
import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Service.Interface.IYeuThichService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/yeuthich")
public class YeuThichController {
    @Autowired
    private IYeuThichService yeuThichService;

    @Autowired
    private ApiResponse apiResponse;

    @PostMapping
    public ResponseEntity<ApiResponse> createYeuThich(
            @RequestBody CreateYeuThichCommandDTO request) {
        try {
            var optionalRes = yeuThichService.add(request);

            if (optionalRes.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Tạo yêu thích thất bại!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            CreateYeuThichResponseDTO res = optionalRes.get();
            apiResponse.setSuccess(true);
            apiResponse.setMessage("Tạo yêu thích thành công!");
            apiResponse.setData(res);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @GetMapping("/kh/{idKH}")
    public ResponseEntity<ApiResponse> getAllYeuThichbyKH(
            @PathVariable("idKH") int maKH) {

        try {
            List<CreateYeuThichResponseDTO> list = yeuThichService.getAllWithIdKH(maKH);

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Lấy danh sách yêu thích theo khách hàng thành công!");
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
