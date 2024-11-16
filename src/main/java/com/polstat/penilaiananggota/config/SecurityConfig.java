package com.polstat.penilaiananggota.config;

import com.polstat.penilaiananggota.entity.User;
import com.polstat.penilaiananggota.repository.UserRepository;
import com.polstat.penilaiananggota.util.JwtFilter;
import com.polstat.penilaiananggota.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        // Menggunakan UserRepository untuk load user berdasarkan email
        authenticationManagerBuilder.userDetailsService(email -> {
            // Mencari pengguna berdasarkan email
            User user = userRepository.findByEmail(email);

            // Jika user tidak ditemukan, lempar exception
            if (user == null) {
                throw new UsernameNotFoundException("Pengguna tidak ditemukan dengan email: " + email);
            }

            // Jika ditemukan, kembalikan UserDetails
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .authorities("USER")  // Menetapkan otoritas pengguna
                    .build();
        }).passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Nonaktifkan CSRF secara eksplisit
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/register", "/login","/docs/**").permitAll()  // Akses publik ke endpoint tertentu
                                .anyRequest().authenticated()  // Semua request lain harus terautentikasi
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Mengatur session menjadi stateless
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);  // Menambahkan filter JWT

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Encoder untuk password
    }
}
