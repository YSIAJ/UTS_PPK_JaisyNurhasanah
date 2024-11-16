package com.polstat.penilaiananggota.service;

import com.polstat.penilaiananggota.entity.User;
import com.polstat.penilaiananggota.dto.RegisterRequest;
import com.polstat.penilaiananggota.repository.UserRepository;
import com.polstat.penilaiananggota.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Fungsi registrasi
    public User register(RegisterRequest registerRequest) {
        // Cek apakah email sudah terdaftar
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email " + registerRequest.getEmail() + " sudah terdaftar.");
        }

        // Mengonversi RegisterRequest menjadi User entity
        User user = new User();
        user.setNim(registerRequest.getNim());
        user.setNama(registerRequest.getNama());
        user.setKelas(registerRequest.getKelas());
        user.setDivisi(registerRequest.getDivisi());
        user.setNoTelp(registerRequest.getNoTelp());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // Enkripsi password

        // Menyimpan User ke database
        return userRepository.save(user);
    }

    // Fungsi login menggunakan email dan password
    public String login(String email, String password) {
        // Mencari user berdasarkan email
        User user = userRepository.findByEmail(email);

        // Periksa apakah pengguna ditemukan dan password valid
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Membuat objek Authentication dengan email dan password
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

            // Melakukan autentikasi menggunakan AuthenticationManager
            authentication = authenticationManager.authenticate(authentication);

            // Generate token JWT
            return jwtUtil.generateAccessToken(authentication);
        } else {
            // Jika login gagal, lempar exception
            throw new RuntimeException("Email atau kata sandi tidak valid.");
        }
    }
}
