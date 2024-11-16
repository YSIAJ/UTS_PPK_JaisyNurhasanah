package com.polstat.penilaiananggota.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "NIM tidak boleh kosong")
    private String nim;

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 6, max = 16, message = "Password harus antara 6 hingga 16 karakter")
    private String password;

    @NotBlank(message = "Nama tidak boleh kosong")
    private String nama;

    @NotBlank(message = "Kelas tidak boleh kosong")
    private String kelas;

    @NotBlank(message = "Divisi tidak boleh kosong")
    private String divisi;

    @NotBlank(message = "Nomor telepon tidak boleh kosong")
    @Size(min = 10, max = 13, message = "Nomor telepon harus antara 10 hingga 13 karakter")
    private String noTelp;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Email tidak valid")
    private String email;  // Tambahkan email
}
