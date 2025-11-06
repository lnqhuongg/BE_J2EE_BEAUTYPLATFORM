package com.beautyplatform.beauty_service.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Entity
@Table(name = "nhacungcap_giolamviec")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NhaCungCapGioLamViec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "magiolamviec")
    private int maGioLamViec;

    @ManyToOne
    @JoinColumn(name = "maNCC", nullable = false)
    private NhaCungCap nhaCungCap;

    @Column(name = "ngaytrongtuan", nullable = false)
    private int ngayTrongTuan;

    @Column(name = "giomocua", nullable = false)
    private LocalTime gioMoCua;

    @Column(name = "giodongcua", nullable = false)
    private LocalTime gioDongCua;
}
