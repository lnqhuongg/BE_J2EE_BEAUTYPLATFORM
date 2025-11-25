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

@Table(name = "danhgia")
public class DanhGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maDG")
    private int maDG;

    @ManyToOne
    @JoinColumn(name = "maDL", nullable = false)
    private DatLich datLich;

    @Column(name = "diemDG", nullable = false)
    private int diemDanhGia;

    @Column(name = "noidung", nullable = false)
    private String noiDung;

    @Column(name = "ngaydanhgia", nullable = false)
    private LocalDate ngayDanhGia;
}
