package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.Model.CTDatLich;
import com.beautyplatform.beauty_service.Model.NhaCungCapGioLamViec;
import com.beautyplatform.beauty_service.Repository.CTDatLichRepository;
import com.beautyplatform.beauty_service.Repository.NhaCungCapGioLamViecRepository;
import com.beautyplatform.beauty_service.Service.Interface.IDatLichService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatLichService implements IDatLichService {

    @Autowired
    private NhaCungCapGioLamViecRepository gioLamViecRepository;

    @Autowired
    private CTDatLichRepository ctDatLichRepository;

    @Override
    public List<LocalTime> getAvailableTimes(
            int maNCC,
            int maNV,
            LocalDate ngay,
            int giodichvu // số phút cần cho dịch vụ
    ) {

        int thu = ngay.getDayOfWeek().getValue();
        if (thu == 7) thu = 8;

        // --- Lấy giờ mở cửa -> đóng cửa trong ngày đó ---
        NhaCungCapGioLamViec caLam =
                gioLamViecRepository.findByNhaCungCap_MaNCCAndNgayTrongTuan(maNCC, thu)
                        .stream().findFirst().orElse(null);

        if (caLam == null) return Collections.emptyList();

        LocalTime moCua = caLam.getGioMoCua();
        LocalTime dongCua = caLam.getGioDongCua();

        // --- Lấy danh sách giờ nhân viên bận ---
        List<CTDatLich> busyList =
                ctDatLichRepository.findBusyTimeByNhanVienAndDate(maNV, ngay);

        // Sinh timeline 30 phút / 1 giờ tùy bạn
        List<LocalTime> result = new ArrayList<>();

        LocalTime cursor = moCua;

        while (cursor.plusMinutes(giodichvu).isBefore(dongCua.plusSeconds(1))) {

            LocalTime start = cursor;
            LocalTime end = cursor.plusMinutes(giodichvu);

            boolean isBusy = busyList.stream().anyMatch(ct ->
                    timeOverlap(start, end, ct.getThoiGianBatDau(), ct.getThoiGianKetThuc())
            );

            if (!isBusy) {
                result.add(start);
            }

            cursor = cursor.plusMinutes(30); // mỗi slot 30 phút
        }

        return result;
    }

    private boolean timeOverlap(LocalTime s1, LocalTime e1, LocalTime s2, LocalTime e2) {
        return !e1.isBefore(s2) && !e2.isBefore(s1);
    }
}
