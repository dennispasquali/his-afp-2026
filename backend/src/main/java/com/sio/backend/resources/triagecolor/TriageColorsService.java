package com.sio.backend.resources.triagecolor;


import com.sio.backend.resources.pathology.Pathologies;
import com.sio.backend.resources.pathology.PathologiesDTO;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



/**
 * Service che gestisce la logica di business per l'entità {@link TriageColors} nel sistema SIO[cite: 8].
 * Gestisce transazionalità, operazioni CRUD e log di auditing dei codici pazienti [cite: 8].
 */
@Service
@Slf4j
@Transactional
public class TriageColorsService {
    private final TriageColorsRepository repo;
    private final TriageColorsMapper mapper;

    /**
     * Inizializza il service iniettando il repository e il mapper dei triage color[cite: 8].
     *
     * @param repo repository per l'accesso ai dati dei triage color [cite: 8]
     * @param mapper componente di conversione tra Entity e DTO[cite: 8]
     */
    public TriageColorsService(TriageColorsRepository repo, TriageColorsMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }




    /**
     * Recupera l'elenco completo di tutte i triage color registrati[cite: 8].
     *
     * @return una lista contenente i {@link TriageColorsDTO} di tutti i triage color [cite: 8]
     */
    @Transactional(readOnly = true)
    public List<TriageColorsDTO> getAllTriageColors(){
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    /**
     * Recupera un elenco paginato dei triage color [cite: 8].
     *
     * @param pageable oggetto contenente i parametri di paginazione e ordinamento[cite: 8]
     * @return una pagina di {@link TriageColorsDTO}[cite: 8]
     */
    @Transactional(readOnly = true)
    public Page<TriageColorsDTO> getAllTriageColorsPaginated(Pageable pageable){

        Page<TriageColors> entityPage = repo.findAll(pageable);
        return entityPage.map(mapper::toDTO);
    }

    /**
     * Recupera una singolo triage color tramite il suo codice identificativo[cite: 8].
     *
     * @param code il codice identificativo del triage color [cite: 8]
     * @return il {@link TriageColorsDTO} corrispondente[cite: 8]
     * @throws java.util.NoSuchElementException se il triage color non viene trovato[cite: 8]
     */
    @Transactional(readOnly = true)
    public TriageColorsDTO getTriageColorById(String code){
        TriageColors e = repo.findById(code).orElseThrow(() -> new EntityNotFoundException("Codice paziente con codice "+code+" non trovata"));

        return mapper.toDTO(e);
    }

    /**
     * Crea una nuovo triage color nel database[cite: 8].
     * Verifica preventivamente l'esistenza di un codice duplicato[cite: 8].
     *
     * @param dto il DTO contenente i dati del triage color da creare[cite: 8]
     * @return il {@link TriageColorsDTO} del triage color creato[cite: 8]
     * @throws IllegalArgumentException se il codice del triage color esiste già[cite: 8]
     */
    public TriageColorsDTO createNewTriageColor(TriageColorsDTO dto){
        if (repo.existsById(dto.code())){
            log.warn("Tentativo di creazione di un triage color con codice già esistente: {}", dto.code());
            throw new EntityExistsException("Triage color con codice " + dto.code() + " già esistente");
        }

        TriageColors entity = mapper.toEntity(dto);
        entity = repo.save(entity);

        log.info("Entità triage color con codice: {} creata con successo", entity.getCode());
        return mapper.toDTO(entity);
    }

    /**
     * Elimina un triage color in base al codice identificativo[cite: 8].
     *
     * @param code il codice della modalita di arrivo da eliminare[cite: 8]
     */
    public void deleteTriageColor(String code){
        TriageColors e = repo.findById(code)
                .orElseThrow(() -> new EntityNotFoundException("Triage color con codice: " + code + " non trovato"));
        repo.delete(e);

        log.info("Triage color con codice {} eliminato con successo", code);
    }

    /**
     * Modifica la descrizione di un triage color esistente[cite: 8].
     *
     * @param dto il DTO contenente i dati aggiornati[cite: 8]
     * @return il {@link TriageColorsDTO} aggiornato[cite: 8]
     * @throws EntityNotFoundException se il triage color non viene trovato nel database[cite: 8]
     */
    public TriageColorsDTO editTriageColor(TriageColorsDTO dto){
        TriageColors e = repo.findById(dto.code())
                .orElseThrow(() -> new EntityNotFoundException("Modalita di arrivo con codice: " + dto.code() + " non trovata"));

        e.setPriority(dto.priority());
        e.setDisplayName(dto.displayName());

        TriageColors savedEntity = repo.save(e);
        log.info("Entita con codice {} modificata con successo", dto.code());
        return mapper.toDTO(savedEntity);
    }
}
