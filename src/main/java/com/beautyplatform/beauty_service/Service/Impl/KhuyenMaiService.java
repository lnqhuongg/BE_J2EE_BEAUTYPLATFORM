package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.DTO.KhuyenMaiDTO.KhuyenMaiDTO;
import com.beautyplatform.beauty_service.Mapper.KhuyenMaiMapper;
import com.beautyplatform.beauty_service.Model.KhuyenMai;
import com.beautyplatform.beauty_service.Model.NhaCungCap;
import com.beautyplatform.beauty_service.Repository.KhuyenMaiRepository;
import com.beautyplatform.beauty_service.Repository.NhaCungCapRepository;
import com.beautyplatform.beauty_service.Service.Interface.IKhuyenMaiService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KhuyenMaiService implements IKhuyenMaiService {

    @Autowired
    private KhuyenMaiRepository repository;

    @Autowired
    private NhaCungCapRepository nhaCungCapRepository;

    //getall
    @Override
    public Optional<List<KhuyenMaiDTO>> getAll(){
        try{
            List<KhuyenMai> listKMEntity = repository.findAll();

            List<KhuyenMaiDTO> dtoList = listKMEntity.stream()
                    .map(KhuyenMaiMapper::toDTO)
                    .toList();
            return Optional.of(dtoList);
        }catch(Exception e) {
            System.err.println("Lỗi khi lấy danh sách khuyến mãi: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<KhuyenMaiDTO> getByKhuyenMaiId(int maKM){
        try{
            KhuyenMai khuyenMai = repository.findById(maKM)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khuyến mãi: "+maKM));
            return Optional.of(KhuyenMaiMapper.toDTO(khuyenMai));
        }catch(Exception e){
            System.err.println("Lỗi: " + e.getMessage() + "khi tìm khuyến mãi có mã: " + maKM);
            return Optional.empty();
        }
    }

    @Override
    public  Optional<KhuyenMaiDTO> add(KhuyenMaiDTO khuyenMaiDTO) {
        NhaCungCap nhaCungCap = nhaCungCapRepository.findById(khuyenMaiDTO.getMaNCC())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhà cung cấp"));
        KhuyenMai km = KhuyenMaiMapper.toEntity(khuyenMaiDTO, nhaCungCap);
        km.setTrangThai(1);
        KhuyenMai saveKM = repository.save(km);
        return Optional.of(KhuyenMaiMapper.toDTO(saveKM));
    }

    @Override
    public Optional<KhuyenMaiDTO> update(KhuyenMaiDTO khuyenMaiDTO){
        KhuyenMai existing = repository.findById(khuyenMaiDTO.getMaKM())
                .orElse(null);
        if(existing == null){
            return Optional.empty();
        }

        NhaCungCap nhaCungCap = nhaCungCapRepository.findById(khuyenMaiDTO.getMaNCC())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhà cung cấp"));
        existing.setNhaCungCap(nhaCungCap);
        existing.setMoTa(khuyenMaiDTO.getMoTa());
        existing.setPhanTram(khuyenMaiDTO.getPhanTram());
        existing.setNgayBatDau(khuyenMaiDTO.getNgayBatDau());
        existing.setNgayKetThuc(khuyenMaiDTO.getNgayKetThuc());
        existing.setTrangThai(khuyenMaiDTO.getTrangThai());

        KhuyenMai saveKM = repository.save(existing);
        return Optional.of(KhuyenMaiMapper.toDTO(saveKM));
    }
    @Override
    public Optional<KhuyenMaiDTO> delete(KhuyenMaiDTO khuyenMaiDTO){
        return Optional.of(khuyenMaiDTO);
    }
}
