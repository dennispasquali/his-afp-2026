package com.sio.backend.resources.triagecolor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TriageColorsDTO(

        @Schema(
                description = "Codice univoco del triage color",
                example = "ROSSO",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Il codice è obbligatorio")
        String code,

        @Schema(
                description = "Descrizione del triage color",
                example = "Codice Rosso - Emergenza",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank
        String displayName,

        @Schema(
                  description = "Priorità del triage color",
                  example = "1",
                  requiredMode = Schema.RequiredMode.REQUIRED
          )
        @NotNull
        @Positive
        Integer priority,


        @Schema(
                description = "Codice HEX del triage color",
                example = "#FF0000",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank
        String hexValue

) {}
