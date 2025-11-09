package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "taikhoan")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maTK")
    private int maTK;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "matkhau") // Nullable vì OAuth không cần password
    private String matKhau;

    @Column(name = "loaiTK", nullable = false)
    private int loaiTK; // 1=Admin, 2=NCC, 3=KhachHang

    @Column(name = "trangthai", nullable = false)
    private int trangThai;

    @Column(name = "ngaytao", nullable = false)
    private LocalDateTime ngayTao;

    @Column(name = "provider") // "local", "google", "facebook"
    private String provider;

    @Column(name = "provider_id") // ID từ Facebook/Google
    private String providerId;
}