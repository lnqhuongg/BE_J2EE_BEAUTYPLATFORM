package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.YeuThich;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YeuThichRepository extends JpaRepository<YeuThich, Integer> {
    List<YeuThich> findByKhachHang_MaKH(int maKH);
}
