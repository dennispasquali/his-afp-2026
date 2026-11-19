package com.sio.backend.resources.arrivalmode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaccia Repository per la gestione della persistenza dell'entità {@link ArrivalModes}.
 * Estende {@link JpaRepository} ereditando le operazioni CRUD standard,
 * utilizzando {@link String} come tipo della chiave primaria (codice identificativo).
 */
@Repository
public interface ArrivalModesRepository extends JpaRepository<ArrivalModes, String> {
}