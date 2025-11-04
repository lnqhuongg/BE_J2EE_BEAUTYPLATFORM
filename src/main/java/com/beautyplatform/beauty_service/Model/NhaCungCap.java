package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "nhacungcap")
public class NhaCungCap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maNCC")
    private int maNCC;

    @ManyToOne
    @JoinColumn(name = "maTK", nullable = false)
    private TaiKhoan taiKhoan;

    @ManyToOne
    @JoinColumn(name = "maLH", nullable = false)
    private LoaiHinhKinhDoanh loaiHinhKinhDoanh;

    @Column(name = "tenNCC", nullable = false)
    private String tenNCC;

    @Column(name = "gioithieu", nullable = false)
    private String gioiThieu;

    @Column(name = "diachi", nullable = false)
    private String diaChi;

    @Column(name = "trangthai", nullable = false)
    private int trangThai;
}
