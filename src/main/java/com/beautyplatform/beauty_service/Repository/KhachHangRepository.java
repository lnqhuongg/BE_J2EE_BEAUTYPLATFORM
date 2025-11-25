package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.KhachHang;
import com.beautyplatform.beauty_service.Model.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    boolean existsBySdt(String sdt);
    // Tìm kiếm theo 1 keyword giống LoaiDichVu, có phân trang
    @Query("""
                SELECT kh
                FROM KhachHang kh
                LEFT JOIN kh.taiKhoan tk
                WHERE (
                    :keyword IS NULL
                    OR LOWER(TRIM(kh.hoTen)) LIKE LOWER(CONCAT('%', TRIM(:keyword), '%'))
                    OR kh.sdt LIKE CONCAT('%', :keyword, '%')
                    OR LOWER(tk.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
                )
            """)
    Page<KhachHang> searchWithPage(
            @Param("keyword") String keyword,
            Pageable pageable
    );
    Optional<KhachHang> findByTaiKhoan(TaiKhoan taiKhoan);
}
