package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.LoaiDichVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiDichVuRepository extends JpaRepository<LoaiDichVu, Integer> {

    @Query("""
        SELECT ldv FROM LoaiDichVu ldv
        WHERE (:tenLDV IS NULL OR LOWER(ldv.tenLDV) LIKE LOWER(CONCAT('%', :tenLDV, '%')))
          AND (:trangThai IS NULL OR ldv.trangThai = :trangThai)
    """)
    Page<LoaiDichVu> filterLoaiDichVu(
            @Param("tenLDV") String tenLDV,
            @Param("trangThai") Integer trangThai,
            Pageable pageable
    );
}
