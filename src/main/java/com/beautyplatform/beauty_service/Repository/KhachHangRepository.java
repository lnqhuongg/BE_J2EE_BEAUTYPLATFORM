package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    boolean existsBySdt(String sdt);

    @Query("SELECT kh FROM KhachHang kh " +
            "WHERE (:maKH IS NULL OR kh.maKH = :maKH) " +
            "AND (:maTK IS NULL OR kh.taiKhoan.maTK = :maTK) " +
            "AND (:hoTen IS NULL OR kh.hoTen LIKE %:hoTen%) " +
            "AND (:sdt IS NULL OR kh.sdt LIKE %:sdt%)")
    List<KhachHang> search(
            @Param("maKH") Integer maKH,
            @Param("maTK") Integer maTK,
            @Param("hoTen") String hoTen,
            @Param("sdt") String sdt
    );
}
