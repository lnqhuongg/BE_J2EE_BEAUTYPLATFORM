package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.ChucVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChucVuRepository extends JpaRepository<ChucVu, Integer> {

    @Query("SELECT cv FROM ChucVu cv " +
            "WHERE (:maCV IS NULL OR cv.maCV = :maCV) " +
            "AND (:maNCC IS NULL OR cv.nhaCungCap.maNCC = :maNCC) " +
            "AND (:tenCV IS NULL OR cv.tenCV LIKE %:tenCV%) " +
            "AND (:trangThai IS NULL OR cv.trangThai = :trangThai)")
    List<ChucVu> search(
            @Param("maCV") Integer maCV,
            @Param("maNCC") Integer maNCC,
            @Param("tenCV") String tenCV,
            @Param("trangThai") Integer trangThai
    );
}
