package com.polstat.penilaiananggota.repository;

import com.polstat.penilaiananggota.entity.Penilaian;
import com.polstat.penilaiananggota.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PenilaianRepository extends JpaRepository<Penilaian, Long> {

    // Mendapatkan daftar penilaian berdasarkan user
    List<Penilaian> findByUser(User user);

    // Menghitung total penilaian untuk user tertentu
    int countByUser(User user);
}
