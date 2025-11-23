package com.beautyplatform.beauty_service.Controller;

import com.beautyplatform.beauty_service.DTO.DichVuDTO.DichVuDTO;
import com.beautyplatform.beauty_service.DTO.DichVuDTO.DichVuResponseDTO;
import com.beautyplatform.beauty_service.DTO.DichVuDTO.TimKiemDichVuDTO;
import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Service.Interface.IDichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/dichvu")
public class DichVuController {
    @Autowired
    private IDichVuService dichVuService;

    @Autowired
    private ApiResponse apiResponse;

    // lấy tất cả, danh sách dịch vụ
    // @ModelAttribute hỗ trợ tìm kiếm (query bằng đường dẫn VD: /dichvu?page=0&size=5&maDV=1&tenDV=“Cắt tóc”&maLDV=2)
    @GetMapping
    public ResponseEntity<ApiResponse> getAllDichVu(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "5") int size,
                                                    @ModelAttribute TimKiemDichVuDTO timKiemDichVuDTO) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<DichVuResponseDTO> pageResult = dichVuService.getAllAndSearchWithPage(timKiemDichVuDTO, pageable);

            // Nếu Optional có dữ liệu và danh sách không rỗng
            if (pageResult != null && pageResult.hasContent()) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Lấy danh sách dịch vụ thành công!");
                apiResponse.setData(pageResult);
                return ResponseEntity.ok(apiResponse); // HTTP 200
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không có dữ liệu dịch vụ nào!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse); // HTTP 204
            }
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse); // HTTP 500
        }
    }

    // lấy theo id (sử dụng cho sửa hoặc xem chi tiết)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getDichVuById(@PathVariable("id") int maDV) {
        try {
            Optional<DichVuResponseDTO> DichVuResponseDTO = dichVuService.getByDichVuId(maDV);
            // isPresent() để kiểm tra xem Optional trên có giá trị hay không
            if (DichVuResponseDTO.isPresent()) {
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Lấy danh sách dịch vụ thành công!");
                apiResponse.setData(DichVuResponseDTO);
                return ResponseEntity.ok(apiResponse); // HTTP 200
            } else {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không có dữ liệu dịch vụ nào!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse); // HTTP 204
            }
        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse); // HTTP 500
        }
    }

    // thêm
    @PostMapping
    public ResponseEntity<ApiResponse> addDichVu(@RequestBody DichVuDTO dichVuDTO) {
        try {
            Optional<DichVuResponseDTO> addDTO = dichVuService.add(dichVuDTO);

            if (addDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Thêm dịch vụ không thành công");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Thêm dịch vụ mới thành công!");
            // thêm + sửa trả về data là addDTO lấy từ service chứ ko phải là dto nhận từ form nha
            apiResponse.setData(addDTO.get()); // dùng get() vì đã check isEmpty ở trên
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse); // HTTP 201 - chuẩn REST
        } catch (Exception e){
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse); // HTTP 500
        }
    }

    // sửa
    @PutMapping("/{maDV}")
    public ResponseEntity<ApiResponse> updateDichVu (@PathVariable("maDV") int maDV, @RequestBody DichVuDTO dichVuDTO) {
        try {
            // lúc submit chỉnh sửa, thì phải gửi cả id của đối tượng đó thì entity mới hiểu được là đang sửa ở đối tượng nào trong bảng
            dichVuDTO.setMaDV(maDV);

            Optional<DichVuResponseDTO> updatedDTO = dichVuService.update(dichVuDTO);

            if (updatedDTO.isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không tìm thấy dịch vụ có mã: " + maDV);
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse); // 404
            }

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Cập nhật dịch vụ thành công!");
            apiResponse.setData(updatedDTO.get());
            return ResponseEntity.ok(apiResponse); // 200 OK

        } catch (Exception e){
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse); // HTTP 500
        }
    }
}
