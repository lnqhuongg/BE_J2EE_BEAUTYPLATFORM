package com.beautyplatform.beauty_service.Controller;

import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Service.Impl.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ApiResponse apiResponse;

    /**
     * Upload ảnh cho Khách hàng
     */
    @PostMapping("/khachhang")
    public ResponseEntity<ApiResponse> uploadKhachHangImage(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, Object> uploadResult = cloudinaryService.uploadImage(file, "khachhang");

            Map<String, String> data = new HashMap<>();
            data.put("url", cloudinaryService.getImageUrl(uploadResult));
            data.put("publicId", cloudinaryService.getPublicId(uploadResult));

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Upload ảnh khách hàng thành công!");
            apiResponse.setData(data);
            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Lỗi khi upload ảnh: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    /**
     * Upload ảnh cho Nhân viên
     */
    @PostMapping("/nhanvien")
    public ResponseEntity<ApiResponse> uploadNhanVienImage(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, Object> uploadResult = cloudinaryService.uploadImage(file, "nhanvien");

            Map<String, String> data = new HashMap<>();
            data.put("url", cloudinaryService.getImageUrl(uploadResult));
            data.put("publicId", cloudinaryService.getPublicId(uploadResult));

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Upload ảnh nhân viên thành công!");
            apiResponse.setData(data);
            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Lỗi khi upload ảnh: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    /**
     * Upload ảnh cho Nhà cung cấp
     */
    @PostMapping("/nhacungcap")
    public ResponseEntity<ApiResponse> uploadNhaCungCapImage(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, Object> uploadResult = cloudinaryService.uploadImage(file, "nhacungcap");

            Map<String, String> data = new HashMap<>();
            data.put("url", cloudinaryService.getImageUrl(uploadResult));
            data.put("publicId", cloudinaryService.getPublicId(uploadResult));

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Upload ảnh nhà cung cấp thành công!");
            apiResponse.setData(data);
            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Lỗi khi upload ảnh: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    /**
     * Upload ảnh từ Base64 (dùng cho OAuth2 hoặc khi frontend gửi base64)
     */
    @PostMapping("/base64")
    public ResponseEntity<ApiResponse> uploadBase64Image(
            @RequestBody Map<String, String> requestBody) {
        try {
            String base64Image = requestBody.get("image");
            String folder = requestBody.getOrDefault("folder", "general");

            if (base64Image == null || base64Image.trim().isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Base64 image không được để trống!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            Map<String, Object> uploadResult = cloudinaryService.uploadImageFromBase64(base64Image, folder);

            Map<String, String> data = new HashMap<>();
            data.put("url", cloudinaryService.getImageUrl(uploadResult));
            data.put("publicId", cloudinaryService.getPublicId(uploadResult));

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Upload ảnh từ base64 thành công!");
            apiResponse.setData(data);
            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Lỗi khi upload ảnh: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    /**
     * Xóa ảnh
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteImage(@RequestParam("publicId") String publicId) {
        try {
            Map<String, Object> deleteResult = cloudinaryService.deleteImage(publicId);

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Xóa ảnh thành công!");
            apiResponse.setData(deleteResult);
            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Lỗi khi xóa ảnh: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    /**
     * Xóa ảnh bằng URL
     */
    @DeleteMapping("/by-url")
    public ResponseEntity<ApiResponse> deleteImageByUrl(@RequestParam("url") String imageUrl) {
        try {
            String publicId = cloudinaryService.extractPublicIdFromUrl(imageUrl);

            if (publicId == null) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Không thể trích xuất public_id từ URL!");
                apiResponse.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

            Map<String, Object> deleteResult = cloudinaryService.deleteImage(publicId);

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Xóa ảnh thành công!");
            apiResponse.setData(deleteResult);
            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Lỗi khi xóa ảnh: " + e.getMessage());
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }
}