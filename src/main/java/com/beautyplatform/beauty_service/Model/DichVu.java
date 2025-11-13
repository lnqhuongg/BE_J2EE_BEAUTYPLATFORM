package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

@Table(name = "dichvu")
public class DichVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maDV")
    private Integer maDV;

    @ManyToOne
    @JoinColumn(name = "maLDV", nullable = false)
    private LoaiDichVu loaiDichVu;

    @ManyToOne
    @JoinColumn(name = "maNCC", nullable = false)
    private NhaCungCap nhaCungCap;

    @ManyToOne
    @JoinColumn(name = "maKM", nullable = true)
    private KhuyenMai khuyenMai;

    @Column(name = "tenDV", nullable = false)
    private String tenDV;

    @Column(name = "mota", nullable = false)
    private String moTa;

    @Column(name = "gia", nullable = false)
    private BigDecimal gia;

    @Column(name = "thoiluong", nullable = false)
    private int thoiLuong;

    @Column(name = "trangthai", nullable = false)
    private int trangThai;
}
