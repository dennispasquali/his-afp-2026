package com.sio.backend.resources.arrivalmode;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) in formato record per il trasferimento
 * dei dati relativi alle modalita di arrivo del paziente nel sistema SIO.
 */
@Schema(description = "DTO per il trasferimento dei dati riguardanti la modalita di arrivo")
public record ArrivalModesDTO(
        @Schema(
                description = "Codice univoco della modalita di arrivo",
                example = "AMB",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Il codice è obbligatorio")
        String code,

        @Schema(
                description = "Descrizione della modalita di arrivo",
                example = "Ambulanza 118",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank
        String description
) {}
