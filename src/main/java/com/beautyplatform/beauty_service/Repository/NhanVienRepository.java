package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {

    @Query("""
                SELECT nv
                FROM NhanVien nv
                WHERE (
                    :keyword IS NULL
                    OR LOWER(TRIM(nv.hoTen)) LIKE LOWER(CONCAT('%', TRIM(:keyword), '%'))
                    OR nv.sdt LIKE CONCAT('%', :keyword, '%')
                )
            """)
    Page<NhanVien> searchWithPage(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    List<NhanVien> findByNhaCungCap_MaNCC(int maNCC);
}
