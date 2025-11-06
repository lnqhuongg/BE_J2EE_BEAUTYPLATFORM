package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.LoaiHinhKinhDoanh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiHinhKinhDoanhRepository extends JpaRepository<LoaiHinhKinhDoanh, Integer> {
}