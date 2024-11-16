package com.polstat.penilaiananggota.service;

import com.polstat.penilaiananggota.entity.Penilaian;
import com.polstat.penilaiananggota.entity.User;
import com.polstat.penilaiananggota.repository.PenilaianRepository;
import com.polstat.penilaiananggota.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PenilaianService {

    @Autowired
    private PenilaianRepository penilaianRepository;

    @Autowired
    private UserRepository userRepository;

    // Menambahkan penilaian baru berdasarkan NIM
    public Penilaian addPenilaian(String nim, Penilaian penilaian) {
        // Cari user berdasarkan NIM
        User user = userRepository.findByNim(nim);
        if (user == null) {
            throw new RuntimeException("User dengan NIM " + nim + " tidak ditemukan.");
        }

        // Menambahkan user ke penilaian
        penilaian.setUser(user);

        // Simpan penilaian ke database
        return penilaianRepository.save(penilaian);
    }

    // Mendapatkan daftar penilaian berdasarkan NIM
    public List<Penilaian> getPenilaianByNim(String nim) {
        // Cari user berdasarkan NIM
        User user = userRepository.findByNim(nim);
        if (user == null) {
            throw new RuntimeException("User dengan NIM " + nim + " tidak ditemukan.");
        }

        // Cari dan kembalikan daftar penilaian berdasarkan user
        return penilaianRepository.findByUser(user);
    }

    // Menambahkan penilaian baru
    public Penilaian createPenilaian(Penilaian penilaian) {
        return penilaianRepository.save(penilaian);
    }

    // Mendapatkan daftar penilaian berdasarkan user
    public List<Penilaian> getPenilaianByUser(User user) {
        return penilaianRepository.findByUser(user);
    }

    // Mendapatkan penilaian berdasarkan ID
    public Optional<Penilaian> getPenilaianById(Long id) {
        return penilaianRepository.findById(id);
    }

    // Mengupdate data penilaian
    public Penilaian updatePenilaian(Long id, Penilaian updatedPenilaian) {
        return penilaianRepository.findById(id)
                .map(penilaian -> {
                    penilaian.setKehadiran(updatedPenilaian.getKehadiran());
                    penilaian.setInovasi(updatedPenilaian.getInovasi());
                    penilaian.setKeberhasilan(updatedPenilaian.getKeberhasilan());
                    return penilaianRepository.save(penilaian);
                }).orElseThrow(() -> new RuntimeException("Penilaian dengan ID " + id + " tidak ditemukan."));
    }

    // Menghapus penilaian berdasarkan ID
    public void deletePenilaian(Long id) {
        if (!penilaianRepository.existsById(id)) {
            throw new RuntimeException("Penilaian dengan ID " + id + " tidak ditemukan.");
        }
        penilaianRepository.deleteById(id);
    }
}
