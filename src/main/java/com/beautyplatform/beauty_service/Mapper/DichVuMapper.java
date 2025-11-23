package com.beautyplatform.beauty_service.Mapper;

import com.beautyplatform.beauty_service.DTO.DichVuDTO.DichVuDTO;
import com.beautyplatform.beauty_service.DTO.DichVuDTO.DichVuResponseDTO;
import com.beautyplatform.beauty_service.DTO.KhuyenMaiDTO.KhuyenMaiDTO;
import com.beautyplatform.beauty_service.DTO.LoaiDichVuDTO.LoaiDichVuDTO;
import com.beautyplatform.beauty_service.DTO.NhaCungCapDTO.NhaCungCapDTO;
import com.beautyplatform.beauty_service.Model.DichVu;
import com.beautyplatform.beauty_service.Model.KhuyenMai;
import com.beautyplatform.beauty_service.Model.LoaiDichVu;
import com.beautyplatform.beauty_service.Model.NhaCungCap;

public class DichVuMapper {
    public static DichVu toEntity(DichVuDTO dto, LoaiDichVu ldv, NhaCungCap ncc, KhuyenMai km) {
        DichVu entity = new DichVu();
        entity.setMaDV(dto.getMaDV());
        entity.setTenDV(dto.getTenDV());
        entity.setMoTa(dto.getMoTa());
        entity.setGia(dto.getGia());
        entity.setThoiLuong(dto.getThoiLuong());
        entity.setTrangThai(dto.getTrangThai());
        entity.setLoaiDichVu(ldv);
        entity.setNhaCungCap(ncc);
        entity.setKhuyenMai(km);
        return entity;
    }

    public static DichVuResponseDTO toDTO(DichVu dichVu) {
        KhuyenMai km = dichVu.getKhuyenMai();
        KhuyenMaiDTO kmDTO = null;

        if (km != null) {
            kmDTO = new KhuyenMaiDTO(
                    km.getMaKM(),
                    km.getNhaCungCap().getMaNCC(),
                    km.getMoTa(),
                    km.getPhanTram(),
                    km.getNgayBatDau(),
                    km.getNgayKetThuc(),
                    km.getTrangThai()
            );
        }
        return DichVuResponseDTO.builder()
                .maDV(dichVu.getMaDV())
                .loaiDichVu(
                        new LoaiDichVuDTO(
                                dichVu.getLoaiDichVu().getMaLDV(),
                                dichVu.getLoaiDichVu().getTenLDV(),
                                dichVu.getLoaiDichVu().getTrangThai()
                        )
                )
                .nhaCungCap(
                        new NhaCungCapDTO(
                                dichVu.getNhaCungCap().getMaNCC(),
                                dichVu.getNhaCungCap().getTaiKhoan().getMaTK(),
                                dichVu.getNhaCungCap().getLoaiHinhKinhDoanh().getMaLH(),
                                dichVu.getNhaCungCap().getTenNCC(),
                                dichVu.getNhaCungCap().getGioiThieu(),
                                dichVu.getNhaCungCap().getDiaChi()
                        )
                )
                .khuyenMai(kmDTO)
                .tenDV(dichVu.getTenDV())
                .moTa(dichVu.getMoTa())
                .gia(dichVu.getGia())
                .thoiLuong(dichVu.getThoiLuong())
                .trangThai(dichVu.getTrangThai())
                .build();
    }
}
