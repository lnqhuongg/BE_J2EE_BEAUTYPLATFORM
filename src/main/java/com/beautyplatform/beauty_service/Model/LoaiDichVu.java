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

@Table(name = "loaidichvu")
public class LoaiDichVu {
    @Id
    @Column(name = "maLDV")
    private int maLDV;

    @Column(name = "tenLDV", nullable = false)
    private String tenLDV;

    @Column(name = "trangthai", nullable = false)
    private int trangThai;
}
