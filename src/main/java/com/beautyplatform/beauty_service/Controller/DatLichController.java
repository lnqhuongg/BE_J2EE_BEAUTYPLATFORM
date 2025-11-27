package com.beautyplatform.beauty_service.Controller;

import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Service.Interface.IDatLichService;
import com.beautyplatform.beauty_service.Service.Interface.INhaCungCapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/datlich")
public class DatLichController {
    @Autowired
    private IDatLichService datLichService;

    @Autowired
    private INhaCungCapService nhaCungCapService;

    @Autowired
    private ApiResponse apiResponse;

    // những ngày ncc không làm việc
    @GetMapping("/invalid-dates/{maNCC}")
    public ResponseEntity<ApiResponse> getInvalidDates(@PathVariable int maNCC) {
        try {
            List<LocalDate> invalidDates = nhaCungCapService.getInvalidDates(maNCC);

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Lấy danh sách ngày không hoạt động thành công!");
            apiResponse.setData(invalidDates);

            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Lỗi: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // những ngày, giờ rảnh của nhân viên
    @GetMapping("/available-times")
    public ResponseEntity<ApiResponse> getAvailableTimes(
            @RequestParam int maNCC,
            @RequestParam int maNV,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngay,
            @RequestParam int giodichvu // tính bằng phút
    ) {
        try {
            List<LocalTime> list = datLichService.getAvailableTimes(maNCC, maNV, ngay, giodichvu);

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Lấy giờ rảnh thành công!");
            apiResponse.setData(list);

            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Lỗi: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }
}