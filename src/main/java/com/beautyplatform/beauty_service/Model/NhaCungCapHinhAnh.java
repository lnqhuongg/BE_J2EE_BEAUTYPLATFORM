package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nhacungcap_hinhanh")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NhaCungCapHinhAnh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mahinhanh")
    private int maHinhAnh;

    @ManyToOne
    @JoinColumn(name = "maNCC", nullable = false)
    private NhaCungCap nhaCungCap;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl; // URL hoặc đường dẫn tới hình ảnh

    @Column(name = "image_main", nullable = false, length = 255)
    private String imageMain; // Có thể dùng để đánh dấu hình ảnh chính hoặc phụ
}
