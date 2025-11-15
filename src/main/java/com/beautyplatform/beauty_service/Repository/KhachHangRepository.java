package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    boolean existsBySdt(String sdt);
// Lọc
//    // --- TÌM KIẾM CÓ PHÂN TRANG (Page<KhachHang>) ---
//    @Query(
//            value = """
//            SELECT kh FROM KhachHang kh
//            WHERE (:maKH IS NULL OR kh.maKH = :maKH)
//              AND (:maTK IS NULL OR kh.taiKhoan.maTK = :maTK)
//              AND (:hoTen IS NULL OR LOWER(kh.hoTen) LIKE LOWER(CONCAT('%', :hoTen, '%')))
//              AND (:sdt IS NULL OR kh.sdt LIKE CONCAT('%', :sdt, '%'))
//        """,
//            countQuery = """
//            SELECT COUNT(kh) FROM KhachHang kh
//            WHERE (:maKH IS NULL OR kh.maKH = :maKH)
//              AND (:maTK IS NULL OR kh.taiKhoan.maTK = :maTK)
//              AND (:hoTen IS NULL OR LOWER(kh.hoTen) LIKE LOWER(CONCAT('%', :hoTen, '%')))
//              AND (:sdt IS NULL OR kh.sdt LIKE CONCAT('%', :sdt, '%'))
//        """
//    )
//    Page<KhachHang> searchWithPage(
//            @Param("maKH") Integer maKH,
//            @Param("maTK") Integer maTK,
//            @Param("hoTen") String hoTen,
//            @Param("sdt") String sdt,
//            Pageable pageable
//    );
    @Query("""
            SELECT kh FROM KhachHang kh
            WHERE (:maKH IS NULL OR kh.maKH = :maKH)
              AND (:maTK IS NULL OR kh.taiKhoan.maTK = :maTK)
              AND (:hoTen IS NULL OR LOWER(kh.hoTen) LIKE LOWER(CONCAT('%', :hoTen, '%')))
              AND (:sdt IS NULL OR kh.sdt LIKE CONCAT('%', :sdt, '%'))
            """)
    List<KhachHang> search(
            @Param("maKH") Integer maKH,
            @Param("maTK") Integer maTK,
            @Param("hoTen") String hoTen,
            @Param("sdt") String sdt
    );
    // Tìm kiếm theo 1 keyword giống LoaiDichVu, có phân trang
    @Query("""
        SELECT kh FROM KhachHang kh
        WHERE (:keyword IS NULL
            OR LOWER(TRIM(kh.hoTen)) LIKE LOWER(CONCAT('%', TRIM(:keyword), '%'))
            OR kh.sdt LIKE CONCAT('%', :keyword, '%')
            OR CAST(kh.maKH AS string) LIKE CONCAT('%', :keyword, '%')
            OR CAST(kh.taiKhoan.maTK AS string) LIKE CONCAT('%', :keyword, '%')
        )
        """)
    Page<KhachHang> searchWithPage(@Param("keyword") String keyword,
                                   Pageable pageable);
}
