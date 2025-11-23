package com.beautyplatform.beauty_service.Controller;

import com.beautyplatform.beauty_service.DTO.KhachHangDTO.KhachHangDTO;
import com.beautyplatform.beauty_service.DTO.KhachHangDTO.TimKiemKhachHangDTO;
import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Service.Interface.IKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/khachhang")
public class KhachHangController {
    @Autowired
    private IKhachHangService khachHangService;

    @Autowired
    private ApiResponse apiResponse;

// Lấy tất cả khách hàng hoặc tìm kiếm bằng keyword
    @GetMapping
    public ResponseEntity<ApiResponse> getAllKhachHang(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyword) {

        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<KhachHangDTO> pageResult;

            // nếu có keyword thì tìm kiếm
            if (keyword != null && !keyword.trim().isEmpty()) {
                pageResult = khachHangService.searchWithPage(keyword.trim(), pageable);
            }
            // nếu không có keyword thì lấy tất cả
            else {
                pageResult = khachHangService.getAll(pageable);
            }

            // kiểm tra kết quả
            if (pageResult != null && pageResult.hasContent()) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Lấy danh sách khách hàng thành công!");
                apiResponse.setData(pageResult);
                return ResponseEntity.ok(apiResponse); // 200 OK
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không có khách hàng nào!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse); // 204
            }

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Lỗi xảy ra khi lấy danh sách khách hàng: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse); // 500
        }
    }

    //id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getKhuyenMaiById(@PathVariable("id") int maKH) {
        try{
            Optional<KhachHangDTO> khachHangDTO = khachHangService.getByKhachHangId(maKH);
            if(khachHangDTO.isPresent()){
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Lấy mã khách hàng thành công!");
                apiResponse.setData(khachHangDTO);
                return ResponseEntity.ok(apiResponse); // HTTP 200
            } else{
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không có dữ liệu khách hàng nào!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse); // HTTP 204
            }
        }catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse); // HTTP 500
        }
    }
    //
    @PutMapping("/{maKH}")
    public ResponseEntity<ApiResponse> updateKhachHang (@PathVariable("maKH") int maKH, @RequestBody KhachHangDTO khachHangDTO) {
        try{

            if(khachHangService.isExistPhoneNumber(khachHangDTO.getSdt())) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Số điện thoại đã tồn tại trong hệ thống");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
            }

            khachHangDTO.setMaKH(maKH);
            Optional<KhachHangDTO> updatedDTO = khachHangService.update(khachHangDTO);

            if(updatedDTO.isEmpty()){
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy khách hàng có mã: " + maKH);
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse); // 404
            }else{
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Cập nhật khách hàng thành công!");
                apiResponse.setData(updatedDTO.get());
                return ResponseEntity.ok(apiResponse); // 200 OK
            }
        }catch (Exception e){
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse); // HTTP 500
        }
    }

    //them
    @PostMapping
    public ResponseEntity<ApiResponse> addKhachHang(@RequestBody KhachHangDTO khachHangDTO) {
        try{

            if(khachHangService.isExistPhoneNumber(khachHangDTO.getSdt())) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Số điện thoại đã tồn tại trong hệ thống");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
            }

            Optional<KhachHangDTO> addDTO = khachHangService.add(khachHangDTO);
            if(addDTO.isEmpty()){
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Thêm khách hàng không thành công!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Thêm khách hàng mới thành công!");
            apiResponse.setData(addDTO.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }catch (Exception e){
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
            }

    }
}
