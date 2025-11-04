package com.beautyplatform.beauty_service.Controller;

import com.beautyplatform.beauty_service.DTO.DichVuDTO.DichVuDTO;
import com.beautyplatform.beauty_service.DTO.KhuyenMaiDTO.KhuyenMaiDTO;
import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Service.Interface.IKhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5500")
@RestController
@RequestMapping("/khuyenmai")
public class KhuyenMaiController {
    @Autowired
    private IKhuyenMaiService khuyenMaiService;

    @Autowired
    private ApiResponse apiResponse;
    //getall
    @GetMapping
    public ResponseEntity<ApiResponse> getAllKhuyenMai(){
        try{
            Optional<List<KhuyenMaiDTO>> listKhuyenMaiDTO = khuyenMaiService.getAll();
            if(listKhuyenMaiDTO.isPresent() && !listKhuyenMaiDTO.get().isEmpty()){
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Lấy danh sách khuyến mãi thành công!");
                apiResponse.setData(listKhuyenMaiDTO.get());
                return ResponseEntity.ok(apiResponse); // HTTP 200
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không có dữ liệu khuyến mãi nào!");
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
    public ResponseEntity<ApiResponse> getKhuyenMaiById(@PathVariable("id") int maKM) {
        try{
            Optional<KhuyenMaiDTO> khuyenMaiDTO = khuyenMaiService.getByKhuyenMaiId(maKM);
            if(khuyenMaiDTO.isPresent()){
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Lấy mã khuyến mãi thành công!");
                apiResponse.setData(khuyenMaiDTO);
                return ResponseEntity.ok(apiResponse); // HTTP 200
            } else{
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không có dữ liệu khuyến mãi nào!");
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
    //them
    @PostMapping
    public ResponseEntity<ApiResponse> addKhuyenMai(@RequestBody KhuyenMaiDTO khuyenMaiDTO){
        try{
            Optional<KhuyenMaiDTO> addDTO = khuyenMaiService.add(khuyenMaiDTO);

            if(addDTO.isEmpty()){
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Thêm khuyến mãi không thành công!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }else{
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Thêm khuyến mãi mới thành công!");
                apiResponse.setData(addDTO.get());
                return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
            }
        }catch (Exception e){
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse); // HTTP 500
        }
    }
    //sua
    @PutMapping("/{maKM}")
    public ResponseEntity<ApiResponse> updateKhuyenMai (@PathVariable("maKM") int maKM, @RequestBody KhuyenMaiDTO khuyenMaiDTO) {
        try{
             khuyenMaiDTO.setMaKM(maKM);
             Optional<KhuyenMaiDTO> updatedDTO = khuyenMaiService.update(khuyenMaiDTO);

             if(updatedDTO.isEmpty()){
                 apiResponse.setSuccess(false);
                 apiResponse.setMessage("Không tìm thấy khuyến mãi có mã: " + maKM);
                 apiResponse.setData(null);
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse); // 404
             }else{
                 apiResponse.setSuccess(true);
                 apiResponse.setMessage("Cập nhật khuyến mãi thành công!");
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
}
