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

@Table(name = "datlich")
public class DatLich {
    @Id
    @Column(name = "maDL")
    private int maDL;

    @ManyToOne
    @JoinColumn(name = "maKH", nullable = false)
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "maDV", nullable = false)
    private DichVu dichVu;

    @ManyToOne
    @JoinColumn(name = "maNV", nullable = false)
    private NhanVien nhanVien;

    @Column(name = "ngaygiohen", nullable = false)
    private LocalDateTime ngayGioHen;

    @Column(name = "ghichu", nullable = false)
    private String ghiChu;

    @Column(name = "ngaytao", nullable = false)
    private LocalDateTime ngayTao;

    @Column(name = "trangthai", nullable = false)
    private int trangThai;
}
