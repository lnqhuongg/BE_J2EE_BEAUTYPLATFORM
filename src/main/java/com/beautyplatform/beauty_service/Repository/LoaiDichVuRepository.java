package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.LoaiDichVu;
import com.beautyplatform.beauty_service.Model.LoaiHinhKinhDoanh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaiDichVuRepository extends JpaRepository<LoaiDichVu, Integer> {

    @Query("SELECT l FROM LoaiDichVu l " +
            "WHERE (:keyword IS NULL OR l.tenLDV LIKE %:keyword%) " +
            "AND (:trangThai IS NULL OR l.trangThai = :trangThai)")
    List<LoaiDichVu> search(String keyword, Integer trangThai);

    @Query("SELECT loai FROM LoaiDichVu loai WHERE LOWER(loai.tenLDV) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<LoaiDichVu> searchWithPage(@Param("keyword") String keyword, Pageable pageable);
}
