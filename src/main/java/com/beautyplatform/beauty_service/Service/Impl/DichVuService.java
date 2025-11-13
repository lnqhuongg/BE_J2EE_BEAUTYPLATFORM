package com.beautyplatform.beauty_service.Service.Impl;

import com.beautyplatform.beauty_service.DTO.DichVuDTO.DichVuDTO;
import com.beautyplatform.beauty_service.DTO.DichVuDTO.TimKiemDichVuDTO;
import com.beautyplatform.beauty_service.Mapper.DichVuMapper;
import com.beautyplatform.beauty_service.Mapper.LoaiDichVuMapper;
import com.beautyplatform.beauty_service.Model.DichVu;
import com.beautyplatform.beauty_service.Model.LoaiDichVu;
import com.beautyplatform.beauty_service.Model.NhaCungCap;
import com.beautyplatform.beauty_service.Repository.DichVuRepository;
import com.beautyplatform.beauty_service.Repository.LoaiDichVuRepository;
import com.beautyplatform.beauty_service.Repository.NhaCungCapRepository;
import com.beautyplatform.beauty_service.Service.Interface.IDichVuService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class DichVuService implements IDichVuService {

    @Autowired
    private DichVuRepository repository;

    @Autowired
    private LoaiDichVuRepository loaiDichVuRepository;

    @Autowired
    private NhaCungCapRepository nhaCungCapRepository;

    // lấy tất cả (hiển thị trên danh sách)
    // tránh xử dụng <List<DichVuDTO> (không có Optional) vì có thể trả về null, trường hợp truyền dữ liệu sai có thể bị lỗi tùm lum
    // nói chung là thống nhất trả về Optional (1 kiểu thôi) cho gọn với code nhanh hơn
    // lấy tất cả nếu ko có lọc, còn nếu có lọc thì lọc nhưng mà cuối cùng thì vẫn có phân trang
    @Override
    public Page<DichVuDTO> getAllAndSearchWithPage(TimKiemDichVuDTO timKiemDichVuDTO, Pageable pageable) {
        try {
            Page<DichVu> pageEntity = repository.searchWithPage(
                    timKiemDichVuDTO.getMaDV(),
                    timKiemDichVuDTO.getMaLDV(),
                    timKiemDichVuDTO.getTenDV(),
                    timKiemDichVuDTO.getThoiLuong(),
                    pageable
            );
            return pageEntity.map(DichVuMapper::toDTO);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm kiếm có phân trang: " + e.getMessage());
            return Page.empty(pageable);
        }
    }

    // lấy theo id (dùng khi nhấn vào modal chỉnh sửa trên giao diện)
    @Override
    public Optional<DichVuDTO> getByDichVuId(int maDV){
        try {
            // lấy danh sách entity từ database
            DichVu dichVu = repository.findById(maDV)
                    // mặc dù findById trong JPA trả về Optional<DichVu> findById, nhờ có dòng orElseThrow(() này mà nó hiểu được là
                    // bên trong Optional đó có kiểu DichVu nên biến dichVu vẫn là DichVu chứ ko phải là Optional nữa, ko hiểu in bóch
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy dịch vụ: " + maDV));
            // map danh sách vừa lấy sang DTO, dùng stream() có sẵn trong Spring Boot
            // trả về (gửi đến controller)
            return Optional.of(DichVuMapper.toDTO(dichVu));
        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage() + "khi tìm dịch vụ có mã: " + maDV);
            return Optional.empty();
        }
    }

    // thêm
    @Override
    public Optional<DichVuDTO> add(DichVuDTO dichVuDTO) {
        // nhận DTO từ controller -> map từ DTO sang Entity để lưu vào database
        LoaiDichVu loaiDichVu = loaiDichVuRepository.findById(dichVuDTO.getMaLDV())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy loại dịch vụ"));
        NhaCungCap nhaCungCap = nhaCungCapRepository.findById(dichVuDTO.getMaNCC())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhà cung cấp"));

        DichVu dv = DichVuMapper.toEntity(dichVuDTO, loaiDichVu, nhaCungCap);
        // cái nào có trạng thái thì mới tạo trạng thái luôn hoạt động nha, tạm thời cứ để vậy đi nha, tui xem r gửi mng cái
        // mô tả website chính thức nha
        dv.setTrangThai(1);
        // lưu vào database
        DichVu saveDV = repository.save(dv);
        // map 1 lần nữa trước khi đưa sang cho Controller
        return Optional.of(DichVuMapper.toDTO(saveDV));
    }

    // sửa
    @Override
    public Optional<DichVuDTO> update(DichVuDTO dichVuDTO){

        DichVu existing = repository.findById(dichVuDTO.getMaDV())
                .orElse(null);
        if (existing == null) {
            return Optional.empty();
        }

        // nhận DTO từ controller -> map từ DTO sang Entity để lưu vào database
        LoaiDichVu loaiDichVu = loaiDichVuRepository.findById(dichVuDTO.getMaLDV())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy loại dịch vụ"));
        NhaCungCap nhaCungCap = nhaCungCapRepository.findById(dichVuDTO.getMaNCC())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhà cung cấp"));

        // làm như thế này, ko sử dụng mapper như thêm được vì có thể mình chỉ sửa 1 số trường thôi không sửa tất cả, tránh
        // việc nếu không sửa các trường khác thì nó ko gửi cái giá trị null đến dto
        existing.setTenDV(dichVuDTO.getTenDV());
        existing.setMoTa(dichVuDTO.getMoTa());
        existing.setGia(dichVuDTO.getGia());
        existing.setThoiLuong(dichVuDTO.getThoiLuong());
        existing.setTrangThai(dichVuDTO.getTrangThai());
        existing.setLoaiDichVu(loaiDichVu);
        existing.setNhaCungCap(nhaCungCap);

        // lưu vào database
        DichVu saveDV = repository.save(existing);
        // map 1 lần nữa trước khi đưa sang cho Controller
        return Optional.of(DichVuMapper.toDTO(saveDV));
    }

    // xóa -- đang xem xét có nên cho xóa hay không
    @Override
    public Optional<DichVuDTO> delete(DichVuDTO dichVuDTO){
        return Optional.of(dichVuDTO);
    }
}
