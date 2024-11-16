package com.polstat.penilaiananggota.service;

import com.polstat.penilaiananggota.entity.User;
import com.polstat.penilaiananggota.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Untuk mengencode password baru

    // Mendapatkan profil pengguna berdasarkan NIM
    public Optional<User> getProfile(String nim) {
        return Optional.ofNullable(userRepository.findByNim(nim));  // Mengembalikan Optional
    }

    // Memperbarui profil pengguna
    public User updateProfile(String nim, User updatedProfile) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByNim(nim));

        if (userOptional.isPresent()) {
            User user = userOptional.get();  // Get the user from Optional
            user.setNama(updatedProfile.getNama());
            user.setKelas(updatedProfile.getKelas());
            user.setDivisi(updatedProfile.getDivisi());
            user.setNoTelp(updatedProfile.getNoTelp());
            return userRepository.save(user);
        } else {
            throw new RuntimeException("Pengguna dengan NIM " + nim + " tidak ditemukan.");
        }
    }

    // Mengubah password pengguna
    public User changePassword(String nim, String newPassword) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByNim(nim));

        if (userOptional.isPresent()) {
            User user = userOptional.get();  // Get the user from Optional
            user.setPassword(passwordEncoder.encode(newPassword));  // Mengenkripsi password baru
            return userRepository.save(user);
        } else {
            throw new RuntimeException("Pengguna dengan NIM " + nim + " tidak ditemukan.");
        }
    }
}
