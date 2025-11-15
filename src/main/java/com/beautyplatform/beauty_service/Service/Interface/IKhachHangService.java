package com.beautyplatform.beauty_service.Service.Interface;

import com.beautyplatform.beauty_service.DTO.KhachHangDTO.KhachHangDTO;
import com.beautyplatform.beauty_service.DTO.KhachHangDTO.TimKiemKhachHangDTO;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IKhachHangService {
    //tim theo id
    Optional<KhachHangDTO> getByKhachHangId(int id);
    //getall
    Page<KhachHangDTO> getAll(Pageable pageable);
    //sua
    Optional<KhachHangDTO> update(KhachHangDTO khachHangDTO);
    //them
    Optional<KhachHangDTO> add(KhachHangDTO khachHangDTO);
    //    //xoa
    //      Optional<KhachHangDTO> delete(KhachHangDTO KhachHangDTO);
    //ktSDT
    boolean isExistPhoneNumber(String sdt);
    Page<KhachHangDTO> searchWithPage(String keyword, Pageable pageable);
}