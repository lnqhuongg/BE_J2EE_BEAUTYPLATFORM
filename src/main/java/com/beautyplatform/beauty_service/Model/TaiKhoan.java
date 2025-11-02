package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "maTK")
    private int maTK;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "matkhau", nullable = false)
    private String matKhau;

    @Column(name = "loaiTK", nullable = false)
    private int loaiTK;

    @Column(name = " ngaytao", nullable = false)
    private LocalDateTime ngayTao;

    @Column(name = "trangthai", nullable = false)
    private int trangThai;
}
