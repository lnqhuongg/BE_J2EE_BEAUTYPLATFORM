package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ctdatlich")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CTDatLich {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maCTDL")
    private int maCTDL;

    @ManyToOne
    @JoinColumn(name = "maDL", nullable = false)
    private DatLich datLich;

    @ManyToOne
    @JoinColumn(name = "maDV", nullable = false)
    private DichVu dichVu;

    @ManyToOne
    @JoinColumn(name = "maNV", nullable = false)
    private NhanVien nhanVien;

    @Column(name = "thoigianbatdau", nullable = false)
    private java.sql.Time thoiGianBatDau;

    @Column(name = "thoigianketthuc", nullable = false)
    private java.sql.Time thoiGianKetThuc;

    @Column(name = "gia", nullable = false)
    private double gia;
}
