package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.LoaiDichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaiDichVuRepository extends JpaRepository<LoaiDichVu, Integer> {

    @Query("SELECT l FROM LoaiDichVu l " +
            "WHERE (:keyword IS NULL OR l.tenLDV LIKE %:keyword%) " +
            "AND (:trangThai IS NULL OR l.trangThai = :trangThai)")
    List<LoaiDichVu> search(String keyword, Integer trangThai);
}
