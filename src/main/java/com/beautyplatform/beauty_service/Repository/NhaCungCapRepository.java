package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.NhaCungCap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface NhaCungCapRepository extends JpaRepository<NhaCungCap, Integer> {

    @Query(
            value = """
            SELECT ncc FROM NhaCungCap ncc
            LEFT JOIN ncc.taiKhoan tk
            WHERE (:maNCC IS NULL OR ncc.maNCC = :maNCC)
              AND (:maLH IS NULL OR ncc.loaiHinhKinhDoanh.maLH = :maLH)
              AND (:tenNCC IS NULL OR LOWER(ncc.tenNCC) LIKE LOWER(CONCAT('%', :tenNCC, '%')))
              AND (:diaChi IS NULL OR LOWER(ncc.diaChi) LIKE LOWER(CONCAT('%', :diaChi, '%')))
              AND (:email IS NULL OR LOWER(tk.email) LIKE LOWER(CONCAT('%', :email, '%')))
        """,
            countQuery = """
            SELECT COUNT(ncc) FROM NhaCungCap ncc
            LEFT JOIN ncc.taiKhoan tk
            WHERE (:maNCC IS NULL OR ncc.maNCC = :maNCC)
              AND (:maLH IS NULL OR ncc.loaiHinhKinhDoanh.maLH = :maLH)
              AND (:tenNCC IS NULL OR LOWER(ncc.tenNCC) LIKE LOWER(CONCAT('%', :tenNCC, '%')))
              AND (:diaChi IS NULL OR LOWER(ncc.diaChi) LIKE LOWER(CONCAT('%', :diaChi, '%')))
              AND (:email IS NULL OR LOWER(tk.email) LIKE LOWER(CONCAT('%', :email, '%')))
        """
    )
    Page<NhaCungCap> searchWithPage(
            @Param("maNCC") Integer maNCC,
            @Param("maLH") Integer maLH,
            @Param("tenNCC") String tenNCC,
            @Param("diaChi") String diaChi,
            @Param("email") String email,
            Pageable pageable
    );

}