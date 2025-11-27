package com.beautyplatform.beauty_service.Service.Interface;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IDatLichService {
    //  giờ dịch vụ là số phút cần cho dịch vụ
    List<LocalTime> getAvailableTimes(int maNCC, int maNV, LocalDate ngay, int giodichvu);
}
