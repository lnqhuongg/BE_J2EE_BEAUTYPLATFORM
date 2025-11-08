package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.DTO.KhachHangDTO.KhachHangDTO;
import com.beautyplatform.beauty_service.DTO.KhachHangDTO.TimKiemKhachHangDTO;
import com.beautyplatform.beauty_service.Mapper.KhachHangMapper;
import com.beautyplatform.beauty_service.Model.KhachHang;
import com.beautyplatform.beauty_service.Model.TaiKhoan;
import com.beautyplatform.beauty_service.Repository.KhachHangRepository;
import com.beautyplatform.beauty_service.Repository.TaiKhoanRepository;
import com.beautyplatform.beauty_service.Service.Interface.IKhachHangService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KhachHangService implements IKhachHangService {

    @Autowired
    private KhachHangRepository repository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    //getall
    @Override
    public Optional<List<KhachHangDTO>> getAll(){
        try{
            List<KhachHang> listKHEntity = repository.findAll();

            List<KhachHangDTO> dtoList = listKHEntity.stream()
                    .map(KhachHangMapper::toDTO)
                    .toList();
            return Optional.of(dtoList);
        }catch(Exception e) {
            System.err.println("Lỗi khi lấy danh sách khuyến mãi: " + e.getMessage());
            return Optional.empty();
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
