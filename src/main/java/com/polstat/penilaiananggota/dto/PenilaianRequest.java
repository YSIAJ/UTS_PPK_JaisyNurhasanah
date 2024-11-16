package com.polstat.penilaiananggota.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PenilaianRequest {

    @NotNull(message = "ID anggota tidak boleh kosong")
    private Long anggotaId;

    @NotBlank(message = "Deskripsi penilaian tidak boleh kosong")
    private String deskripsi;

    @NotNull(message = "Skor tidak boleh kosong")
    @Min(value = 0, message = "Skor minimal adalah 0")
    @Max(value = 100, message = "Skor maksimal adalah 100")
    private Integer skor;
}
