package com.beautyplatform.beauty_service.Service.Interface;

import com.beautyplatform.beauty_service.DTO.DatLichDTO.CTDatLichDTO;
import com.beautyplatform.beauty_service.DTO.DatLichDTO.DatLichDTO;
import com.beautyplatform.beauty_service.DTO.DatLichDTO.DatLichResponseDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface IDatLichService {
    // Giờ dịch vụ là số phút cần cho dịch vụ
    List<LocalTime> getAvailableTimes(int maNCC, int maNV, LocalDate ngay, int giodichvu);

    List<LocalDate> getValidDates(int maNCC);

    Optional<DatLichDTO> add(DatLichDTO dto);

    List<CTDatLichDTO> addCTDatLichList(int maDL, List<CTDatLichDTO> list);

    // API mới cho khách hàng xem lịch hẹn
    List<DatLichResponseDTO> getByKhachHang(int maKH);

    List<DatLichResponseDTO> getByKhachHangAndTrangThai(int maKH, int trangThai);

    Optional<DatLichResponseDTO> getById(int maDL);
}