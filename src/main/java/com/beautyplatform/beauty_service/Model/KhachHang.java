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

@Table(name = "khachhanng")
public class KhachHang {
    @Id
    @Column(name = "maKH")
    private int maKH;

    @ManyToOne
    @JoinColumn(name = "maTK", nullable = false)
    private TaiKhoan taiKhoan;

    @Column(name = "hoten", nullable = false)
    private String hoTen;

    @Column(name = "gioitinh", nullable = false)
    private int gioiTinh;

    @Column(name = " ngaysinh", nullable = false)
    private LocalDateTime ngaySinh;

    @Column(name = " sdt", nullable = false)
    private String sdt;

    @Column(name = " diemthuong", nullable = false)
    private int diemThuong;
}
