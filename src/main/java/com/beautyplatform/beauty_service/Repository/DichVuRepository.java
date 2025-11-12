package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.DichVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface DichVuRepository extends JpaRepository<DichVu, Integer> {
    @Query("""
       SELECT dv FROM DichVu dv
       WHERE (:maLoaiDichVu IS NULL OR dv.loaiDichVu.maLDV = :maLoaiDichVu)
         AND (:maNhaCungCap IS NULL OR dv.nhaCungCap.maNCC = :maNhaCungCap)
         AND (:tenDV IS NULL OR LOWER(dv.tenDV) LIKE LOWER(CONCAT('%', :tenDV, '%')))
         AND (:minGia IS NULL OR dv.gia >= :minGia)
         AND (:maxGia IS NULL OR dv.gia <= :maxGia)
         AND (:trangThai IS NULL OR dv.trangThai = :trangThai)
""")
    Page<DichVu> filterDichVu(
            @Param("maLoaiDichVu") Integer maLoaiDichVu,
            @Param("maNhaCungCap") Integer maNhaCungCap,
            @Param("tenDV") String tenDV,
            @Param("minGia") BigDecimal minGia,
            @Param("maxGia") BigDecimal maxGia,
            @Param("trangThai") Integer trangThai,
            Pageable pageable
    );

}
