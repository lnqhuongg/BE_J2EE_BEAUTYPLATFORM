package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

@Table(name = "khuyenmai")
public class KhuyenMai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maKM")
    private int maKM;

    @ManyToOne
    @JoinColumn(name = "maNCC", nullable = false)
    private NhaCungCap nhaCungCap;

    @Column(name = "mota", nullable = false)
    private String moTa;

    @Column(name = "phantram", nullable = false)
    private BigDecimal phanTram;

    @Column(name = "ngaybatdau", nullable = false)
    private LocalDate ngayBatDau;

    @Column(name = "ngayketthuc", nullable = false)
    private LocalDate ngayKetThuc;

    @Column(name = "trangthai", nullable = false)
    private int trangThai;
}
