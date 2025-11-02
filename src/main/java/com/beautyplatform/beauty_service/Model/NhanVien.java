package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

@Table(name = "nhanvien")
public class NhanVien {
    @Id
    @Column(name = "maNV")
    private int maNV;

    @ManyToOne
    @JoinColumn(name = "maNCC", nullable = false)
    private NhaCungCap nhaCungCap;

    @Column(name = "hoten", nullable = false)
    private String hoTen;

    @ManyToOne
    @JoinColumn(name = "chucvu", nullable = false)
    private ChucVu chucVu;

    @Column(name = "sdt", nullable = false)
    private String sdt;

    @Column(name = "trangthai", nullable = false)
    private int trangThai;
}
