package com.polstat.penilaiananggota.util;

import com.polstat.penilaiananggota.entity.User;
import com.polstat.penilaiananggota.repository.UserRepository; // Pastikan ini di-import
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository; // Injeksi UserRepository

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Memeriksa apakah request mengandung header Authorization
        System.out.println("Processing request to: " + request.getRequestURI());
        String authHeader = request.getHeader("Authorization");
        System.out.println("Auth header: " + authHeader);

        // Jika tidak ada Bearer token, lanjutkan filter chain
        if (!hasAuthorizationBearer(request)) {
            System.out.println("No Bearer token found");
            filterChain.doFilter(request, response);
            return;
        }

        // Mengambil dan memvalidasi token
        String token = getAccessToken(request);
        if (!jwtUtil.validateAccessToken(token)) {
            System.out.println("Invalid token");
            filterChain.doFilter(request, response);
            return;
        }

        // Jika token valid, set context keamanan
        setAuthenticationContext(token, request);
        System.out.println("Authentication set successfully");
        filterChain.doFilter(request, response);
    }

    // Memeriksa apakah request mengandung header Authorization yang valid (Bearer)
    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return !ObjectUtils.isEmpty(header) && header.startsWith("Bearer");
    }

    // Mengambil token dari header Authorization
    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header.split(" ")[1].trim(); // Mengambil token setelah "Bearer"
    }

    // Menetapkan context autentikasi dengan token yang valid
    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // Mendapatkan informasi pengguna berdasarkan token
    private UserDetails getUserDetails(String token) {
        String email = jwtUtil.getSubject(token); // Mengambil email dari token
        User user = userRepository.findByEmail(email); // Menggunakan objek userRepository yang di-inject
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email); // Jika pengguna tidak ditemukan
        }

        // Membuat objek UserDetails menggunakan email dan password
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword()) // Pastikan password sudah dienkripsi
                .authorities("USER")  // Misalnya Anda menetapkan "USER" sebagai authority
                .build();
    }
}
