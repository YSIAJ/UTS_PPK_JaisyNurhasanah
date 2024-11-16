package com.polstat.penilaiananggota.controller;

import com.polstat.penilaiananggota.entity.User;
import com.polstat.penilaiananggota.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    // Endpoint untuk melihat profil pengguna berdasarkan NIM
    @Operation(summary = "Get user profile by NIM")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User profile retrieved successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/{nim}")
    @PermitAll
    public ResponseEntity<User> getProfile(@PathVariable String nim) {
        Optional<User> user = profileService.getProfile(nim);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint untuk mengupdate profil pengguna
    @Operation(summary = "Update user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User profile updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content)
    })
    @PutMapping("/{nim}")
    public ResponseEntity<User> updateProfile(@PathVariable String nim, @RequestBody User updatedUser) {
        User user = profileService.updateProfile(nim, updatedUser);
        return ResponseEntity.ok(user);
    }

    // Endpoint untuk mengubah password
    @Operation(summary = "Change user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Password changed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Invalid password format",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content)
    })
    @PutMapping("/{nim}/change-password")
    public ResponseEntity<User> changePassword(@PathVariable String nim, @RequestParam String newPassword) {
        User user = profileService.changePassword(nim, newPassword);
        return ResponseEntity.ok(user);
    }
}
