package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.Service.Interface.ICloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService implements ICloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map<String, Object> uploadImage(MultipartFile file, String folder) throws IOException {
        try {
            // Validate file
            if (file.isEmpty()) {
                throw new IOException("File không được để trống");
            }

            // Validate file type
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IOException("File phải là hình ảnh");
            }

            // Validate file size (max 10MB)
            if (file.getSize() > 10 * 1024 * 1024) {
                throw new IOException("Kích thước file không được vượt quá 10MB");
            }

            // Tạo public_id duy nhất
            String publicId = folder + "/" + UUID.randomUUID().toString();

            // Upload lên Cloudinary - VERSION ĐƠN GIẢN
            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder,
                            "resource_type", "image"
                    )
            );

            return uploadResult;

        } catch (IOException e) {
            System.err.println("Lỗi khi upload ảnh: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Map<String, Object> deleteImage(String publicId) throws IOException {
        try {
            if (publicId == null || publicId.trim().isEmpty()) {
                throw new IOException("Public ID không được để trống");
            }

            Map<String, Object> deleteResult = cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.emptyMap()
            );

            return deleteResult;

        } catch (IOException e) {
            System.err.println("Lỗi khi xóa ảnh: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Map<String, Object> uploadImageFromBase64(String base64Image, String folder) throws IOException {
        try {
            if (base64Image == null || base64Image.trim().isEmpty()) {
                throw new IOException("Base64 image không được để trống");
            }

            // Tạo public_id duy nhất
            String publicId = folder + "/" + UUID.randomUUID().toString();

            // Upload lên Cloudinary
            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    base64Image,
                    ObjectUtils.asMap(
                            "public_id", publicId,
                            "folder", folder,
                            "resource_type", "image",
                            "quality", "auto",
                            "fetch_format", "auto"
                    )
            );

            return uploadResult;

        } catch (IOException e) {
            System.err.println("Lỗi khi upload ảnh từ Base64: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Helper method: Lấy URL từ kết quả upload
     */
    public String getImageUrl(Map<String, Object> uploadResult) {
        return (String) uploadResult.get("secure_url");
    }

    /**
     * Helper method: Lấy public_id từ kết quả upload
     */
    public String getPublicId(Map<String, Object> uploadResult) {
        return (String) uploadResult.get("public_id");
    }

    /**
     * Helper method: Trích xuất public_id từ URL Cloudinary
     */
    public String extractPublicIdFromUrl(String imageUrl) {
        if (imageUrl == null || !imageUrl.contains("cloudinary.com")) {
            return null;
        }

        try {
            // URL format: https://res.cloudinary.com/{cloud_name}/image/upload/v{version}/{public_id}.{format}
            String[] parts = imageUrl.split("/upload/");
            if (parts.length < 2) return null;

            String pathWithVersion = parts[1];
            // Bỏ phần version (vXXXXXXXXXX/)
            String path = pathWithVersion.replaceFirst("v\\d+/", "");
            // Bỏ extension
            int lastDot = path.lastIndexOf('.');
            if (lastDot > 0) {
                path = path.substring(0, lastDot);
            }

            return path;

        } catch (Exception e) {
            System.err.println("Lỗi khi trích xuất public_id: " + e.getMessage());
            return null;
        }
    }
}