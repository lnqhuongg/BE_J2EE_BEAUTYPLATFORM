package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.DatLich;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DatLichRepository extends JpaRepository<DatLich, Integer> {

    // Lấy tất cả đặt lịch của khách hàng, sắp xếp theo ngày tạo mới nhất
    List<DatLich> findByKhachHang_MaKHOrderByNgayTaoDesc(int maKH);

    // Lấy đặt lịch theo khách hàng và trạng thái
    List<DatLich> findByKhachHang_MaKHAndTrangThaiOrderByNgayTaoDesc(int maKH, int trangThai);

    // Lấy đặt lịch với chi tiết (tối ưu query)
    @Query("""
        SELECT DISTINCT dl FROM DatLich dl
        LEFT JOIN FETCH dl.khachHang
        WHERE dl.khachHang.maKH = :maKH
        ORDER BY dl.ngayTao DESC
    """)
    List<DatLich> findByKhachHangWithDetails(@Param("maKH") int maKH);
}