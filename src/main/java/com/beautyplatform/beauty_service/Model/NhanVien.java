package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nhanvien")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maNV")
    private int maNV;

    @ManyToOne
    @JoinColumn(name = "maNCC", nullable = false)
    private NhaCungCap nhaCungCap;

    @Column(name = "hoten", nullable = false)
    private String hoTen;

    @Column(name = "sdt", nullable = false)
    private String sdt;

    @Column(name = "gioitinh", nullable = false)
    private int gioiTinh; // 0 = Nữ, 1 = Nam

    @Column(name = "hinhanh", nullable = false)
    private String hinhAnh;

    @Column(name = "trangthai", nullable = false)
    private int trangThai;
}
