package com.beautyplatform.beauty_service.DTO.KhuyenMaiDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class TimKiemKhuyenMaiDTO {
    private int maKM;
    private int maNCC;
    private BigDecimal phanTram;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate ngayBatDau;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate ngayKetThuc;
}
