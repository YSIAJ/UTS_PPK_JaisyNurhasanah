package com.polstat.penilaiananggota.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ProgramKerja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String divisi;
    private String namaProgram;
    private String deskripsi;
    private LocalDate tanggalPelaksanaan;

    // Constructor
    public ProgramKerja() {}

    public ProgramKerja(String divisi, String namaProgram, String deskripsi, LocalDate tanggalPelaksanaan) {
        this.divisi = divisi;
        this.namaProgram = namaProgram;
        this.deskripsi = deskripsi;
        this.tanggalPelaksanaan = tanggalPelaksanaan;
    }

    // Getter dan Setter
    public Long getId() {
        return id;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getNamaProgram() {
        return namaProgram;
    }

    public void setNamaProgram(String namaProgram) {
        this.namaProgram = namaProgram;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public LocalDate getTanggalPelaksanaan() {
        return tanggalPelaksanaan;
    }

    public void setTanggalPelaksanaan(LocalDate tanggalPelaksanaan) {
        this.tanggalPelaksanaan = tanggalPelaksanaan;
    }
}
