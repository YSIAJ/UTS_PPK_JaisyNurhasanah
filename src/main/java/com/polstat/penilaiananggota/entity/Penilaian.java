package com.polstat.penilaiananggota.entity;

import jakarta.persistence.*;

@Entity
public class Penilaian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int kehadiran;
    private int inovasi;
    private int keberhasilan;

    // Constructor
    public Penilaian() {}

    public Penilaian(User user, int kehadiran, int inovasi, int keberhasilan) {
        this.user = user;
        this.kehadiran = kehadiran;
        this.inovasi = inovasi;
        this.keberhasilan = keberhasilan;
    }

    // Getter dan Setter
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getKehadiran() {
        return kehadiran;
    }

    public void setKehadiran(int kehadiran) {
        this.kehadiran = kehadiran;
    }

    public int getInovasi() {
        return inovasi;
    }

    public void setInovasi(int inovasi) {
        this.inovasi = inovasi;
    }

    public int getKeberhasilan() {
        return keberhasilan;
    }

    public void setKeberhasilan(int keberhasilan) {
        this.keberhasilan = keberhasilan;
    }

    // Total nilai
    public int getTotalNilai() {
        return kehadiran + inovasi + keberhasilan;
    }
}
