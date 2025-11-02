package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "maLH")
    private int maLoaiHinh;

    @Column(name = "tenLH", nullable = false)
    private String tenLoaiHinh;

    @Column(name = "trangthai", nullable = false)
    private int trangThai;
}
