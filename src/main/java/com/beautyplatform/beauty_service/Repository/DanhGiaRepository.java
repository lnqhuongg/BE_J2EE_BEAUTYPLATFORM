package com.beautyplatform.beauty_service.Repository;


import com.beautyplatform.beauty_service.Model.DanhGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, Integer> {
    @Query(value = """
        SELECT DISTINCT dg.*
        FROM danhgia dg
                 JOIN ctdatlich ctdl ON dg.maDL = ctdl.maDL
                 JOIN dichvu dv      ON ctdl.maDV = dv.maDV
        WHERE dv.maNCC = :maNCC
          AND (:diemDG = 0 OR dg.diemDG = :diemDG)
        """,
            nativeQuery = true)
    List<DanhGia> findAllByNccAndDiemDanhGia(@Param("maNCC") int maNCC,
                                             @Param("diemDG") int diemDG);
}
