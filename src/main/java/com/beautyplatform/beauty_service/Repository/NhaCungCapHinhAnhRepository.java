package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.NhaCungCapHinhAnh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NhaCungCapHinhAnhRepository extends JpaRepository<NhaCungCapHinhAnh, Integer> {

    // Lấy tất cả hình ảnh của 1 NCC
    List<NhaCungCapHinhAnh> findByNhaCungCap_MaNCC(int maNCC);

    // Lấy ảnh chính của NCC
    Optional<NhaCungCapHinhAnh> findByNhaCungCap_MaNCCAndImageMain(int maNCC, String imageMain);
}