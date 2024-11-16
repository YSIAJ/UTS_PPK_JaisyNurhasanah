package com.polstat.penilaiananggota.repository;

import com.polstat.penilaiananggota.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // Menemukan user berdasarkan email
    public User findByEmail(String email);

    // Memeriksa apakah email sudah ada
    boolean existsByEmail(String email);

    // Menemukan user berdasarkan NIM
    public User findByNim(String nim);  // Menambahkan metode ini
}
