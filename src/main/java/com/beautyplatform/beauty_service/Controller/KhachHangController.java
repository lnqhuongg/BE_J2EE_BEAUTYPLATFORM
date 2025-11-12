package com.beautyplatform.beauty_service.Controller;

import com.beautyplatform.beauty_service.DTO.KhachHangDTO.KhachHangDTO;
import com.beautyplatform.beauty_service.DTO.KhachHangDTO.TimKiemKhachHangDTO;
import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Service.Interface.IKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/khachhang")
public class KhachHangController {
    @Autowired
    private IKhachHangService khachHangService;

    @Autowired
    private ApiResponse apiResponse;
    //getall
    @GetMapping
    public ResponseEntity<ApiResponse> getAllKhachHang(){
        try{
            Optional<List<KhachHangDTO>> listKhachHangDTO = khachHangService.getAll();
            if(listKhachHangDTO.isPresent() && !listKhachHangDTO.get().isEmpty()){
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Lấy danh sách khách hàng thành công!");
                apiResponse.setData(listKhachHangDTO.get());
                return ResponseEntity.ok(apiResponse); // HTTP 200
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không có dữ liệu khách hàng nào!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse); // HTTP 204
            }
        }catch(Exception e){
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse); // HTTP 500
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
    //tìm kiếm khách hàng
    @PostMapping("/search")
    public ResponseEntity<ApiResponse> searchKhachHang(@RequestBody TimKiemKhachHangDTO timKiemKhachHangDTO) {
        try {
            Optional<List<KhachHangDTO>> result = khachHangService.search(timKiemKhachHangDTO);

            if (result.isPresent() && !result.get().isEmpty()) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Tìm kiếm khách hàng thành công!");
                apiResponse.setData(result.get());
                return ResponseEntity.ok(apiResponse);
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy khách hàng phù hợp!");
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
}
