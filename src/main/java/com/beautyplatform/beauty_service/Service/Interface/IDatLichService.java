package com.beautyplatform.beauty_service.Service.Interface;

import com.beautyplatform.beauty_service.DTO.DatLichDTO.CTDatLichDTO;
import com.beautyplatform.beauty_service.DTO.DatLichDTO.DatLichDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface IDatLichService {
    //  giờ dịch vụ là số phút cần cho dịch vụ
    List<LocalTime> getAvailableTimes(int maNCC, int maNV, LocalDate ngay, int giodichvu);

    List<LocalDate> getValidDates(int maNCC);

    Optional<DatLichDTO> add(DatLichDTO dto);

    List<CTDatLichDTO> addCTDatLichList(int maDL, List<CTDatLichDTO> list);
}
