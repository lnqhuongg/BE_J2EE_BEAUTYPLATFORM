package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.KhachHang;
import com.beautyplatform.beauty_service.Model.NhaCungCap;
import com.beautyplatform.beauty_service.Model.TaiKhoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhaCungCapRepository extends JpaRepository<NhaCungCap, Integer> {

    @Query("SELECT ncc FROM NhaCungCap ncc " +
            "WHERE (:maNCC IS NULL OR ncc.maNCC = :maNCC) " +
            "AND (:maTK IS NULL OR ncc.taiKhoan.maTK = :maTK) " +
            "AND (:maLH IS NULL OR ncc.loaiHinhKinhDoanh.maLH = :maLH) " +
            "AND (:tenNCC IS NULL OR LOWER(ncc.tenNCC) LIKE LOWER(CONCAT('%', :tenNCC, '%'))) " +
            "AND (:diaChi IS NULL OR LOWER(ncc.diaChi) LIKE LOWER(CONCAT('%', :diaChi, '%')))")
    Page<NhaCungCap> searchWithPage(
            @Param("maNCC") Integer maNCC,
            @Param("maTK") Integer maTK,
            @Param("maLH") Integer maLH,
            @Param("tenNCC") String tenNCC,
            @Param("diaChi") String diaChi,
            Pageable pageable
    );
    Optional<NhaCungCap> findByTaiKhoan(TaiKhoan taiKhoan);
}