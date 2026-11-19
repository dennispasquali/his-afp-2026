package com.sio.backend.resources.triagecolor;

import com.sio.backend.resources.triagecolor.TriageColors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaccia Repository per la gestione della persistenza dell'entità {@link TriageColors}.
 * Estende {@link JpaRepository} ereditando le operazioni CRUD standard,
 * utilizzando {@link String} come tipo della chiave primaria (codice identificativo).
 */
@Repository
public interface TriageColorsRepository extends JpaRepository<TriageColors, String> {
}