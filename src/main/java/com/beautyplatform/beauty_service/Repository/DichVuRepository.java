package com.beautyplatform.beauty_service.Repository;

import com.beautyplatform.beauty_service.Model.DichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DichVuRepository extends JpaRepository<DichVu, Integer> {

    @Query(value = """
        SELECT *
        FROM dichvu
        WHERE
            (:maDV IS NULL OR maDV = :maDV)
            AND (:maLoaiDV IS NULL OR maLoaiDV = :maLoaiDV)
            AND (:maNCC IS NULL OR maNCC = :maNCC)
            AND (:tenDV IS NULL OR LIKE CONCAT('%', :tenDV, '%'))
        """, nativeQuery = true)
    public List<DichVu> search(
            @Param("maDV") int maDV,
            @Param("maLoaiDV") int maLoaiDV,
            @Param("maNCC") int maNCC,
            @Param("tenDV") String tenDV
    );
}
