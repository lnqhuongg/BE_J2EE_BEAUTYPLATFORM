package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

@Table(name = "loaidichvu")
public class LoaiDichVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maLDV")
    private int maLDV;

    @Column(name = "tenLDV", nullable = false)
    private String tenLDV;

    @Column(name = "trangthai", nullable = false)
    private int trangThai;
}
