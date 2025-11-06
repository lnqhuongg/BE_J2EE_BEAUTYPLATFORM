package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.NhaCungCapGioLamViec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NhaCungCapGioLamViecRepository extends JpaRepository<NhaCungCapGioLamViec, Integer> {

    // Lấy giờ làm việc theo mã NCC
    List<NhaCungCapGioLamViec> findByNhaCungCap_MaNCC(int maNCC);
}