package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

@Table(name = "thanhtoan")
public class ThanhToan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maTT")
    private int maTT;

    @ManyToOne
    @JoinColumn(name = "maDL", nullable = false)
    private DatLich datLich;

    @Column(name = "phuongthuc", nullable = false)
    private int phuongThuc;

    @Column(name = "sotien", nullable = false)
    private BigDecimal soTien;

    @Column(name = "trangthai", nullable = false)
    private int trangThai;

    @Column(name = "ngaythanhtoan", nullable = false)
    private LocalDateTime ngayThanhToan;
}
