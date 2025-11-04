package com.beautyplatform.beauty_service.Service.Interface;

import com.beautyplatform.beauty_service.DTO.KhuyenMaiDTO.KhuyenMaiDTO;
import com.beautyplatform.beauty_service.DTO.KhuyenMaiDTO.TimKiemKhuyenMaiDTO;

import java.util.List;
import java.util.Optional;

public interface IKhuyenMaiService {
    //thêm
    public Optional<KhuyenMaiDTO> add(KhuyenMaiDTO khuyenMaiDTO);
    //sửa
    public Optional<KhuyenMaiDTO> update(KhuyenMaiDTO khuyenMaiDTO);
    //xóa
    public Optional<KhuyenMaiDTO> delete(KhuyenMaiDTO khuyenMaiDTO);
    //xem ds
    public Optional<List<KhuyenMaiDTO>> getAll();
    //lấy theo id (dùng khi nhấn vào modal chỉnh sửa trên giao diện)
    public Optional<KhuyenMaiDTO> getByKhuyenMaiId(int id);
    //tìm kiếm + lọc
//    public  Optional<List<KhuyenMaiDTO>> search(TimKiemKhuyenMaiDTO khuyenMaiDTO);

}
