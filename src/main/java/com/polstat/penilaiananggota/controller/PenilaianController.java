package com.polstat.penilaiananggota.controller;

import com.polstat.penilaiananggota.entity.Penilaian;
import com.polstat.penilaiananggota.service.PenilaianService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/penilaian")
public class PenilaianController {

    @Autowired
    private PenilaianService penilaianService;

    // Endpoint untuk menambah penilaian anggota
    @Operation(summary = "Add a new assessment for a member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Assessment successfully added",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Penilaian.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Member not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content)
    })
    @PostMapping("/{nim}")
    public ResponseEntity<Penilaian> addPenilaian(@PathVariable String nim, @RequestBody Penilaian penilaian) {
        Penilaian createdPenilaian = penilaianService.addPenilaian(nim, penilaian);
        return ResponseEntity.ok(createdPenilaian);
    }

    @Operation(summary = "Get assessments by NIM")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of assessments retrieved successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Penilaian.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Member not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content)
    })

    // Endpoint untuk mendapatkan daftar penilaian berdasarkan NIM
    @GetMapping("/{nim}")
    public ResponseEntity<List<Penilaian>> getPenilaianByNim(@PathVariable String nim) {
        List<Penilaian> penilaianList = penilaianService.getPenilaianByNim(nim);
        return ResponseEntity.ok(penilaianList);
    }

    @Operation(summary = "Update an existing assessment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Assessment updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Penilaian.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Assessment not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content)
    })

    // Endpoint untuk mengupdate penilaian berdasarkan ID
    @PutMapping("/{id}")
    public ResponseEntity<Penilaian> updatePenilaian(@PathVariable Long id, @RequestBody Penilaian updatedPenilaian) {
        Penilaian penilaian = penilaianService.updatePenilaian(id, updatedPenilaian);
        return ResponseEntity.ok(penilaian);
    }

    @Operation(summary = "Delete an assessment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Assessment deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Assessment not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content)
    })

    // Endpoint untuk menghapus penilaian berdasarkan ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePenilaian(@PathVariable Long id) {
        penilaianService.deletePenilaian(id);
        return ResponseEntity.noContent().build();
    }
}
