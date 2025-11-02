package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.LoaiDichVu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoaiDichVuRepository extends JpaRepository<LoaiDichVu, Integer> {
    public Optional<LoaiDichVu> findById(int maLDV);
}
