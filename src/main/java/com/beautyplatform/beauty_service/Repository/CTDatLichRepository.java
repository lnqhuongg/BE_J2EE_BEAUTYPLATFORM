package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.CTDatLich;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CTDatLichRepository extends JpaRepository<CTDatLich, Integer> {
    @Query("""
                SELECT ct 
                FROM CTDatLich ct
                JOIN ct.datLich dl
                WHERE ct.nhanVien.maNV = :maNV
                  AND dl.ngayTao = :ngay
            """)
    List<CTDatLich> findBusyTimeByNhanVienAndDate(
            @Param("maNV") int maNV,
            @Param("ngay") LocalDate ngay
    );
}
