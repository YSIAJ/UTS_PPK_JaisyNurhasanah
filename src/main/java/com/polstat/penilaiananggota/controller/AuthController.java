package com.polstat.penilaiananggota.controller;

import com.polstat.penilaiananggota.dto.RegisterRequest;
import com.polstat.penilaiananggota.entity.User;
import com.polstat.penilaiananggota.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "User registration to create a new account.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User registered successfully",
                    content = {
                            @Content}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content)
    })

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        try {
            // Panggil layanan registrasi
            User user = authService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);  // Mengembalikan status CREATED
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Mengembalikan status BAD_REQUEST jika ada error
        }
    }

    @Operation(summary = "User login to get access token.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Email and access token",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema=@Schema(implementation= Page.class))}),
            @ApiResponse(responseCode = "401",
                    description ="Invalid credentials",
                    content = @Content)})

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        try {
            // Panggil layanan login dan dapatkan token JWT
            String accessToken = authService.login(email, password);
            return ResponseEntity.ok(accessToken);  // Mengembalikan token JWT dalam body respons
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email atau kata sandi tidak valid.");  // Mengembalikan status BAD_REQUEST jika login gagal
        }
    }
}
