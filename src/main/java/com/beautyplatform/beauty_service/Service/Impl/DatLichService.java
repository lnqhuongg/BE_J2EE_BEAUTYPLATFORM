package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.DTO.DatLichDTO.CTDatLichDTO;
import com.beautyplatform.beauty_service.DTO.DatLichDTO.DatLichDTO;
import com.beautyplatform.beauty_service.DTO.DatLichDTO.DatLichResponseDTO;
import com.beautyplatform.beauty_service.Mapper.CTDatLichMapper;
import com.beautyplatform.beauty_service.Mapper.DatLichMapper;
import com.beautyplatform.beauty_service.Mapper.DatLichResponseMapper;
import com.beautyplatform.beauty_service.Model.*;
import com.beautyplatform.beauty_service.Repository.*;
import com.beautyplatform.beauty_service.Service.Interface.IDatLichService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DatLichService implements IDatLichService {

    @Autowired
    private NhaCungCapGioLamViecRepository gioLamViecRepository;

    @Autowired
    private CTDatLichRepository ctDatLichRepository;

    @Autowired
    private DatLichRepository datLichRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private DichVuRepository dichVuRepository;

    @Override
    public List<LocalTime> getAvailableTimes(
            int maNCC,
            int maNV,
            LocalDate ngay,
            int giodichvu
    ) {
        int thu = ngay.getDayOfWeek().getValue();
        if (thu == 7) thu = 8;

        NhaCungCapGioLamViec caLam =
                gioLamViecRepository
                        .findByNhaCungCap_MaNCCAndNgayTrongTuan(maNCC, thu)
                        .stream().findFirst().orElse(null);

        if (caLam == null)
            return Collections.emptyList();

        LocalTime moCua = caLam.getGioMoCua();
        LocalTime dongCua = caLam.getGioDongCua();

        List<CTDatLich> busyList =
                ctDatLichRepository.findBusyTimeByNhanVienAndDate(maNV, ngay);

        List<LocalTime> result = new ArrayList<>();
        LocalTime cursor = moCua;

        while (cursor.plusMinutes(giodichvu).isBefore(dongCua.plusSeconds(1))) {
            LocalTime start = cursor;
            LocalTime end = cursor.plusMinutes(giodichvu);

            boolean isBusy = busyList.stream().anyMatch(ct ->
                    timeOverlap(
                            start, end,
                            ct.getThoiGianBatDau(),
                            ct.getThoiGianKetThuc()
                    )
            );

            if (!isBusy) {
                result.add(start);
            }

            cursor = cursor.plusMinutes(90);
        }

        return result;
    }

    @Override
    public List<LocalDate> getValidDates(int maNCC) {
        List<NhaCungCapGioLamViec> list = gioLamViecRepository.findByNhaCungCap_MaNCC(maNCC);

        Set<Integer> workingDays = list.stream()
                .map(NhaCungCapGioLamViec::getNgayTrongTuan)
                .collect(Collectors.toSet());

        List<LocalDate> validDates = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < 10; i++) {
            LocalDate day = today.plusDays(i);
            int thu = day.getDayOfWeek().getValue();

            if (workingDays.contains(thu)) {
                validDates.add(day);
            }
        }
        return validDates;
    }

    private boolean timeOverlap(LocalTime s1, LocalTime e1, LocalTime s2, LocalTime e2) {
        return !e1.isBefore(s2) && !e2.isBefore(s1);
    }

    @Override
    public Optional<DatLichDTO> add(DatLichDTO dto) {
        try {
            KhachHang kh = khachHangRepository.findById(dto.getMaKH())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khách hàng"));

            DatLich entity = DatLichMapper.toEntity(dto, kh);
            DatLich saved = datLichRepository.save(entity);

            return Optional.of(DatLichMapper.toDTO(saved));
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm đặt lịch: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<CTDatLichDTO> addCTDatLichList(int maDL, List<CTDatLichDTO> list) {
        DatLich datLich = datLichRepository.findById(maDL)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy đặt lịch!"));

        List<CTDatLich> entities = new ArrayList<>();

        for (CTDatLichDTO dto : list) {
            DichVu dv = dichVuRepository.findById(dto.getMaDV())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy dịch vụ"));

            NhanVien nv = nhanVienRepository.findById(dto.getMaNV())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhân viên"));

            CTDatLich entity = CTDatLichMapper.toEntity(dto, datLich, dv, nv);
            entities.add(entity);
        }

        List<CTDatLich> saved = ctDatLichRepository.saveAll(entities);

        return saved.stream()
                .map(CTDatLichMapper::toDTO)
                .toList();
    }

    @Override
    public List<DatLichResponseDTO> getByKhachHang(int maKH) {
        List<DatLich> datLichList = datLichRepository.findByKhachHangWithDetails(maKH);

        return datLichList.stream()
                .map(dl -> {
                    List<CTDatLich> chiTiet = ctDatLichRepository.findByDatLichWithDetails(dl.getMaDL());
                    return DatLichResponseMapper.toResponseDTO(dl, chiTiet);
                })
                .toList();
    }

    @Override
    public List<DatLichResponseDTO> getByKhachHangAndTrangThai(int maKH, int trangThai) {
        List<DatLich> datLichList = datLichRepository
                .findByKhachHang_MaKHAndTrangThaiOrderByNgayTaoDesc(maKH, trangThai);

        return datLichList.stream()
                .map(dl -> {
                    List<CTDatLich> chiTiet = ctDatLichRepository.findByDatLichWithDetails(dl.getMaDL());
                    return DatLichResponseMapper.toResponseDTO(dl, chiTiet);
                })
                .toList();
    }

    @Override
    public Optional<DatLichResponseDTO> getById(int maDL) {
        Optional<DatLich> datLich = datLichRepository.findById(maDL);

        if (datLich.isEmpty()) {
            return Optional.empty();
        }

        List<CTDatLich> chiTiet = ctDatLichRepository.findByDatLichWithDetails(maDL);
        return Optional.of(DatLichResponseMapper.toResponseDTO(datLich.get(), chiTiet));
    }
}