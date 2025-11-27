package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "datlich")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DatLich {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maDL")
    private int maDL;

    @ManyToOne
    @JoinColumn(name = "maKH", nullable = false)
    private KhachHang khachHang;

    @Column(name = "tongthoigian", nullable = false)
    private int tongThoiGian;

    @Column(name = "tongtien", nullable = false, precision = 12, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "trangthai", nullable = false)
    private int trangThai;

    @Column(name = "ngaytao", nullable = false)
    private LocalDate ngayTao;
}
