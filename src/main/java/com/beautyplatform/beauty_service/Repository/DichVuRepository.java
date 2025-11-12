package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.DichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DichVuRepository extends JpaRepository<DichVu, Integer> {

    @Query("SELECT d FROM DichVu d " +
            "WHERE (:maDV IS NULL OR d.maDV = :maDV) " +
            "AND (:maLDV IS NULL OR d.loaiDichVu.maLDV = :maLDV) " +
            "AND (:maNCC IS NULL OR d.nhaCungCap.maNCC = :maNCC) " +
            "AND (:tenDV IS NULL OR d.tenDV LIKE %:tenDV%)")
    public List<DichVu> search(
            @Param("maDV") int maDV,
            @Param("maLoaiDV") int maLoaiDV,
            @Param("maNCC") int maNCC,
            @Param("tenDV") String tenDV
    );
}
