package com.beautyplatform.beauty_service.Service.Interface;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ICloudinaryService {

    /**
     * Upload ảnh lên Cloudinary
     * @param file File ảnh cần upload
     * @param folder Tên thư mục trên Cloudinary (vd: "khachhang", "nhanvien", "nhacungcap")
     * @return Map chứa thông tin ảnh đã upload (url, public_id, etc.)
     */
    Map<String, Object> uploadImage(MultipartFile file, String folder) throws IOException;

    /**
     * Xóa ảnh trên Cloudinary
     * @param publicId Public ID của ảnh cần xóa
     * @return Map chứa kết quả xóa
     */
    Map<String, Object> deleteImage(String publicId) throws IOException;

    /**
     * Upload ảnh từ Base64
     * @param base64Image Chuỗi base64 của ảnh
     * @param folder Tên thư mục trên Cloudinary
     * @return Map chứa thông tin ảnh đã upload
     */
    Map<String, Object> uploadImageFromBase64(String base64Image, String folder) throws IOException;
}