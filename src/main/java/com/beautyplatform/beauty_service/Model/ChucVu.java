package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

@Table(name = "chucvu")
public class ChucVu {
    @Id
    @Column(name = "maCV")
    private int maCV;

    @Column(name = "tenCV", nullable = false)
    private String tenCV;

    @ManyToOne
    @JoinColumn(name = "maNCC", nullable = false)
    private NhaCungCap nhaCungCap;

    @Column(name = "trangthai", nullable = false)
    private int trangThai;
}
