package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.DichVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DichVuRepository extends JpaRepository<DichVu, Integer> {

    @Query(
            value = """
            SELECT d FROM DichVu d
            WHERE (:maDV IS NULL OR d.maDV = :maDV)
              AND (:maLDV IS NULL OR d.loaiDichVu.maLDV = :maLDV)
              AND (:tenDV IS NULL OR LOWER(d.tenDV) LIKE LOWER(CONCAT('%', :tenDV, '%')))
              AND (:thoiluong IS NULL OR d.thoiLuong = :thoiluong)
        """,
            countQuery = """
            SELECT COUNT(d) FROM DichVu d
            WHERE (:maDV IS NULL OR d.maDV = :maDV)
              AND (:maLDV IS NULL OR d.loaiDichVu.maLDV = :maLDV)
              AND (:tenDV IS NULL OR LOWER(d.tenDV) LIKE LOWER(CONCAT('%', :tenDV, '%')))
              AND (:thoiluong IS NULL OR d.thoiLuong = :thoiluong)
        """
    )
    Page<DichVu> searchWithPage(
            @Param("maDV") Integer maDV,
            @Param("maLDV") Integer maLDV,
            @Param("tenDV") String tenDV,
            @Param("thoiluong") Integer thoiluong,
            Pageable pageable
    );

    List<DichVu> findByLoaiDichVu_MaLDVAndNhaCungCap_MaNCC(int maLDV, int maNCC);
}
