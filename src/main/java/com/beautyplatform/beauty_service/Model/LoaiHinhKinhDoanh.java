package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

@Table(name = "loaihinh")
public class LoaiHinhKinhDoanh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maLH")
    private int maLoaiHinh;

    @Column(name = "tenLH", nullable = false)
    private String tenLoaiHinh;

    @Column(name = "trangthai", nullable = false)
    private int trangThai;
}
