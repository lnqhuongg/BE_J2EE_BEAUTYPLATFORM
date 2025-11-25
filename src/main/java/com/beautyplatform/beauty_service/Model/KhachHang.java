package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

@Table(name = "khachhang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maKH")
    private int maKH;

    @OneToOne
    @JoinColumn(name = "maTK", nullable = false)
    private TaiKhoan taiKhoan;

    @Column(name = "hoten", nullable = false)
    private String hoTen;

    @Column(name = "gioitinh", nullable = true)
    private Integer gioiTinh;

    @Column(name = "ngaysinh", nullable = true)
    private LocalDate ngaySinh;

    @Column(name = "sdt", nullable = true)
    private String sdt;

    @Column(name = "hinhanh", nullable = false)
    private String hinhAnh;
}
