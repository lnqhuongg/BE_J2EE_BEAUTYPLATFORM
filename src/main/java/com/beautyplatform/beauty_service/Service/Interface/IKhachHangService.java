package com.beautyplatform.beauty_service.Service.Interface;

import com.beautyplatform.beauty_service.DTO.KhachHangDTO.KhachHangDTO;
import com.beautyplatform.beauty_service.DTO.KhachHangDTO.TimKiemKhachHangDTO;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IKhachHangService {
    //tim theo id
    public Optional<KhachHangDTO> getByKhachHangId(int id);
    //getall
    public  Page<KhachHangDTO> getAll(Pageable pageable);
    //sua
    public Optional<KhachHangDTO> update(KhachHangDTO khachHangDTO);
    //them
    public Optional<KhachHangDTO> add(KhachHangDTO khachHangDTO);
//    //xoa
//    public  Optional<KhachHangDTO> delete(KhachHangDTO KhachHangDTO);
    //ktSDT
    public boolean isExistPhoneNumber(String sdt);
    //search
//    Optional<List<KhachHangDTO>> search(TimKiemKhachHangDTO timKiemKhachHangDTO);

//    Page<KhachHangDTO> getAllSearchWithPage(TimKiemKhachHangDTO timKiemKhachHangDTO, Pageable pageable);
    Page<KhachHangDTO> searchWithPage(String keyword, Pageable pageable);
}