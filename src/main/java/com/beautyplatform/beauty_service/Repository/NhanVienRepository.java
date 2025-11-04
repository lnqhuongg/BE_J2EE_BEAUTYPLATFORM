package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {

    @Query("SELECT nv FROM NhanVien nv " +
            "WHERE (:maNV IS NULL OR nv.maNV = :maNV) " +
            "AND (:maNCC IS NULL OR nv.nhaCungCap.maNCC = :maNCC) " +
            "AND (:hoTen IS NULL OR nv.hoTen LIKE %:hoTen%) " +
            "AND (:gioiTinh IS NULL OR nv.gioiTinh = :gioiTinh) " +
            "AND (:trangThai IS NULL OR nv.trangThai = :trangThai)")
    List<NhanVien> search(
            @Param("maNV") Integer maNV,
            @Param("maNCC") Integer maNCC,
            @Param("hoTen") String hoTen,
            @Param("gioiTinh") Integer gioiTinh,
            @Param("trangThai") Integer trangThai
    );
}
