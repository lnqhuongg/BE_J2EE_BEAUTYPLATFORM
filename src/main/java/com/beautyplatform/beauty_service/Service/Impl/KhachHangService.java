package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.DTO.KhachHangDTO.KhachHangDTO;
import com.beautyplatform.beauty_service.DTO.KhachHangDTO.TimKiemKhachHangDTO;
import com.beautyplatform.beauty_service.Helper.ApiResponse;
import com.beautyplatform.beauty_service.Mapper.KhachHangMapper;
import com.beautyplatform.beauty_service.Model.KhachHang;
import com.beautyplatform.beauty_service.Model.TaiKhoan;
import com.beautyplatform.beauty_service.Repository.KhachHangRepository;
import com.beautyplatform.beauty_service.Repository.TaiKhoanRepository;
import com.beautyplatform.beauty_service.Service.Interface.IKhachHangService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class KhachHangService implements IKhachHangService {

    @Autowired
    private KhachHangRepository repository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
//    Lọc
//    @Override
//    public Page<KhachHangDTO> getAllSearchWithPage(TimKiemKhachHangDTO timKiemKhachHangDTO, Pageable pageable) {
//        try {
//            Page<KhachHang> pageEntity = repository.searchWithPage(
//                    timKiemKhachHangDTO.getMaKH(),
//                    timKiemKhachHangDTO.getMaTK(),
//                    timKiemKhachHangDTO.getHoTen(),
//                    timKiemKhachHangDTO.getSdt(),
//
//                    pageable
//            );
//            return pageEntity.map(KhachHangMapper::toDTO);
//        }catch (Exception e) {
//            System.err.println("Lỗi khi tìm kiếm có phân trang" + e.getMessage());
//            return Page.empty(pageable);
//        }
//    }
    //lấy toàn bộ khách hàng
    @Override
    public Page<KhachHangDTO> getAll(Pageable pageable) {
        try {
            Page<KhachHang> pageEntity = repository.findAll(pageable);
            return pageEntity.map(KhachHangMapper::toDTO);
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách khách hàng: " + e.getMessage());
            return Page.empty(pageable);
        }
    }

    @Override
    public Page<KhachHangDTO> searchWithPage(String keyword, Pageable pageable) {
        try {
            Page<KhachHang> pageEntity = repository.searchWithPage(keyword, pageable);
            return pageEntity.map(KhachHangMapper::toDTO);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm kiếm khách hàng có phân trang: " + e.getMessage());
            return Page.empty(pageable);
        }
    }

    //theo id
    @Override
    public Optional<KhachHangDTO> getByKhachHangId(int maKH){
        try{
            KhachHang khachHang = repository.findById(maKH)
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khách hàng: "+maKH));
            return Optional.of(KhachHangMapper.toDTO(khachHang));
        }catch(Exception e){
            System.err.println("Lỗi: " + e.getMessage() + "khi tìm khách hàng có mã: " + maKH);
            return Optional.empty();
        }
    }
    //sua
    @Override
    public Optional<KhachHangDTO> update(KhachHangDTO khachHangDTO){
        KhachHang existing = repository.findById(khachHangDTO.getMaKH())
                .orElse(null);
        if(existing == null){
            return Optional.empty();
        }
        existing.setHoTen(khachHangDTO.getHoTen());
        existing.setGioiTinh(khachHangDTO.getGioiTinh());
        existing.setNgaySinh(khachHangDTO.getNgaySinh());
        existing.setSdt(khachHangDTO.getSdt());
        existing.setHinhAnh(khachHangDTO.getHinhAnh());

        KhachHang saveKH = repository.save(existing);
        return Optional.of(KhachHangMapper.toDTO(saveKH));
    }
    //them
    @Override
    public Optional<KhachHangDTO> add(KhachHangDTO khachHangDTO){
        try{
            // Validate TaiKhoan
            TaiKhoan taiKhoan = taiKhoanRepository.findById(khachHangDTO.getMaTK())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tài khoản"));
            KhachHang kh = KhachHangMapper.toEntity(khachHangDTO, taiKhoan);
            KhachHang saveKH = repository.save(kh);
            return Optional.of(KhachHangMapper.toDTO(saveKH));
        }catch (Exception e){
            System.err.println("Lỗi khi thêm khách hàng: ");
            return Optional.empty();
        }
    }
    //KTSDT
    public boolean isExistPhoneNumber(String sdt) {
        return repository.existsBySdt(sdt);
    }

    //search
    public Optional<List<KhachHangDTO>> search(TimKiemKhachHangDTO timKiemKhachHangDTO){
        try{
            List<KhachHang>  entities = repository.search(
                    timKiemKhachHangDTO.getMaKH(),
                    timKiemKhachHangDTO.getMaTK(),
                    timKiemKhachHangDTO.getHoTen(),
                    timKiemKhachHangDTO.getSdt()
            );

            List<KhachHangDTO> dtoList = entities.stream()
                    .map(KhachHangMapper::toDTO)
                    .toList();
            return Optional.of(dtoList);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm kiếm khách hàng");
            return Optional.empty();
        }
    }
}
