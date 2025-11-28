package com.beautyplatform.beauty_service.Controller;

import com.beautyplatform.beauty_service.DTO.DatLichDTO.CTDatLichDTO;
import com.beautyplatform.beauty_service.DTO.DatLichDTO.DatLichDTO;
import com.beautyplatform.beauty_service.DTO.DatLichDTO.DatLichResponseDTO;
import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Service.Interface.IDatLichService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/datlich")
public class DatLichController {
    @Autowired
    private IDatLichService datLichService;

    @Autowired
    private ApiResponse apiResponse;

    // Những ngày ncc không làm việc
    @GetMapping("/valid-dates/{maNCC}")
    public ResponseEntity<ApiResponse> getInvalidDates(@PathVariable int maNCC) {
        try {
            List<LocalDate> invalidDates = datLichService.getValidDates(maNCC);

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

    // Những ngày, giờ rảnh của nhân viên
    @GetMapping("/available-times")
    public ResponseEntity<ApiResponse> getAvailableTimes(
            @RequestParam int maNCC,
            @RequestParam int maNV,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngay,
            @RequestParam int giodichvu
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

    // Tạo đặt lịch mới
    @PostMapping
    public ResponseEntity<ApiResponse> addDatLich(@RequestBody DatLichDTO dto) {
        try {
            Optional<DatLichDTO> result = datLichService.add(dto);

            if (result.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Thêm đặt lịch không thành công!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Tạo đặt lịch thành công!");
            apiResponse.setData(result.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    // Thêm chi tiết đặt lịch
    @PostMapping("/{maDL}/ct")
    public ResponseEntity<ApiResponse> addCTDatLich(
            @PathVariable int maDL,
            @RequestBody List<CTDatLichDTO> list
    ) {
        ApiResponse api = new ApiResponse();
        try {
            List<CTDatLichDTO> created = datLichService.addCTDatLichList(maDL, list);

            api.setSuccess(true);
            api.setMessage("Thêm chi tiết đặt lịch thành công!");
            api.setData(created);

            return ResponseEntity.status(HttpStatus.CREATED).body(api);
        } catch (Exception e) {
            api.setSuccess(false);
            api.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            api.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);
        }
    }

    // ========== API MỚI: XEM LỊCH HẸN CỦA KHÁCH HÀNG ==========

    // Lấy tất cả lịch hẹn của khách hàng
    @GetMapping("/khachhang/{maKH}")
    public ResponseEntity<ApiResponse> getByKhachHang(@PathVariable int maKH) {
        ApiResponse api = new ApiResponse();
        try {
            List<DatLichResponseDTO> list = datLichService.getByKhachHang(maKH);

            api.setSuccess(true);
            api.setMessage("Lấy danh sách lịch hẹn thành công!");
            api.setData(list);

            return ResponseEntity.ok(api);
        } catch (Exception e) {
            api.setSuccess(false);
            api.setMessage("Lỗi: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);
        }
    }

    // Lấy lịch hẹn của khách hàng theo trạng thái
    @GetMapping("/khachhang/{maKH}/trangthai/{trangThai}")
    public ResponseEntity<ApiResponse> getByKhachHangAndTrangThai(
            @PathVariable int maKH,
            @PathVariable int trangThai
    ) {
        ApiResponse api = new ApiResponse();
        try {
            List<DatLichResponseDTO> list = datLichService.getByKhachHangAndTrangThai(maKH, trangThai);

            api.setSuccess(true);
            api.setMessage("Lấy danh sách lịch hẹn theo trạng thái thành công!");
            api.setData(list);

            return ResponseEntity.ok(api);
        } catch (Exception e) {
            api.setSuccess(false);
            api.setMessage("Lỗi: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);
        }
    }

    // Lấy chi tiết một lịch hẹn
    @GetMapping("/{maDL}")
    public ResponseEntity<ApiResponse> getById(@PathVariable int maDL) {
        ApiResponse api = new ApiResponse();
        try {
            Optional<DatLichResponseDTO> result = datLichService.getById(maDL);

            if (result.isEmpty()) {
                api.setSuccess(false);
                api.setMessage("Không tìm thấy lịch hẹn!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(api);
            }

            api.setSuccess(true);
            api.setMessage("Lấy chi tiết lịch hẹn thành công!");
            api.setData(result.get());

            return ResponseEntity.ok(api);
        } catch (Exception e) {
            api.setSuccess(false);
            api.setMessage("Lỗi: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(api);
        }
    }
}