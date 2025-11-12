package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.LoaiHinhKinhDoanh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaiHinhKinhDoanhRepository extends JpaRepository<LoaiHinhKinhDoanh, Integer> {
    boolean existsByTenLHIgnoreCase(String tenLH);
    boolean existsByTenLHIgnoreCaseAndMaLHNot(String tenLH, int maLH);
    @Query("SELECT l FROM LoaiHinhKinhDoanh l " +
            "WHERE (:keyword IS NULL " +
            "   OR CAST(l.maLH AS string) LIKE %:keyword% " +
            "   OR l.tenLH LIKE %:keyword%)")
    List<LoaiHinhKinhDoanh> search(String keyword);

    @Query("SELECT l FROM LoaiHinhKinhDoanh l WHERE LOWER(l.tenLH) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<LoaiHinhKinhDoanh> searchWithPage(@Param("keyword") String keyword, Pageable pageable);
}