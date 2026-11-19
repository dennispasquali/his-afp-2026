package com.sio.backend.resources.triagecolor;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "triage_colors") // schema="sio"
@Schema(description = "Entità di persistenza per la gestione dei colori e codici di triage di arrivo del paziente nel sistema SIO")
public class TriageColors {
    /**
     * Codice identificativo univoco del triage color del paziente (Chiave Primaria).
     */
    @Id
    @Size(max = 20)
    @NotBlank
    @Column(name = "code", nullable = false, length = 20)
    @Schema(description = "Codice identificativo univoco del triage color del paziente", example = "ROSSO", maxLength = 20)
    private String code;

    /**
     * Descrizione testuale estesa del triage color del paziente.
     */
    @Size(max = 50)
    @NotBlank
    @Column(name = "display_name", nullable = false, length = 50)
    @Schema(description = "Descrizione dettagliata dello stato di arrivo del paziente", example = "Codice Rosso - Emergenza", maxLength = 50)
    private String displayName;


    /**
     * livello di priorità triage color del paziente.
     */
    @NotNull
    @Positive
    @Column(name = "priority", nullable = false)
    @Schema(description = "Descrizione dettagliata della priorita nel triage del paziente", example = "1")
    private Integer priority;



    /**
     * codice hex del codice colore di arrivo del paziente.
     */
    @Size(max = 7)
    @NotBlank
    @Column(name = "hex_value", nullable = false)
    @Schema(description = "codice esadecimale del colore di gravità assegnato al paziente durante il triage", example = "#FF0000", maxLength = 7)
    private String hexValue;

    /**
     * Timestamp di creazione del record, generato automaticamente dal database.
     */
    @CreationTimestamp
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    @Schema(description = "Data e ora di registrazione dell assegnazione del codice di arrivo")
    private Instant createdAt;
}
