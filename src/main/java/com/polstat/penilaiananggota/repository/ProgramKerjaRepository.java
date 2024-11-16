package com.polstat.penilaiananggota.repository;

import com.polstat.penilaiananggota.entity.ProgramKerja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProgramKerjaRepository extends JpaRepository<ProgramKerja, Long> {

    // Mendapatkan daftar program kerja berdasarkan divisi
    List<ProgramKerja> findByDivisi(String divisi);

    // Mendapatkan program kerja berdasarkan nama program
    List<ProgramKerja> findByNamaProgramContaining(String namaProgram);
}
