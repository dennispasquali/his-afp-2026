package com.sio.backend.resources.arrivalmode;


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
 * Service che gestisce la logica di business per l'entità {@link ArrivalModes} nel sistema SIO[cite: 8].
 * Gestisce transazionalità, operazioni CRUD e log di auditing delle modalita di arrivo [cite: 8].
 */
@Service
@Slf4j
@Transactional
public class ArrivalModesService {
    private final ArrivalModesRepository repo;
    private final ArrivalModesMapper mapper;

    /**
     * Inizializza il service iniettando il repository e il mapper delle modalita di arrivo[cite: 8].
     *
     * @param repo repository per l'accesso ai dati delle modalita di arrivo[cite: 8]
     * @param mapper componente di conversione tra Entity e DTO[cite: 8]
     */
    public ArrivalModesService(ArrivalModesRepository repo, ArrivalModesMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }



    /**
     * Recupera l'elenco completo di tutte le modalita di arrivo registrate[cite: 8].
     *
     * @return una lista contenente i {@link ArrivalModesDTO} di tutte le modalita di arrivo[cite: 8]
     */
    @Transactional(readOnly = true)
    public List<ArrivalModesDTO> getAllArrivalModes(){
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    /**
     * Recupera un elenco paginato delle modalità di arrivo[cite: 8].
     *
     * @param pageable oggetto contenente i parametri di paginazione e ordinamento[cite: 8]
     * @return una pagina di {@link ArrivalModesDTO}[cite: 8]
     */
    @Transactional(readOnly = true)
    public Page<ArrivalModesDTO> getAllArrivalModesPaginated(Pageable pageable){

        Page<ArrivalModes> entityPage = repo.findAll(pageable);
        return entityPage.map(mapper::toDTO);
    }

    /**
     * Recupera una singola modalita di arrivo tramite il suo codice identificativo[cite: 8].
     *
     * @param code il codice identificativo della modalita di arrivo [cite: 8]
     * @return il {@link ArrivalModesDTO} corrispondente[cite: 8]
     * @throws EntityNotFoundException se la modalita di arrivo non viene trovata[cite: 8]
     */
    @Transactional(readOnly = true)
    public ArrivalModesDTO getArrivalModeById(String code){
        ArrivalModes e = repo.findById(code).orElseThrow(() -> new EntityNotFoundException("Modalità di arrivo con codice "+code+" non trovata"));

        return mapper.toDTO(e);
    }

    /**
     * Crea una nuova modalita di arrivo nel database[cite: 8].
     * Verifica preventivamente l'esistenza di un codice duplicato[cite: 8].
     *
     * @param dto il DTO contenente i dati della modalita di arrivo da creare[cite: 8]
     * @return il {@link ArrivalModesDTO} della modalità di arrivo creata[cite: 8]
     * @throws IllegalArgumentException se il codice della modalità di arrivo esiste già[cite: 8]
     */
    @Transactional
    public ArrivalModesDTO createNewArrivalMode(ArrivalModesDTO dto){


        if (repo.existsById(dto.code())){
            log.warn("Tentativo di creazione di una modalità di arrivo con codice già esistente: {}", dto.code());
            throw new EntityExistsException("Modalità di arrivo con codice " + dto.code() + " già esistente");
        }

        ArrivalModes entity = mapper.toEntity(dto);
        entity = repo.save(entity);

        log.info("Entità modalita di arrivo con codice: {} creata con successo", entity.getCode());
        return mapper.toDTO(entity);
    }

    /**
     * Elimina una modalita di arrivo in base al codice identificativo[cite: 8].
     *
     * @param code il codice della modalita di arrivo da eliminare[cite: 8]
     * @throws EntityNotFoundException se fa il codice con cui si vuole eliminare la modalita di arrivo non esiste
     */
    @Transactional
    public void deleteArrivalMode(String code){
        log.warn("Non è possbile eliminare l'entità {}", code);
        throw new UnsupportedOperationException("Operazione non consentita non si puo eliminare un arrival mode");

    }

    /**
     * Modifica la descrizione di una modalita di arrivo esistente[cite: 8].
     *
     * @param dto il DTO contenente i dati aggiornati[cite: 8]
     * @return il {@link ArrivalModesDTO} aggiornato[cite: 8]
     * @throws EntityNotFoundException se la modalita di arrivo non viene trovata nel database[cite: 8]
     */
    @Transactional
    public ArrivalModesDTO editArrivalMode(ArrivalModesDTO dto){
        ArrivalModes e = repo.findById(dto.code())
                .orElseThrow(() -> new EntityNotFoundException("Modalita di arrivo con codice: " + dto.code() + " non trovata"));

        e.setDescription(dto.description());

        ArrivalModes savedEntity = repo.save(e);
        log.info("Entita con codice {} modificata con successo", dto.code());
        return mapper.toDTO(savedEntity);
    }
}
