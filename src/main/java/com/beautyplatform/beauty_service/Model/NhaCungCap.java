package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

@Table(name = "nhacungcap")
public class NhaCungCap {
    @Id
    @Column(name = "maNCC")
    private int maNCC;

    @ManyToOne
    @JoinColumn(name = "maTK", nullable = false)
    private TaiKhoan taiKhoan;

    @Column(name = "tenNCC", nullable = false)
    private String tenNCC;

    @ManyToOne
    @JoinColumn(name = "maLH", nullable = false)
    private LoaiHinhKinhDoanh loaiHinhKinhDoanh;

    @Column(name = "gioithieu", nullable = false)
    private String gioiThieu;

    @Column(name = "diachi", nullable = false)
    private String diaChi;

    @Column(name = "giomocua", nullable = false)
    private LocalTime gioMoCua;

    @Column(name = "giodongcua", nullable = false)
    private LocalTime gioDongCua;

    @Column(name = "trangthai", nullable = false)
    private int trangThai;
}
