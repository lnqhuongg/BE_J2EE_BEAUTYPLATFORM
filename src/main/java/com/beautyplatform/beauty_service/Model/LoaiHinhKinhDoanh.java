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
    private int maLH;

    @Column(name = "tenLH", nullable = false)
    private String tenLH;

    @Column(name = "trangthai", nullable = false)
    private int trangThai;
}
